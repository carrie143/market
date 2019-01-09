package com.gop.c2c.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.gop.asset.dto.AssetOperationDto;
import com.gop.c2c.dto.C2cAlipayInfoDto;
import com.gop.c2c.dto.C2cBankInfoDto;
import com.gop.c2c.dto.C2cTransOrderDto;
import com.gop.c2c.facade.C2cMessageFacade;
import com.gop.c2c.service.C2cAlipayInfoService;
import com.gop.c2c.service.C2cBankInfoService;
import com.gop.c2c.service.C2cOrderPaymentDetailService;
import com.gop.c2c.service.C2cOrderRecordService;
import com.gop.c2c.service.C2cSellAdvertisementService;
import com.gop.c2c.service.C2cTransOrderOperRecordService;
import com.gop.c2c.service.C2cTransOrderRecordService;
import com.gop.c2c.service.C2cTransOrderService;
import com.gop.c2c.service.C2cUserCancelTransOrderCountRecordService;
import com.gop.code.consts.C2cCodeConst;
import com.gop.code.consts.MessageConst;
import com.gop.common.Environment;
import com.gop.domain.C2cAlipayInfo;
import com.gop.domain.C2cBankInfo;
import com.gop.domain.C2cOrderPaymentDetail;
import com.gop.domain.C2cOrderRecord;
import com.gop.domain.C2cSellAdvertisement;
import com.gop.domain.C2cTransOrder;
import com.gop.domain.C2cTransOrderRecord;
import com.gop.domain.C2cUserCancelTransOrderCountRecord;
import com.gop.domain.TokenOrderCompletionCount;
import com.gop.domain.User;
import com.gop.domain.enums.C2cPayType;
import com.gop.domain.enums.C2cSellAdvertStatus;
import com.gop.domain.enums.C2cTransOrderStatus;
import com.gop.domain.enums.C2cTransType;
import com.gop.exception.AppException;
import com.gop.financecheck.enums.AccountClass;
import com.gop.financecheck.enums.AccountSubject;
import com.gop.financecheck.enums.BusinessSubject;
import com.gop.mapper.C2cTransOrderMapper;
import com.gop.mode.vo.PageModel;
import com.gop.offlineorder.service.TokenOrderCompletionCountService;
import com.gop.user.facade.UserFacade;
import com.gop.util.OrderUtil;
import com.gop.util.SequenceUtil;

import lombok.extern.slf4j.Slf4j;

@Service("C2cTransOrderService")
@Slf4j
public class C2cTransOrderServiceImpl implements C2cTransOrderService {
	@Autowired
	private C2cSellAdvertisementService c2cSellAdvertisementService;
	@Autowired
	private C2cTransOrderMapper c2cTransOrderMapper;
	@Autowired
	private C2cTransOrderOperRecordService c2cTransOrderOperRecordService;
	@Autowired
	private TokenOrderCompletionCountService tokenOrderCompletionCountService;
	@Autowired
	private UserFacade userFacade;
	@Autowired
	private C2cTransOrderService c2cTransOrderService;
	@Autowired
	private C2cOrderRecordService c2cOrderRecordService;
	@Autowired
	private C2cUserCancelTransOrderCountRecordService c2cUserCancelTransOrderCountRecordService;
	@Autowired
	private C2cMessageFacade c2cMessageFacade;
	@Autowired
	private C2cTransOrderRecordService c2cTransOrderRecordService;
	@Autowired
	private C2cOrderPaymentDetailService c2cOrderPaymentDetailService;
	@Autowired
	private C2cBankInfoService c2cBankInfoService;
	@Autowired
	private C2cAlipayInfoService c2cAlipayInfoService;
	@Autowired
	private Environment environmentContxt;

	@Override
	public C2cTransOrder selectByTransOrderId(String transOrderId) {
		C2cTransOrder order = c2cTransOrderMapper.selectByTransOrderId(transOrderId);
		if (null == order) {
			throw new AppException(C2cCodeConst.INVALID_TRANSORDER, "id错误或数据库无此数据");
		}
		return order;
	}

