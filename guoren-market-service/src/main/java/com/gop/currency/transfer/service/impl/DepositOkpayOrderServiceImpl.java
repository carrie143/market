package com.gop.currency.transfer.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.asset.dto.UserAccountDto;
import com.gop.code.consts.AccountCodeConst;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.RechargeCodeConst;
import com.gop.code.consts.UserAssetCodeConst;
import com.gop.common.Environment;
import com.gop.config.OkpayConstants;
import com.gop.currency.transfer.dto.DepositOrderReturnDto;
import com.gop.currency.transfer.service.ChannelCurrencyDepositOrderService;
import com.gop.currency.transfer.service.DepositCurrencyQueryOrderService;
import com.gop.currency.transfer.utils.OkpayRuleUtil;
import com.gop.domain.ChannelOkpayGlobalConfig;
import com.gop.domain.ChannelOkpayUserRules;
import com.gop.domain.DepositCurrencyOrderUser;
import com.gop.domain.enums.DepositCurrencyOrderStatus;
import com.gop.domain.enums.DepositCurrencyPayMode;
import com.gop.domain.enums.SwitchStatus;
import com.gop.exception.AppException;
import com.gop.mapper.ChannelOkpayGlobalConfigMapper;
import com.gop.mapper.ChannelOkpayUserRulesMapper;
import com.gop.mapper.DepositCurrencyOrderUserMapper;
import com.gop.user.dto.UserSimpleInfoDto;
import com.gop.user.facade.UserFacade;
import com.gop.util.OrderUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DepositOkpayOrderServiceImpl implements ChannelCurrencyDepositOrderService {

	@Autowired
	Environment environmentContxt;

//	@Autowired
//	private UserAccountFacade userAccountFacade;

	@Autowired
	private UserFacade userFacade;

	@Autowired
	private DepositCurrencyOrderUserMapper depositCurrencyOrderUserMapper;

	@Autowired
	private ChannelOkpayUserRulesMapper okpayUserRulesMapper;

	@Autowired
	private ChannelOkpayGlobalConfigMapper okpayGlobalConfMapper;

	@Autowired
	private DepositCurrencyQueryOrderService depositCurrencyQueryOrderService;
	
	@Override
	public DepositOrderReturnDto depositOrder(Integer channelWithdrawId, String assetCode, Integer uid,
			BigDecimal money, String memo) {

		ChannelOkpayUserRules okpayUserRules = null;
		okpayUserRules = getOkpayUsefulRule(uid);
		if (okpayUserRules == null || okpayUserRules.getStatus().equals(SwitchStatus.OFF)) {
			log.error("okpay规则帐户不存在,或未开启");
			throw new AppException(AccountCodeConst.OKPAY_NOT_OPEN);
		}

		checkMoney(money);
		// account = okpayMapper.selectByPrimaryKey(channelWithdrawId);
		// if (account == null) {
		// log.error("用户={}, okpay帐号={}不存在", uid, channelWithdrawId);
		// throw new AppException(AccountCodeConst.OKPAY_NOT_EXIT,
		// "用户okpay帐户不存在");
		// }
		// if (account.getDelFlag().equals(DelFlag.TRUE)) {
		// log.error("用户={}, , okpay帐号={}已删除", uid, channelWithdrawId);
		// throw new AppException(AccountCodeConst.OKPAY_NOT_EXIT,
		// "用户okpay帐户已删除");
		// }

		UserSimpleInfoDto user = userFacade.getUserInfoByUid(uid);

//		UserAccountDto account = userAccountFacade.queryAccount(uid, assetCode);
//		if (null == account) {
//			log.info("用户资产异常");
//			throw new AppException(UserAssetCodeConst.USER_ACCOUNT_NOT_EXIST);
//		}
//		Integer accountId = account.getAccountId();
		String name = user.getFullName();
		BigDecimal fee = BigDecimal.ZERO;

		String txid = OrderUtil.generateCode(OrderUtil.PAY_SERVICE, OrderUtil.TRANSFER_IN_CURRENCY);

		DepositCurrencyOrderUser order = new DepositCurrencyOrderUser();
		order.setUid(uid);
		order.setBrokerId(user.getBrokerId());
		order.setAccount(user.getUserAccount());
//		order.setAccountId(accountId);
		order.setAssetCode(assetCode);
		order.setBank(OkpayConstants.OKPAY_BANK_NAME);

		order.setFee(fee);
		order.setMoney(money);
		order.setPay(money.subtract(fee));
		order.setMsg(memo);
		order.setStatus(DepositCurrencyOrderStatus.WAIT);
		// OKpay充值不需要事先绑定OKPAY账号，所以生成订单没有账号信息
		order.setAcnumber("");
		order.setName(name);
		// order.setAcnumber(account.getAccountNo());
		// order.setName(account.getAccountName());
		order.setPayMode(DepositCurrencyPayMode.OKPAY);
		order.setUid(uid);
		order.setTxid(txid);
		
		order.setThirdAccount(okpayUserRules.getAccountNo());
		order.setThirdAccountCode(okpayUserRules.getAccountCode());
		order.setThirdAccountName(okpayUserRules.getAccountName());

		order.setCreateDate(new Date());
		order.setUpdateDate(new Date());
		try {
			depositCurrencyOrderUserMapper.insert(order);
		} catch (RuntimeException e) {
			log.info("添加okpay充值订单失败,用户订单信息为:{}", order);
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "添加订单失败");
		}
		DepositOrderReturnDto dto = new DepositOrderReturnDto();
		dto.setName(order.getTxid());
		dto.setAccountNo(order.getThirdAccount());
		dto.setType("service");
		dto.setCurrency(order.getAssetCode());
		dto.setPrice(order.getMoney());
		dto.setTax(BigDecimal.ZERO);
		return dto;
	}

	private void checkMoney(BigDecimal money) {
		ChannelOkpayGlobalConfig okpayGlobalConfig = okpayGlobalConfMapper
				.selectByPrimaryKey(OkpayConstants.OKPAY_GLOBAL_CONFIG_PK);
		if (okpayGlobalConfig == null || okpayGlobalConfig.getStatus().equals(SwitchStatus.OFF)) {
			log.error("okpay全局设置={}不存在,或未开启status={}", OkpayConstants.OKPAY_GLOBAL_CONFIG_PK,
					okpayGlobalConfig.getStatus());
			throw new AppException(AccountCodeConst.OKPAY_NOT_OPEN, "okpay充值未开启");
		}
		if (okpayGlobalConfig.getMinAmount().compareTo(money) == 1) {
			log.error("本次充值金额={},不在设定的okpay全局设置区间内.minAmt={},maxAmt={}", money, okpayGlobalConfig.getMinAmount(),
					okpayGlobalConfig.getMaxAmount());
			throw new AppException(RechargeCodeConst.RECHARGE_MIN_CURRENCY, "提交金额小于最低转入值",
					okpayGlobalConfig.getMinAmount().stripTrailingZeros().toPlainString());
		}
		if (okpayGlobalConfig.getMaxAmount().compareTo(money) == -1) {
			log.error("本次充值金额={},不在设定的okpay全局设置区间内.minAmt={},maxAmt={}", money, okpayGlobalConfig.getMaxAmount(),
					okpayGlobalConfig.getMaxAmount());
			throw new AppException(RechargeCodeConst.RECHARGE_MAX_CURRENCY, "提交金额大于最高转入值",
					okpayGlobalConfig.getMaxAmount().stripTrailingZeros().toPlainString());

		}
	}

	public ChannelOkpayUserRules getOkpayUsefulRule(Integer uid) {

		List<ChannelOkpayUserRules> lst = okpayUserRulesMapper.getRulesListUseful();
		for (ChannelOkpayUserRules rule : lst) {
			if (OkpayRuleUtil.checkIsConformRule(uid, rule.getRules()) && rule.getStatus().equals(SwitchStatus.ON))
				return rule;
		}
		return null;
	}

}
