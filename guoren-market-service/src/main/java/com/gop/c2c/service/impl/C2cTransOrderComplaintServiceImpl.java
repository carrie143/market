package com.gop.c2c.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.gop.asset.dto.AssetOperationDto;
import com.gop.c2c.service.C2cOrderCancelRecordService;
import com.gop.c2c.service.C2cOrderRecordService;
import com.gop.c2c.service.C2cSellAdvertisementService;
import com.gop.c2c.service.C2cTransOrderComplaintService;
import com.gop.c2c.service.C2cTransOrderRecordService;
import com.gop.c2c.service.C2cTransOrderService;
import com.gop.code.consts.C2cCodeConst;
import com.gop.code.consts.CommonCodeConst;
import com.gop.domain.C2cOrderCancelRecord;
import com.gop.domain.C2cOrderRecord;
import com.gop.domain.C2cSellAdvertisement;
import com.gop.domain.C2cTransOrder;
import com.gop.domain.C2cTransOrderComplaint;
import com.gop.domain.C2cTransOrderRecord;
import com.gop.domain.TokenOrderCompletionCount;
import com.gop.domain.User;
import com.gop.domain.enums.C2cComplaintStatus;
import com.gop.domain.enums.C2cSellAdvertStatus;
import com.gop.domain.enums.C2cTransOrderStatus;
import com.gop.domain.enums.C2cTransType;
import com.gop.exception.AppException;
import com.gop.financecheck.enums.AccountClass;
import com.gop.financecheck.enums.AccountSubject;
import com.gop.financecheck.enums.BusinessSubject;
import com.gop.mapper.C2cTransOrderComplaintMapper;
import com.gop.offlineorder.service.TokenOrderCompletionCountService;
import com.gop.user.facade.UserFacade;
import com.gop.util.OrderUtil;
import com.gop.util.SequenceUtil;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author zhushengtao
 *
 */
@Slf4j
@Service("C2cTransOrderComplaintService")
public class C2cTransOrderComplaintServiceImpl implements C2cTransOrderComplaintService {
	@Autowired
	private C2cTransOrderComplaintMapper c2cTransOrderComplaintMapper;
	@Autowired
	private C2cTransOrderService c2cTransOrderService;
	@Autowired
	private C2cSellAdvertisementService c2cSellAdvertisementService;
	@Autowired
	private C2cOrderRecordService c2cOrderRecordService;
	@Autowired
	private C2cOrderCancelRecordService c2cOrderCancelRecordService;
	@Autowired
	private TokenOrderCompletionCountService tokenOrderCompletionCountService;
	@Autowired
	private C2cTransOrderRecordService c2cTransOrderRecordService;
	@Autowired
	private UserFacade userFacade;