	@Override
	@Transactional
	public String createTransOrder(String advertId, Integer uid, BigDecimal number, C2cPayType buyPayType,
			String remark) {
		// 校验用户是否有待付款订单
		List<C2cTransOrder> checkUserUnpayList = queryBuyerByUncompletedStatus(uid, C2cTransOrderStatus.UNPAY);
		if (checkUserUnpayList.size() >=1 ) {
			throw new AppException(C2cCodeConst.TRANSORDER_HAVE_AN_UNPAY, "有未支付订单");
		}
		// 校验,查询原有状态是否为展示状态
		C2cSellAdvertisement sellAdvert = c2cSellAdvertisementService.selectByAdvertId(advertId);
		if (null == sellAdvert) {
			throw new AppException(C2cCodeConst.ADVERT_NOT_EXIST, "广告不存在");
		}
		// 购买个数不可以大于广告单
		if (sellAdvert.getLockNum().compareTo(number) < 0 || number.compareTo(BigDecimal.ZERO) <= 0) {
			throw new AppException(C2cCodeConst.NUMBER_MATCH_ERROR_IN_ADVERT, "订单中购买数量异常");
		}
		if (!sellAdvert.getStatus().equals(C2cSellAdvertStatus.SHOW)) {
			throw new AppException(C2cCodeConst.TRANSORDER_STATUS_MATCH_ERROR, "广告状态不符,只可以是展示中");
		}
		Integer buyUid = uid;
		Integer sellUid = sellAdvert.getUid();
		User buyer = userFacade.getUser(buyUid);
		User seller = userFacade.getUser(sellUid);
		// 用户下单,确认buyer是否有手机号
		if (null == buyer || Strings.isNullOrEmpty(buyer.getMobile())) {
			throw new AppException(C2cCodeConst.NO_SET_PHONE, "用户未设置手机号");
		}
		// 卖方昵称校验
		if (null == seller || Strings.isNullOrEmpty(seller.getNickname())) {
			throw new AppException(C2cCodeConst.NO_SET_NICKNAME, "用户未设置昵称");
		}

		Calendar currentDayBegin = new GregorianCalendar();
		currentDayBegin.set(Calendar.HOUR_OF_DAY, 0);
		currentDayBegin.set(Calendar.MINUTE, 0);
		currentDayBegin.set(Calendar.SECOND, 0);
		System.out.println(currentDayBegin.getTime());
		int count = c2cUserCancelTransOrderCountRecordService.getCountOfCancelToday(uid, currentDayBegin.getTime(),
				new Date());
		if (count >= 3) {
			throw new AppException(C2cCodeConst.FORBID_TRADE_BY_CANCEL_ORDER_MORETHAN_3_DAILY, "用户取消单子超过三次");
		}
		// 是否判断自卖自买
		if (buyUid.equals(sellUid)) {
			throw new AppException(C2cCodeConst.SELF_TRADE_FORBIDDEN, "用户不可以自我买卖");
		}
		C2cOrderRecord orderRecord = c2cOrderRecordService.selectOrderByAdvertId(advertId);
		// 更新广告状态为已购买
		int upResult = c2cSellAdvertisementService.updateC2cSellAdvertStatusByAdvertId(advertId,
				C2cSellAdvertStatus.PURCHASED, C2cSellAdvertStatus.SHOW);
		if (upResult != 1) {
			throw new AppException(C2cCodeConst.ORDER_CREATED, "订单已被创建");
		}
		// 创建交易单
		// 生成TransOrderId
		String transOrderId = OrderUtil.generateC2cCode();
		String buyRequestNo = SequenceUtil.getNextId();
		String sellRequestNo = SequenceUtil.getNextId();
		// 创建交易单对象
		C2cTransOrder c2cTransOrder = new C2cTransOrder();
		c2cTransOrder.setAdvertId(advertId);
		c2cTransOrder.setAssetCode(sellAdvert.getAssetCode());
		c2cTransOrder.setBuyNickname(Strings.isNullOrEmpty(buyer.getNickname()) ? "" : buyer.getNickname());
		c2cTransOrder.setBuyPayType(buyPayType.toString());
		c2cTransOrder.setBuyUid(buyUid);
		c2cTransOrder.setLockNum(sellAdvert.getLockNum());
		c2cTransOrder.setSellUid(sellUid);
		c2cTransOrder.setSellNickname(seller.getNickname());
		c2cTransOrder.setTransOrderId(transOrderId);
		c2cTransOrder.setOrderId(orderRecord.getOrderId());
		c2cTransOrder.setNumber(number);
		c2cTransOrder.setMoney(number.multiply(sellAdvert.getTradePrice()).setScale(2, BigDecimal.ROUND_DOWN));
		c2cTransOrder.setBuyRequestNo(buyRequestNo);
		c2cTransOrder.setSellRequestNo(sellRequestNo);
		c2cTransOrder.setFee(BigDecimal.ZERO); // 第一版不收费,设置为零
		c2cTransOrder.setStatus(C2cTransOrderStatus.UNPAY);
		c2cTransOrder.setTradePrice(sellAdvert.getTradePrice());
		c2cTransOrder.setRemark(remark);
		c2cTransOrder.setFlag("UNCOMPLETED");
		c2cTransOrder.setPayCode(transOrderId.substring(transOrderId.length() - 6, transOrderId.length()));
		// c2cTransOrder.
		// 保存单子,并记录操作
		int insertSelective = c2cTransOrderMapper.insertSelective(c2cTransOrder);
		if (insertSelective != 1) {
			throw new AppException(C2cCodeConst.TRANSORDER_CREATE_ERROR, "订单添加异常");
		}
		c2cTransOrderOperRecordService.saveTransOrderOperRecord(c2cTransOrder, uid, "",
				C2cTransOrderStatus.UNPAY.toString());
		// 返回成功创建的单号
		return transOrderId;
	}

