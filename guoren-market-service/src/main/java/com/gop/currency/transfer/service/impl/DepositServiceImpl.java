package com.gop.currency.transfer.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gop.asset.dto.AssetOperationDto;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.MessageConst;
import com.gop.code.consts.RechargeCodeConst;
import com.gop.common.Environment;
import com.gop.common.SmsMessageService;
import com.gop.config.SmsMessageConfig;
import com.gop.currency.transfer.service.DepositCurrencyService;
import com.gop.domain.DepositCurrencyOrderUser;
import com.gop.domain.enums.DepositCurrencyOrderStatus;
import com.gop.exception.AppException;
import com.gop.financecheck.enums.AccountClass;
import com.gop.financecheck.enums.AccountSubject;
import com.gop.financecheck.enums.BusinessSubject;
import com.gop.mapper.DepositCurrencyOrderUserMapper;
import com.gop.user.dto.UserSimpleInfoDto;
import com.gop.user.facade.UserFacade;
import com.gop.user.service.UserMessageService;
import com.gop.util.BigDecimalUtils;
import com.gop.util.DateUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DepositServiceImpl implements DepositCurrencyService {

	@Autowired
	Environment environmentContxt;

	@Autowired
	private UserMessageService userMessageService;

	@Autowired
	private SmsMessageService smsMessageService;

	@Autowired
	private UserFacade userFace;

	@Autowired
	private DepositCurrencyOrderUserMapper depositCurrencyOrderUserMapper;

	@Override
	@Transactional
	public void closeOrder(int orderKey) {
		DepositCurrencyOrderUser order = depositCurrencyOrderUserMapper.selectForUpdate(orderKey);
		if (order == null) {
			log.info("锁定订单失败");
			return;
		}
		if (order.getStatus().equals(DepositCurrencyOrderStatus.WAIT)) {
			order.setStatus(DepositCurrencyOrderStatus.FAILURE);
			order.setUpdateDate(new Date());
			depositCurrencyOrderUserMapper.updateByPrimaryKey(order);
		} else {
			log.info("订单{}状态已变为非WAIT状态", order.getTxid());
		}
	}

	@Override
	@Transactional
	public void confirm(int id, int adminId) {
		DepositCurrencyOrderUser order = depositCurrencyOrderUserMapper.selectForUpdate(id);
		if (null == order) {
			throw new AppException(RechargeCodeConst.RECHARGE_ORDER_NOT_EXIST, "充值订单不存在");
		}

		if (!order.getStatus().equals(DepositCurrencyOrderStatus.WAIT)) {
			log.info("订单状态不匹配");
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "订单状态异常");
		}
		// 人民币充值确认
		order.setStatus(DepositCurrencyOrderStatus.SUCCESS);
		order.setAdminId(adminId);
		if (!BigDecimalUtils.isBigger(order.getPay(), BigDecimal.ZERO)) {
			throw new AppException(RechargeCodeConst.RECHARGE_ORDER_NOT_EXIST, "无效充值订单");
		}

		BigDecimal pay = order.getPay();
		String txid = order.getTxid();
		Integer uid = order.getUid();

		List<AssetOperationDto> ops = new ArrayList<>();

		AssetOperationDto depositDto = new AssetOperationDto();
		depositDto.setAccountClass(AccountClass.LIABILITY);
		depositDto.setAccountSubject(AccountSubject.DEPOSIT_COMMON);
		depositDto.setAssetCode(order.getAssetCode());
		depositDto.setBusinessSubject(BusinessSubject.DEPOSIT);
		depositDto.setAmount(pay);
		depositDto.setLoanAmount(BigDecimal.ZERO);
		depositDto.setLockAmount(BigDecimal.ZERO);
		depositDto.setMemo(order.getAssetCode() + "充值");
		depositDto.setRequestNo(txid);
		depositDto.setUid(uid);
		ops.add(depositDto);

		order.setUpdateDate(new Date());

		if (depositCurrencyOrderUserMapper.updateByPrimaryKey(order) <= 0) {
			log.error("人民币充值确认时出错，id{}", order.getId());
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "人民币充值确认：更新转帐表时出错");
		}

		sendMessage(order);
	}

	@Override
	@Transactional
	public void confirm(String txid, int adminId, BigDecimal amount) {

		DepositCurrencyOrderUser order = depositCurrencyOrderUserMapper.getTransferCnyByTxid(txid);

		if (null == order) {
			throw new AppException(RechargeCodeConst.USER_ORDER_NOT_EXIST, "充值订单不存在");
		}

		order = depositCurrencyOrderUserMapper.selectForUpdate(order.getId());
		if (!order.getStatus().equals(DepositCurrencyOrderStatus.PROCESSING)) {

			throw new AppException(RechargeCodeConst.USER_ORDER_HAS_CHANGE, "订单状态异常");
		}
		// 人民币充值确认

		order.setStatus(DepositCurrencyOrderStatus.SUCCESS);
		order.setMoney(amount);

		BigDecimal fee = BigDecimal.ZERO;

		order.setPay(amount.subtract(fee));
		order.setFee(fee);

		order.setAdminId(adminId);
		if (!BigDecimalUtils.isBigger(order.getPay(), BigDecimal.ZERO)) {
			throw new AppException(RechargeCodeConst.RECHARGE_ORDER_NOT_EXIST, "无效充值订单");
		}

		BigDecimal pay = order.getPay();

		Integer uid = order.getUid();

		order.setUpdateDate(new Date());

		if (depositCurrencyOrderUserMapper.updateByPrimaryKey(order) <= 0) {
			log.error("人民币充值确认时出错，id{}", order.getId());
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "人民币充值确认：更新转帐表时出错");
		}

		List<AssetOperationDto> ops = new ArrayList<>();

		AssetOperationDto depositDto = new AssetOperationDto();
		depositDto.setAccountClass(AccountClass.LIABILITY);
		depositDto.setAccountSubject(AccountSubject.DEPOSIT_COMMON);
		depositDto.setAssetCode(order.getAssetCode());
		depositDto.setBusinessSubject(BusinessSubject.DEPOSIT);
		depositDto.setAmount(pay);
		depositDto.setLoanAmount(BigDecimal.ZERO);
		depositDto.setLockAmount(BigDecimal.ZERO);
		depositDto.setMemo(order.getAssetCode() + "充值");
		depositDto.setRequestNo(txid);
		depositDto.setUid(uid);
		ops.add(depositDto);
		// if (BigDecimalUtils.isBiggerZero(fee)) {
		// AssetOperationDto feeDto = new AssetOperationDto();
		// feeDto.setAccountClass(AccountClass.LIABILITY);
		// feeDto.setAccountSubject(AccountSubject.FEE_DEPOSIT_THIRD_INCOME);
		// feeDto.setAmount(fee);
		// feeDto.setAssetCode(transferCny.getAssetCode());
		// feeDto.setBusinessSubject(BusinessSubject.FEE);
		// feeDto.setLoanAmount(BigDecimal.ZERO);
		// feeDto.setLockAmount(BigDecimal.ZERO);
		// feeDto.setMemo(transferCny.getMsg());
		// feeDto.setRequestNo(txid);
		// feeDto.setUid(uid);
		// ops.add(feeDto);
		// }

		sendMessage(order);

	}

	@Override
	@Transactional
	public void changeStatus(String txid, DepositCurrencyOrderStatus exceptStatus,
			DepositCurrencyOrderStatus changeStatus) {

		DepositCurrencyOrderUser order = depositCurrencyOrderUserMapper.getTransferCnyByTxid(txid);
		if (order == null) {
			log.error("充值订单{}不存在", txid);
			throw new AppException(RechargeCodeConst.USER_ORDER_NOT_EXIST, "充值订单不存在");
		}
		order = depositCurrencyOrderUserMapper.selectForUpdate(order.getId());

		if (!order.getStatus().equals(exceptStatus)) {
			log.info("订单状态与期望状态不一致");
			throw new AppException(RechargeCodeConst.USER_ORDER_HAS_CHANGE, "用户订单状态已经改变");
		}

		order.setStatus(changeStatus);
		order.setUpdateDate(new Date());

		depositCurrencyOrderUserMapper.updateByPrimaryKeySelective(order);

	}

	public String generateDepositSuccMessage(DepositCurrencyOrderUser order) {

		String message = null;

		switch (order.getStatus()) {
		case SUCCESS:
			message = environmentContxt.getMsg(MessageConst.CASH_DEPOSIT_SUCCESS_MESSAGE,
					order.getMoney().setScale(2, RoundingMode.FLOOR).toString(), order.getAssetCode(),
					DateUtils.formatDate(order.getCreateDate()));
			break;
		default:
			break;
		}
		return message;
	}

	private void sendMessage(DepositCurrencyOrderUser order) {
		try {
			String message = generateDepositSuccMessage(order);
			userMessageService.insertMessage(order.getUid(), message);

			UserSimpleInfoDto user = userFace.getUserInfoByUid(order.getUid());

			smsMessageService.sendEmailMessage(user.getUserAccount(), message);

		} catch (Exception e) {
			log.info("给用户发送消息失败");
		}
	}

}