	@Override
	@Transactional
	public void creatComplaint(C2cTransOrderComplaint complaint, Integer uid, String phone) {
		// 一个交易单只能申诉一次
		if (null == complaint.getTransOrderId()) {
			throw new AppException(C2cCodeConst.INVALID_COMPLAINT, "C2C申诉单不可以无交易单号");
		}
		int complaintInDB = c2cTransOrderComplaintMapper.selectByTransOrderId(complaint.getTransOrderId());
		if (complaintInDB > 0) {
			throw new AppException(C2cCodeConst.COMPLAINT_CREATED, "C2C交易单不可以重复申诉");
		}
		// 校验申诉人与类型
		C2cTransOrder transOrder = c2cTransOrderService.selectByTransOrderId(complaint.getTransOrderId());
		if (null == transOrder) {
			throw new AppException(C2cCodeConst.INVALID_TRANSORDER,"C2C交易单不存在");
		}
		User seller = userFacade.getUser(transOrder.getSellUid());
		User buyer = userFacade.getUser(transOrder.getBuyUid());
		// 判断申诉类型
		if (uid.equals(transOrder.getBuyUid())) {
			complaint.setComplainType(C2cTransType.BUY);
			complaint.setBuyPhone(phone);
			complaint.setSellPhone(seller.getMobile());
		}
		// 判断申诉类型
		if (uid.equals(transOrder.getSellUid())) {
			complaint.setComplainType(C2cTransType.SELL);
			complaint.setSellPhone(phone);
			complaint.setBuyPhone(buyer.getMobile());
		}
		if (!(uid.equals(transOrder.getBuyUid()) || uid.equals(transOrder.getSellUid()))) {
			throw new AppException(C2cCodeConst.USER_MATCH_COMPLAINT_ERROR, "申诉人uid与订单信息不匹配");
		}
		// 生成申诉单号
		String complaintId = OrderUtil.generateC2cCode();
		complaint.setComplainId(complaintId);
		complaint.setOrderId(transOrder.getOrderId());
		complaint.setUid(uid);
		complaint.setTransOrderStatus(transOrder.getStatus());
		int result = c2cTransOrderComplaintMapper.insertSelective(complaint);
		if (result != 1) {
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "生成申诉单失败");
		}
		// 修改订单状态为申诉中(方法内包含订单状态判断)
		c2cTransOrderService.updateTransOrderToComplaint(transOrder.getTransOrderId(), uid, uid, "");
	}

	@Override
	public List<C2cTransOrderComplaint> queryByStatusAndComplainType(Integer pageNo,Integer pageSize,C2cComplaintStatus status,C2cTransType complainType) {
		PageHelper.startPage(pageNo, pageSize);
		PageHelper.orderBy("complain_id asc");
		return c2cTransOrderComplaintMapper.selectByStatusAndComplainType(status,complainType);
	}

	@Override
	public C2cTransOrderComplaint selectByComplainId(String complainId) {
		return c2cTransOrderComplaintMapper.selectByComplainId(complainId);
	}

	// 管理员强制打币
	@Override
	@Transactional
	public void forceTransforCoin(String complainId, Integer managerUid) {
		C2cTransOrderComplaint complaint = c2cTransOrderComplaintMapper.selectByComplainId(complainId);
		// 申诉单校验
		if (null == complaint) {
			throw new AppException(C2cCodeConst.INVALID_COMPLAINT, "申诉单不存在");
		}
		if (null == complaint.getTransOrderId()) {
			throw new AppException(C2cCodeConst.ORDER_IN_COMPLAINT_FAULT, "申诉单中未找到交易单号");
		}
		C2cTransOrder transOrder = c2cTransOrderService.selectByTransOrderId(complaint.getTransOrderId());
		if (null == transOrder) {
			throw new AppException(C2cCodeConst.ORDER_IN_COMPLAINT_INEXISTENCE, "申诉单中存储的交易单不存在");
		}
		if (!C2cTransOrderStatus.COMPLAINNING.equals(transOrder.getStatus())) {
			throw new AppException(C2cCodeConst.TRANSORDER_STATUS_NO_COMPLAINT, "交易单状态异常,只能是申诉中");
		}
		// 申诉单修改为已完成
		int result = c2cTransOrderComplaintMapper.updateToProcesedByComplainId(complainId, transOrder.getTransOrderId(),
				managerUid,C2cComplaintStatus.PROCESSING,C2cComplaintStatus.PROCESSED);
		if (result != 1) {
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "更新申诉单出现异常");
		}
		// 交易单修改为已关闭
		String orderRecordsg = "uid为"+complaint.getUid()+"所提起的交易单被管理员:"+managerUid+"强制打币";
		boolean updatetransOrderToClosed = c2cTransOrderService.updatetransOrderToClosed(transOrder.getTransOrderId(), complaint.getUid(), managerUid, orderRecordsg);
		if (!updatetransOrderToClosed) {
			throw new AppException(C2cCodeConst.TRANSORDER_UPDATE_ERROR,"订单更新失败");
		}
		// 交易单流水记录
		C2cTransOrderRecord orderRecord = new C2cTransOrderRecord();
		orderRecord.setTransOrderId(transOrder.getTransOrderId());
		orderRecord.setBuyUid(transOrder.getBuyUid());
		orderRecord.setSellUid(transOrder.getSellUid());
		orderRecord.setLockNum(transOrder.getLockNum());
		orderRecord.setTransNum(transOrder.getNumber());
		orderRecord.setUnlockNum(transOrder.getLockNum().subtract(transOrder.getNumber()));
		orderRecord.setOrderId(transOrder.getOrderId());
		int orderRecordResult = c2cTransOrderRecordService.saveRecord(orderRecord);
		if (orderRecordResult != 1) {
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "交易单记录失败");
		}
		// 强制打币
		String msg = "管理员:" + managerUid + ",强制将用户uid为:" + transOrder.getSellUid() + "的" + transOrder.getNumber() + "个"
				+ transOrder.getAssetCode() + "转给uid为:" + transOrder.getBuyUid() + "的用户";

		// *********
		c2cTransOrderService.updateTransOrderFromComplaintToFinished(transOrder.getTransOrderId(),
				transOrder.getSellUid(), managerUid, msg);

		// 创建buyer计数对象
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
		assetOperationSellDto.setLockAmount(transOrder.getNumber().negate());
		assetOperationSellDto.setLoanAmount(BigDecimal.ZERO);
		assetOperationSellDto.setAccountClass(AccountClass.ASSET);
		assetOperationSellDto.setAccountSubject(AccountSubject.SYSTEM_TRANSFER_ASSET_LESS);
		assetOperationSellDto.setIndex(1);
		assetOperationDtos.add(assetOperationSellDto);
		// 给聚币用户加数字资产