	@Override
	@Transactional
	public void updateTransOrderToPaid(String c2cTransOrderId, Integer operaUid, Integer operRecordUid, String msg) {
		C2cTransOrder order = c2cTransOrderMapper.selectByTransOrderId(c2cTransOrderId);
		if (null == order) {
			throw new AppException(C2cCodeConst.INVALID_TRANSORDER, "C2C交易单不存在");
		}
		if (!C2cTransOrderStatus.UNPAY.equals(order.getStatus())) {
			throw new AppException(C2cCodeConst.TRANSORDER_STATUS_MATCH_ERROR, "C2C交易单创建时,状态只可以是未付");
		}
		if (operaUid.intValue() != order.getBuyUid().intValue()) {
			throw new AppException(C2cCodeConst.USER_MATCH_ORDER_ERROR, "C2C交易单确认支付只可以是交易对中的购买人");
		}
		// 超时检测
		if ((new Date().getTime() - order.getCreateDate().getTime()) > 30 * 60 * 1000) {
			throw new AppException(C2cCodeConst.OVERTIME_ERROR, "C2C交易单支付超时");
		}
		// 更新交易单状态
		if (order.getFlag().equals("UNCOMPLETED")) {
			String flag = order.getFlag().equals("UNCOMPLETED") ? "UNCOMPLETED" : "COMPLETED";
			int payResult = c2cTransOrderMapper.updateByPrimaryKeyAndStatus(order.getId(), C2cTransOrderStatus.UNPAY,
					C2cTransOrderStatus.PAID, flag);
			if (payResult != 1) {
				throw new AppException(C2cCodeConst.TRANSORDER_UPDATE_ERROR, "订单更新异常");
			}
		}
		c2cTransOrderOperRecordService.saveTransOrderOperRecord(order, operRecordUid, msg,
				C2cTransOrderStatus.PAID.toString());
		// 买家支付,通知卖家
		String sellerMsg = String.format(MessageConst.C2C_BUY_HAS_PAID_MESSAGE, order.getAssetCode(),
				order.getTransOrderId(), order.getMoney().setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString(), order.getNumber().stripTrailingZeros().toPlainString(),
				order.getAssetCode());
		c2cMessageFacade.sendMessageByUid(order.getSellUid(), sellerMsg);
	}

	// 买家确认打币
	@Override
	@Transactional
	public void updateTransOrderToFinishedAndTransforCoin(String c2cTransOrderId, Integer operaUid,
			Integer operRecordUid, String msg) {
		C2cTransOrder order = c2cTransOrderMapper.selectByTransOrderId(c2cTransOrderId);
		if (null == order) {
			throw new AppException(C2cCodeConst.INVALID_TRANSORDER, "C2C交易单不存在");
		}
		if (!C2cTransOrderStatus.PAID.equals(order.getStatus())) {
			throw new AppException(C2cCodeConst.TRANSORDER_STATUS_MATCH_ERROR, "C2C交易单完成时,状态只可以是支付");
		}
		if (operaUid.intValue() != order.getSellUid().intValue()) {
			throw new AppException(C2cCodeConst.USER_MATCH_ORDER_ERROR, "C2C交易单确认支付只可以是交易对中的购买人");
		}
		// 更新交易单状态
		if (order.getFlag().equals("UNCOMPLETED")) {
			String flag = order.getFlag().equals("UNCOMPLETED") ? "UNCOMPLETED" : "COMPLETED";
			int payResult = c2cTransOrderMapper.updateByPrimaryKeyAndStatus(order.getId(), C2cTransOrderStatus.PAID,
					C2cTransOrderStatus.FINISHED, "COMPLETED");
			if (payResult != 1) {
				throw new AppException(C2cCodeConst.TRANSORDER_UPDATE_ERROR, "订单更新异常");
			}
		}
		// 交易单流水记录
		C2cTransOrderRecord orderRecord = new C2cTransOrderRecord();
		orderRecord.setTransOrderId(order.getTransOrderId());
		orderRecord.setBuyUid(order.getBuyUid());
		orderRecord.setSellUid(order.getSellUid());
		orderRecord.setLockNum(order.getLockNum());
		orderRecord.setTransNum(order.getNumber());
		orderRecord.setUnlockNum(order.getLockNum().subtract(order.getNumber()));
		orderRecord.setOrderId(order.getOrderId());
		int orderRecordResult = c2cTransOrderRecordService.saveRecord(orderRecord);
		if (orderRecordResult != 1) {
			throw new AppException(C2cCodeConst.TRANSORDERLOG_RECORD_ERROR, "交易单记录失败");
		}
		// 打币步骤
		// 创建buyer计数对象
		C2cTransOrder transOrder = c2cTransOrderService.selectByTransOrderId(c2cTransOrderId);
		TokenOrderCompletionCount buyerCount = new TokenOrderCompletionCount();
		buyerCount.setUid(transOrder.getBuyUid());
		buyerCount.setBuyCount(BigDecimal.ONE);
		buyerCount.setTotalCount(BigDecimal.ONE);
		// 更新或插入buyer计数
		tokenOrderCompletionCountService.saveOrUpdateCount(buyerCount);
		// 创建seller计数对象
		TokenOrderCompletionCount sellerCount = new TokenOrderCompletionCount();
		sellerCount.setUid(transOrder.getSellUid());
		sellerCount.setSellCount(BigDecimal.ONE);
		sellerCount.setTotalCount(BigDecimal.ONE);
		// 更新或插入seller计数
		tokenOrderCompletionCountService.saveOrUpdateCount(sellerCount);
		// ************ finance 的处理 ************
		List<AssetOperationDto> assetOperationDtos = new ArrayList<AssetOperationDto>();
		// 买家dto
		AssetOperationDto assetOperationBuyDto = new AssetOperationDto();
		assetOperationBuyDto.setUid(transOrder.getBuyUid());
		assetOperationBuyDto.setRequestNo(transOrder.getBuyRequestNo());
		assetOperationBuyDto.setBusinessSubject(BusinessSubject.C2C_BUY);
		assetOperationBuyDto.setAssetCode(transOrder.getAssetCode());
		assetOperationBuyDto.setAmount(transOrder.getNumber());
		assetOperationBuyDto.setLockAmount(BigDecimal.ZERO);
		assetOperationBuyDto.setLoanAmount(BigDecimal.ZERO);
		assetOperationBuyDto.setAccountClass(AccountClass.ASSET);
		assetOperationBuyDto.setAccountSubject(AccountSubject.SYSTEM_TRANSFER_ASSET_PLUS);
		assetOperationBuyDto.setIndex(0);
		assetOperationDtos.add(assetOperationBuyDto);
		// 卖家dto
		AssetOperationDto assetOperationSellDto = new AssetOperationDto();
		assetOperationSellDto.setUid(transOrder.getSellUid());
		assetOperationSellDto.setRequestNo(transOrder.getSellRequestNo());
		assetOperationSellDto.setBusinessSubject(BusinessSubject.C2C_SELL);
		assetOperationSellDto.setAssetCode(transOrder.getAssetCode());
		assetOperationSellDto.setAmount(transOrder.getLockNum().subtract(transOrder.getNumber()));
		assetOperationSellDto.setLockAmount(transOrder.getLockNum().negate());
		assetOperationSellDto.setLoanAmount(BigDecimal.ZERO);
		assetOperationSellDto.setAccountClass(AccountClass.ASSET);
		assetOperationSellDto.setAccountSubject(AccountSubject.SYSTEM_TRANSFER_ASSET_LESS);
		assetOperationSellDto.setIndex(1);
		assetOperationDtos.add(assetOperationSellDto);
		// 卖家确认打币,双方发送信息
		String sellerAndBuyerMsg = String.format(MessageConst.C2C_SELL_HAS_TRANSFER_MESSAGE, order.getTransOrderId(),
				order.getMoney().setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString(), order.getNumber().stripTrailingZeros().toPlainString(), order.getAssetCode());
		c2cMessageFacade.sendMessageByUid(order.getSellUid(), sellerAndBuyerMsg);
		c2cMessageFacade.sendMessageByUid(order.getBuyUid(), sellerAndBuyerMsg);
		c2cTransOrderOperRecordService.saveTransOrderOperRecord(order, operRecordUid, msg,
				C2cTransOrderStatus.FINISHED.toString());

	}