//		userAccountFacade.assetOperation(assetOperationDtos);
	}

	@Override
	@Transactional
	public void forceCloseComplaint(String complainId, Integer managerUid) {
		// 申诉单校验
		C2cTransOrderComplaint complaint = c2cTransOrderComplaintMapper.selectByComplainId(complainId);
		if (null == complaint) {
			throw new AppException(C2cCodeConst.INVALID_COMPLAINT, "申诉单不存在");
		}
		if (null == complaint.getTransOrderId()) {
			throw new AppException(C2cCodeConst.ORDER_IN_COMPLAINT_FAULT, "申诉单中未找到交易单号");
		}
		// 订单校验
		C2cTransOrder transOrder = c2cTransOrderService.selectByTransOrderId(complaint.getTransOrderId());
		if (null == transOrder) {
			throw new AppException(C2cCodeConst.ORDER_IN_COMPLAINT_INEXISTENCE, "申诉单中存储的交易单号异常");
		}
		if (!C2cTransOrderStatus.COMPLAINNING.equals(transOrder.getStatus())) {
			throw new AppException(C2cCodeConst.TRANSORDER_STATUS_NO_COMPLAINT, "交易单状态异常,只能是申诉中");
		}
		// 订单状态修改为已关闭
		String msg = "管理员:" + managerUid + "将申诉单:" + complainId + ",强制关闭;用户uid为:" + transOrder.getSellUid() + "的资产:"
				+ transOrder.getNumber() + "个" + transOrder.getAssetCode() + "解冻.";
		// 广告单校验
		String advertId = transOrder.getAdvertId();

		C2cSellAdvertisement c2cSellAdvertisement = c2cSellAdvertisementService.selectByAdvertId(advertId);
		if (!C2cSellAdvertStatus.PURCHASED.equals(c2cSellAdvertisement.getStatus())) {
			throw new AppException(C2cCodeConst.ADVERT_HAS_PURCHASED,"广告更新异常");
		}
		Integer uid = c2cSellAdvertisement.getUid();

		try {
			// 更新订单状态
			boolean updatetransOrderToClosed = c2cTransOrderService.updatetransOrderToClosed(transOrder.getTransOrderId(), transOrder.getSellUid(),
					managerUid, msg);
			if (!updatetransOrderToClosed) {
				throw new AppException(C2cCodeConst.TRANSORDER_UPDATE_ERROR,"交易单更新异常");
			}
			// 更新申诉单
			// 申诉单修改为已完成
			int result = c2cTransOrderComplaintMapper.updateToProcesedByComplainId(complainId, transOrder.getTransOrderId(),
					managerUid,C2cComplaintStatus.PROCESSING,C2cComplaintStatus.PROCESSED);
			if (result != 1) {
				throw new AppException(CommonCodeConst.SERVICE_ERROR, "更新申诉单出现异常");
			}
			// 交易单流水记录
			C2cTransOrderRecord orderRecord = new C2cTransOrderRecord();
			orderRecord.setTransOrderId(transOrder.getTransOrderId());
			orderRecord.setBuyUid(transOrder.getBuyUid());
			orderRecord.setSellUid(transOrder.getSellUid());
			orderRecord.setLockNum(transOrder.getLockNum());
			orderRecord.setTransNum(BigDecimal.ZERO);
			orderRecord.setUnlockNum(transOrder.getLockNum());
			orderRecord.setOrderId(transOrder.getOrderId());
			int orderRecordResult = c2cTransOrderRecordService.saveRecord(orderRecord);
			if (orderRecordResult != 1) {
				throw new AppException(C2cCodeConst.TRANSORDERLOG_RECORD_ERROR, "交易单记录失败");
			}
			// 1.更新广告状态为已删除
			int advertResult = c2cSellAdvertisementService.updateC2cSellAdvertStatusByAdvertId(advertId, C2cSellAdvertStatus.DELETED,
					C2cSellAdvertStatus.PURCHASED);
			if (advertResult != 1) {
				throw new AppException(C2cCodeConst.ADVERT_HAS_DELETED);
			}
			// 2.取消订单表新增一条记录 用于对账查账
			C2cOrderRecord c2cOrderRecord = c2cOrderRecordService.selectOrderByAdvertId(advertId);
			String orderId = c2cOrderRecord.getOrderId();
			String cancelOrderId = SequenceUtil.getNextId();
			String assetCode = c2cSellAdvertisement.getAssetCode();
			String requestNo = OrderUtil.generateC2cCode();
			BigDecimal returnNum = c2cSellAdvertisement.getLockNum();
			BigDecimal tradePrice = c2cSellAdvertisement.getTradePrice();

			C2cOrderCancelRecord c2cOrderCancelRecord = new C2cOrderCancelRecord();
			c2cOrderCancelRecord.setUid(c2cSellAdvertisement.getUid());
			c2cOrderCancelRecord.setOrderId(orderId);
			c2cOrderCancelRecord.setCancelOrderId(cancelOrderId);
			c2cOrderCancelRecord.setRequestNo(requestNo);
			c2cOrderCancelRecord.setAssetCode(c2cSellAdvertisement.getAssetCode());
			c2cOrderCancelRecord.setReturnNum(c2cSellAdvertisement.getLockNum());
			c2cOrderCancelRecord.setPrice(c2cSellAdvertisement.getTradePrice());
			c2cOrderCancelRecord.setMoney(returnNum.multiply(tradePrice));
			c2cOrderCancelRecordService.addC2cOrderCancelRecordByOrderId(c2cOrderCancelRecord);

			// 3.解冻用户资产金额
			AssetOperationDto assetOperationDto = new AssetOperationDto();
			assetOperationDto.setUid(uid);
			assetOperationDto.setRequestNo(requestNo);
			assetOperationDto.setBusinessSubject(BusinessSubject.UNLOCK);
			assetOperationDto.setAssetCode(assetCode);
			assetOperationDto.setAmount(returnNum);
			assetOperationDto.setLockAmount(returnNum.negate());
			assetOperationDto.setLoanAmount(BigDecimal.ZERO);
			assetOperationDto.setAccountClass(AccountClass.ASSET);
			assetOperationDto.setAccountSubject(AccountSubject.SYSTEM_TRANSFER_ASSET_PLUS);
			assetOperationDto.setIndex(0);
			List<AssetOperationDto> assetOperationDtos = Lists.newArrayList(assetOperationDto);
//			userAccountFacade.assetOperation(assetOperationDtos);

		} catch (Exception e) {
			log.error("用户发布删除广告异常 userId={},eMessage=" + e.getMessage(), uid, e);
			Throwables.propagateIfInstanceOf(e, AppException.class);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
	}

}