	@Override
	@Transactional
	public void updateTransOrderToCancel(String c2cTransOrderId, Integer operaUid, Integer operRecordUid, String msg) {
		C2cTransOrder order = c2cTransOrderMapper.selectByTransOrderId(c2cTransOrderId);
		if (null == order) {
			throw new AppException(C2cCodeConst.INVALID_TRANSORDER, "C2C交易单不存在");
		}
		if (!C2cTransOrderStatus.UNPAY.equals(order.getStatus())) {
			throw new AppException(C2cCodeConst.TRANSORDER_STATUS_MATCH_ERROR, "C2C交易单取消时,状态只可以是未支付");
		}
		if (!Objects.equals(operaUid, order.getBuyUid())) {
			throw new AppException(C2cCodeConst.USER_MATCH_ORDER_ERROR, "C2C交易单确认支付只可以是交易对中的购买人");
		}
		// 记录用户取消交易单
		C2cUserCancelTransOrderCountRecord cancelRecord = new C2cUserCancelTransOrderCountRecord();
		cancelRecord.setUid(operaUid);
		cancelRecord.setTransOrderId(c2cTransOrderId);
		int cancelResult = c2cUserCancelTransOrderCountRecordService.recordCancel(cancelRecord);
		if (cancelResult != 1) {
			throw new AppException(C2cCodeConst.TRANSORDER_CANCEL_FAILED, "用户取消C2C交易单,记录失败");
		}
		// 更改卖单为展示
		C2cSellAdvertisement sellAdvert = c2cSellAdvertisementService.selectByAdvertId(order.getAdvertId());
		if (null == sellAdvert || sellAdvert.getStatus().equals(C2cSellAdvertStatus.SHOW)) {
			throw new AppException(C2cCodeConst.ADVERTID_IN_ORDER_FAULT, "订单中的存储的广告信息异常");
		}
		int sellAdvetResult = c2cSellAdvertisementService.updateC2cSellAdvertStatusByAdvertId(order.getAdvertId(),
				C2cSellAdvertStatus.SHOW, C2cSellAdvertStatus.PURCHASED);
		if (sellAdvetResult != 1) {
			throw new AppException(C2cCodeConst.ADVERT_UPDATE_FAILED, "广告更新异常");
		}
		// 更新交易单状态
		if (order.getFlag().equals("UNCOMPLETED")) {
			String flag = order.getFlag().equals("UNCOMPLETED") ? "UNCOMPLETED" : "COMPLETED";
			int payResult = c2cTransOrderMapper.updateByPrimaryKeyAndStatus(order.getId(), C2cTransOrderStatus.UNPAY,
					C2cTransOrderStatus.CANCELED, "COMPLETED");
			if (payResult != 1) {
				throw new AppException(C2cCodeConst.TRANSORDER_UPDATE_ERROR, "订单更新异常");
			}
		}

		c2cTransOrderOperRecordService.saveTransOrderOperRecord(order, operRecordUid, msg,
				C2cTransOrderStatus.FINISHED.toString());
		// 买家取消,通知买家
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = formatter.format(new Date());
		String BuyMsg = String.format(MessageConst.C2C_BUY_HAS_CANCEL_TO_BUY_MESSAGE, order.getAssetCode(),
				order.getTransOrderId(), order.getMoney().setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString(), order.getNumber().stripTrailingZeros().toPlainString(),
				order.getAssetCode(), date);
		c2cMessageFacade.sendMessageByUid(order.getBuyUid(), BuyMsg);
	}

	@Override
	@Transactional
	public void updateTransOrderToComplaint(String c2cTransOrderId, Integer operaUid, Integer operRecordUid,
			String msg) {
		// TODO
		C2cTransOrder order = c2cTransOrderMapper.selectByTransOrderId(c2cTransOrderId);
		if (null == order) {
			throw new AppException(C2cCodeConst.INVALID_TRANSORDER, "C2C交易单不存在");
		}
		if (!(C2cTransOrderStatus.PAID.equals(order.getStatus())
				|| C2cTransOrderStatus.FINISHED.equals(order.getStatus())
				|| C2cTransOrderStatus.OVERTIME.equals(order.getStatus())
				|| C2cTransOrderStatus.CANCELED.equals(order.getStatus()))) {
			throw new AppException(C2cCodeConst.TRANSORDER_STATUS_MATCH_ERROR, "C2C交易申诉,状态只可以是已付款,完成,取消,超时");
		}
		if (!Objects.equals(operaUid, order.getBuyUid()) && !Objects.equals(operaUid, order.getSellUid())) {
			throw new AppException(C2cCodeConst.USER_MATCH_ORDER_ERROR, "C2C交易单申诉只可以是交易对中的用户");
		}
		// 更新交易单状态
//		String flag = order.getFlag().equals("UNCOMPLETED") ? "UNCOMPLETED" : "COMPLETED";
		int payResult = c2cTransOrderMapper.updateByPrimaryKeyAndStatus(order.getId(), order.getStatus(),
				C2cTransOrderStatus.COMPLAINNING, "UNCOMPLETED");
		if (payResult != 1) {
			throw new AppException(C2cCodeConst.TRANSORDER_UPDATE_ERROR, "订单更新异常");
		}
		c2cTransOrderOperRecordService.saveTransOrderOperRecord(order, operRecordUid, msg,
				C2cTransOrderStatus.COMPLAINNING.toString());
		// 买卖方发送信息通知
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = formatter.format(new Date());
		if (operaUid == order.getBuyUid()) {
			String buyMsg = String.format(MessageConst.C2C_BUY_HAS_COMPLAIN_TO_BUY_MESSAGE, order.getAssetCode(),
					order.getTransOrderId(), date);
			c2cMessageFacade.sendMessageByUid(order.getBuyUid(), buyMsg);
			String sellMsg = String.format(MessageConst.C2C_BUY_HAS_COMPLAIN_TO_SELL_MESSAGE, order.getAssetCode(),
					order.getTransOrderId(), date);
			c2cMessageFacade.sendMessageByUid(order.getSellUid(), sellMsg);

		}
		if (operaUid == order.getSellUid()) {
			String buyMsg = String.format(MessageConst.C2C_SELL_HAS_COMPLAIN_TO_BUY_MESSAGE, order.getAssetCode(),
					order.getTransOrderId(), date);
			c2cMessageFacade.sendMessageByUid(order.getBuyUid(), buyMsg);
			String sellMsg = String.format(MessageConst.C2C_SELL_HAS_COMPLAIN_TO_SELL_MESSAGE, order.getAssetCode(),
					order.getTransOrderId(), date);
			c2cMessageFacade.sendMessageByUid(order.getSellUid(), sellMsg);
		}
	}

	@Override
	@Transactional
	public void updateTransOrderToOverTime(String c2cTransOrderId, Integer operaUid, Integer operRecordUid,
			String msg) {
		C2cTransOrder order = c2cTransOrderMapper.selectByTransOrderId(c2cTransOrderId);
		if (null == order) {
			throw new AppException(C2cCodeConst.INVALID_TRANSORDER, "C2C交易单不存在");
		}
		if (!C2cTransOrderStatus.UNPAY.equals(order.getStatus())) {
			throw new AppException(C2cCodeConst.TRANSORDER_STATUS_MATCH_ERROR, "C2C交易单超时,状态只可以是未支付");
		}
		if (!Objects.equals(operaUid, order.getBuyUid()) && !Objects.equals(operaUid, order.getSellUid())) {
			throw new AppException(C2cCodeConst.USER_MATCH_ORDER_ERROR, "C2C交易单确认支付只可以是交易对中的购买人");
		}
		// 超时检查
		Calendar currentDayBegin = new GregorianCalendar();
		currentDayBegin.set(Calendar.HOUR_OF_DAY, 0);
		currentDayBegin.set(Calendar.MINUTE, 0);
		currentDayBegin.set(Calendar.SECOND, 0);

		if (((new Date().getTime() - order.getCreateDate().getTime()) > 30 * 60 * 1000)
				&& order.getStatus().equals(C2cTransOrderStatus.UNPAY)) {
			// 更新未付状态数据
			// 更新交易单状态并记录操作
			if (order.getFlag().equals("UNCOMPLETED")) {
				// 未支付状态必须对应flag的UNcompleted
				String flag = order.getFlag().equals("UNCOMPLETED") ? "UNCOMPLETED" : "COMPLETED";
				int payResult = c2cTransOrderMapper.updateByPrimaryKeyAndStatus(order.getId(),
						C2cTransOrderStatus.UNPAY, C2cTransOrderStatus.OVERTIME, "COMPLETED");
				if (payResult != 1) {
					throw new AppException(C2cCodeConst.TRANSORDER_UPDATE_ERROR, "订单更新异常");
				}
				c2cTransOrderOperRecordService.saveTransOrderOperRecord(order, operRecordUid, msg,
						C2cTransOrderStatus.OVERTIME.toString());
			}
			// 记录当日取消次数
			if (currentDayBegin.getTimeInMillis() < order.getCreateDate().getTime()) {

				C2cUserCancelTransOrderCountRecord cancelRecord = new C2cUserCancelTransOrderCountRecord();
				cancelRecord.setUid(operaUid);
				cancelRecord.setTransOrderId(c2cTransOrderId);
				int cancelResult = c2cUserCancelTransOrderCountRecordService.recordCancel(cancelRecord);
				if (cancelResult != 1) {
					throw new AppException(C2cCodeConst.TRANSORDER_CANCEL_FAILED, "用户取消C2C交易单,记录失败");
				}
			}
		}
		// 恢复广告为展示
		C2cSellAdvertisement sellAdvert = c2cSellAdvertisementService.selectByAdvertId(order.getAdvertId());
		if (null == sellAdvert || sellAdvert.getStatus().equals(C2cSellAdvertStatus.SHOW)) {
			throw new AppException(C2cCodeConst.ADVERTID_IN_ORDER_FAULT, "订单中的存储的广告信息异常");
		}
		int sellAdvetResult = c2cSellAdvertisementService.updateC2cSellAdvertStatusByAdvertId(order.getAdvertId(),
				C2cSellAdvertStatus.SHOW, C2cSellAdvertStatus.PURCHASED);
		if (sellAdvetResult != 1) {
			throw new AppException(C2cCodeConst.TRANSORDER_UPDATE_ERROR, "广告更新异常");
		}

		// 支付超时,买家通知
		String BuyMsg = String.format(MessageConst.C2C_BUY_HAS_OVERTIME_TO_BUY_MESSAGE, order.getAssetCode(),
				order.getTransOrderId(), order.getMoney().setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString(), order.getNumber().stripTrailingZeros().toPlainString(),
				order.getAssetCode());
		c2cMessageFacade.sendMessageByUid(order.getBuyUid(), BuyMsg);

	}

	// 管理员修改调用更新订单申诉状态
	@Override
	@Transactional
	public void updateTransOrderFromComplaintToFinished(String c2cTransOrderId, Integer operaUid, Integer operRecordUid,
			String msg) {
		C2cTransOrder order = c2cTransOrderMapper.selectByTransOrderId(c2cTransOrderId);
		if (null == order) {
			throw new AppException(C2cCodeConst.INVALID_TRANSORDER, "C2C交易单不存在");
		}
//		if (!C2cTransOrderStatus.COMPLAINNING.equals(order.getStatus())) {
//			throw new AppException(C2cCodeConst.TRANSORDER_STATUS_MATCH_ERROR, "管理员强制打币时,C2C交易单状态只可以是申诉中");
//		}
		if (operaUid.intValue() != order.getBuyUid().intValue() && operaUid.intValue() != order.getSellUid().intValue()) {
			throw new AppException(C2cCodeConst.USER_MATCH_ORDER_ERROR, "C2C交易单确认支付只可以是交易对中的购买人");
		}
		// 更新交易单状态
		if (order.getFlag().equals("UNCOMPLETED")) {
			String flag = order.getFlag().equals("UNCOMPLETED") ? "UNCOMPLETED" : "COMPLETED";
			int payResult = c2cTransOrderMapper.updateByPrimaryKeyAndStatus(order.getId(),
					C2cTransOrderStatus.COMPLAINNING, C2cTransOrderStatus.FINISHED, "COMPLETED");
			if (payResult != 1) {
				throw new AppException(C2cCodeConst.TRANSORDER_UPDATE_ERROR, "订单更新异常");
			}
		}
		c2cTransOrderOperRecordService.saveTransOrderOperRecord(order, operRecordUid, msg,
				C2cTransOrderStatus.FINISHED.toString());
	}

	// 管理员强制关闭
	@Override
	@Transactional
	public boolean updatetransOrderToClosed(String c2cTransOrderId, Integer operaUid, Integer operRecordUid,
			String msg) {
		C2cTransOrder order = c2cTransOrderMapper.selectByTransOrderId(c2cTransOrderId);
		if (null == order) {
			throw new AppException(C2cCodeConst.INVALID_TRANSORDER, "C2C交易单不存在");
		}
		if (!C2cTransOrderStatus.COMPLAINNING.equals(order.getStatus())) {
			throw new AppException(C2cCodeConst.TRANSORDER_STATUS_MATCH_ERROR, "管理员强制打币时,C2C交易单状态只可以是申诉中");
		}
		if (!Objects.equals(operaUid, order.getBuyUid()) && !Objects.equals(operaUid, order.getSellUid())) {
			throw new AppException(C2cCodeConst.USER_MATCH_ORDER_ERROR, "C2C交易单确认支付只可以是交易对中的购买人");
		}
		// 更新交易单状态
		if (order.getFlag().equals("UNCOMPLETED")) {
			String flag = order.getFlag().equals("UNCOMPLETED") ? "UNCOMPLETED" : "COMPLETED";
			int payResult = c2cTransOrderMapper.updateByPrimaryKeyAndStatus(order.getId(),
					C2cTransOrderStatus.COMPLAINNING, C2cTransOrderStatus.CLOSED, "COMPLETED");
			if (payResult != 1) {
				throw new AppException(C2cCodeConst.TRANSORDER_UPDATE_ERROR, "订单更新异常");
			}
		}

		c2cTransOrderOperRecordService.saveTransOrderOperRecord(order, operRecordUid, msg,
				C2cTransOrderStatus.CLOSED.toString());

		return true;
	}

	/**
	 * sellerUid与buyerUid不能同时为都有
	 */
	@Override
	@Transactional
	public List<C2cTransOrder> queryByflagAndPayTypeAndStatus(Integer seller, Integer buyer, String completed,
			C2cTransOrderStatus status, Integer pageNo, Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize, true);
		PageHelper.orderBy("create_date desc");
		return c2cTransOrderMapper.queryByflagAndPayTypeAndStatus(seller, buyer, completed, status);
	}

	@Override
	public List<C2cTransOrder> queryBuyerByUncompletedStatus(Integer uid, C2cTransOrderStatus status) {
		return c2cTransOrderMapper.queryBuyerByUncompletedStatus(uid, status);
	}

	@Override
	public List<C2cTransOrder> querySellerByUncompletedStatus(Integer uid, C2cTransOrderStatus status) {
		return c2cTransOrderMapper.querySellerByUncompletedStatus(uid, status);
	}

	@Override
	public List<C2cTransOrder> selectByStatus(C2cTransOrderStatus status) {
		return c2cTransOrderMapper.selectByStatus(status);
	}

	@Override
	public PageModel<C2cTransOrderDto> managerOrderList(Integer pageNo, Integer pageSize, C2cTransOrderStatus status,
			Integer uid, C2cTransType orderType) {
		// 步骤一:
		// 添加计数器
		// int count = 0;
		// 对用户有关联的订单表进行脏数据处理
		List<C2cTransOrder> checklist = Lists.newArrayList();
		checklist.addAll(querySellerByUncompletedStatus(uid, C2cTransOrderStatus.UNPAY));
		checklist.addAll(queryBuyerByUncompletedStatus(uid, C2cTransOrderStatus.UNPAY));
		for (C2cTransOrder c2cTransOrder : checklist) {
			// 超时校验,并且更新超时状态
			if (C2cTransOrderStatus.UNPAY.equals(c2cTransOrder.getStatus())
					&& (new Date().getTime() - c2cTransOrder.getCreateDate().getTime() > 30 * 60 * 1000)) {

				updateTransOrderToOverTime(c2cTransOrder.getTransOrderId(), c2cTransOrder.getBuyUid(),
						c2cTransOrder.getBuyUid(), "");
				// 计数器加1;!!线程可能不安全?
				// count++;
				continue;
			}
		}
		// 如果计数器大于一,有脏数据,递归,调用自己
		// if (count > 0) {
		// return managerOrderList(pageNo, pageSize, status, uid, orderType);
		// }
		// 步骤二:
		// 用户分页查询
		String completed = C2cTransOrderStatus.COMPLETED.equals(status) ? "COMPLETED" : null;
		C2cTransOrderStatus queryStatus = C2cTransOrderStatus.COMPLETED.equals(status) ? null : status;
		List<C2cTransOrder> list = Lists.newArrayList();
		if (orderType.equals(C2cTransType.BUY)) {
			list = c2cTransOrderService.queryByflagAndPayTypeAndStatus(null, uid, completed, queryStatus, pageNo,
					pageSize);
		}
		if (orderType.equals(C2cTransType.SELL)) {
			list = c2cTransOrderService.queryByflagAndPayTypeAndStatus(uid, null, completed, queryStatus, pageNo,
					pageSize);
		}
		if (null == list) {
			return new PageModel<C2cTransOrderDto>();
		}
		List<C2cTransOrderDto> dtoList = Lists.newArrayList();
		for (C2cTransOrder transOrder : list) {

			C2cTransOrderDto dto = new C2cTransOrderDto(transOrder);
			C2cOrderPaymentDetail detail = new C2cOrderPaymentDetail();
			if (C2cPayType.ALIPAY.toString().equals(transOrder.getBuyPayType())) {
				detail = c2cOrderPaymentDetailService.selectDetailByAdvertIdAndPaytype(transOrder.getAdvertId(),
						C2cPayType.ALIPAY);
				C2cAlipayInfo info = c2cAlipayInfoService.selectById(detail.getPayChannelId());
				C2cAlipayInfoDto c2cAlipayInfoDto = C2cAlipayInfoDto.builder().build();
				c2cAlipayInfoDto.setC2cPayType(C2cPayType.ALIPAY);
				c2cAlipayInfoDto.setUid(info.getUid());
				c2cAlipayInfoDto.setName(info.getName());
				c2cAlipayInfoDto.setAlipayNo(info.getAlipayNo());
				c2cAlipayInfoDto.setCreateDate(info.getCreateDate());
				c2cAlipayInfoDto.setUpdateDate(info.getUpdateDate());
				dto.setDto(c2cAlipayInfoDto);
			}
			if (C2cPayType.BANK.toString().equals(transOrder.getBuyPayType())) {
				detail = c2cOrderPaymentDetailService.selectDetailByAdvertIdAndPaytype(transOrder.getAdvertId(),
						C2cPayType.BANK);
				C2cBankInfo info = c2cBankInfoService.selectById(detail.getPayChannelId());
				C2cBankInfoDto c2cBankInfoDto = C2cBankInfoDto.builder().build();
				c2cBankInfoDto.setAcnumber(info.getAcnumber());
				c2cBankInfoDto.setBank(info.getBank());
				c2cBankInfoDto.setC2cPayType(C2cPayType.BANK);
				c2cBankInfoDto.setSubBank(info.getSubbank());
				c2cBankInfoDto.setName(info.getName());
				dto.setDto(c2cBankInfoDto);
			}
			dtoList.add(dto);
		}
		PageInfo<C2cTransOrderDto> pageInfo = new PageInfo<>(dtoList);
		PageInfo<C2cTransOrder> orderPageInfo = new PageInfo<>(list);
		PageModel<C2cTransOrderDto> pageModel = new PageModel<>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(pageSize);
		pageModel.setPageNum(orderPageInfo.getPages());
		pageModel.setList(pageInfo.getList());
		pageModel.setTotal(orderPageInfo.getTotal());
		return pageModel;
	}
}
