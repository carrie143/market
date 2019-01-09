package com.gop.currency.transfer.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.code.consts.AccountCodeConst;
import com.gop.code.consts.RechargeCodeConst;
import com.gop.common.Environment;
import com.gop.config.AlipayConstants;
import com.gop.currency.transfer.dto.DepositOrderReturnDto;
import com.gop.currency.transfer.service.ChannelCurrencyDepositOrderService;
import com.gop.currency.transfer.service.DepositCurrencyQueryOrderService;
import com.gop.currency.transfer.utils.AlipayRuleUtil;
import com.gop.domain.ChannelAlipayGlobalConfig;
import com.gop.domain.ChannelAlipayUserAccount;
import com.gop.domain.ChannelAlipayUserRules;
import com.gop.domain.DepositCurrencyOrderUser;
import com.gop.domain.enums.DelFlag;
import com.gop.domain.enums.DepositCurrencyOrderStatus;
import com.gop.domain.enums.DepositCurrencyPayMode;
import com.gop.domain.enums.SwitchStatus;
import com.gop.exception.AppException;
import com.gop.mapper.ChannelAlipayGlobalConfigMapper;
import com.gop.mapper.ChannelAlipayUserAccountMapper;
import com.gop.mapper.ChannelAlipayUserRulesMapper;
import com.gop.mapper.DepositCurrencyOrderUserMapper;
import com.gop.user.dto.UserSimpleInfoDto;
import com.gop.user.facade.UserFacade;
import com.gop.util.OrderUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DepositAlipayOrderServiceImpl implements ChannelCurrencyDepositOrderService {

	@Autowired
	Environment environmentContxt;

	@Autowired
	private UserFacade userFacade;

	@Autowired
	private DepositCurrencyOrderUserMapper depositCurrencyOrderUserMapper;

	@Autowired
	private ChannelAlipayUserAccountMapper alipayMapper;

	@Autowired
	private ChannelAlipayUserRulesMapper alipayUserRulesMapper;

	@Autowired
	private ChannelAlipayGlobalConfigMapper alipayGlobalConfMapper;
	
	@Autowired
	private DepositCurrencyQueryOrderService depositCurrencyQueryOrderService;
	
	@Override
	public DepositOrderReturnDto depositOrder(Integer channelWithdrawId, String assetCode, Integer uid, BigDecimal money, String memo) {
		
		
		ChannelAlipayUserAccount account = null;
		ChannelAlipayUserRules alipayUserRules = null;
		alipayUserRules = getUsefulRule(uid);
		if (alipayUserRules == null || alipayUserRules.getStatus().equals(SwitchStatus.OFF)) {
			log.error("支付宝规则帐户不存在,或未开启");
			throw new AppException(AccountCodeConst.ALIPAY_NOT_OPEN, "支付宝充值未开启");
		}
		
		checkMoney(money);

		account = alipayMapper.selectByPrimaryKey(channelWithdrawId);
		if (account == null) {
			log.error("用户={}, 支付宝帐号={}不存在", uid, channelWithdrawId);
			throw new AppException(AccountCodeConst.ALIPAY_NOT_EXIT, "用户支付宝帐户不存在");
		}
		if (account.getDelFlag().equals(DelFlag.TRUE)) {
			log.error("用户={}, , 支付宝帐号={}已删除", uid, channelWithdrawId);
			throw new AppException(AccountCodeConst.ALIPAY_NOT_EXIT, "用户支付宝帐户已删除");
		}

		
		UserSimpleInfoDto user = userFacade.getUserInfoByUid(uid);
		BigDecimal fee = BigDecimal.ZERO;

		String txid = OrderUtil.generateCode(OrderUtil.PAY_SERVICE, OrderUtil.TRANSFER_IN_CURRENCY);

		DepositCurrencyOrderUser order = new DepositCurrencyOrderUser();
		order.setUid(uid);
		order.setBrokerId(user.getBrokerId());
		order.setAccount(user.getUserAccount());
		order.setAcnumber(account.getAccountNo());
		order.setAssetCode(assetCode);
		order.setBank(AlipayConstants.ALIPAY_BANK_NAME);
		order.setStatus(DepositCurrencyOrderStatus.WAIT);
		order.setFee(fee);
		order.setMoney(money);
		order.setPay(money.subtract(fee));
		order.setMsg(memo);
		order.setName(account.getAccountName());
		order.setPayMode(DepositCurrencyPayMode.ALIPAY);
		order.setUid(uid);
		order.setTxid(txid);
		
		order.setThirdAccount(alipayUserRules.getAccountNo());
		order.setThirdAccountCode(alipayUserRules.getAccountCode());
		order.setThirdAccountName(alipayUserRules.getAccountName());

		order.setCreateDate(new Date());
		order.setUpdateDate(new Date());
		try {
			depositCurrencyOrderUserMapper.insert(order);
		} catch (RuntimeException e) {
			log.info("添加支付宝充值订单失败,用户订单信息为:{}", order);
		}
		//银行充值单不需要返回信息
		return new DepositOrderReturnDto();

	}

	private void checkMoney(BigDecimal money) {
		ChannelAlipayGlobalConfig alipayGlobalConfig = alipayGlobalConfMapper
				.selectByPrimaryKey(AlipayConstants.ALIPAY_GLOBAL_CONFIG_PK);
		if (alipayGlobalConfig == null || alipayGlobalConfig.getStatus().equals(SwitchStatus.OFF)) {
			log.error("支付宝全局设置={}不存在,或未开启status={}", AlipayConstants.ALIPAY_GLOBAL_CONFIG_PK,
					alipayGlobalConfig.getStatus());
			throw new AppException(AccountCodeConst.ALIPAY_NOT_OPEN, "支付宝充值未开启");
		}
		if (alipayGlobalConfig.getMinAmount().compareTo(money) == 1) {
			log.error("本次充值金额={},不在设定的支付宝全局设置区间内.minAmt={},maxAmt={}", money, alipayGlobalConfig.getMinAmount(),
					alipayGlobalConfig.getMaxAmount());
			throw new AppException(RechargeCodeConst.RECHARGE_MIN_CURRENCY, "提交金额小于最低充值金额",
					alipayGlobalConfig.getMinAmount().stripTrailingZeros().toPlainString());
		}
		if (alipayGlobalConfig.getMaxAmount().compareTo(money) == -1) {
			log.error("本次充值金额={},不在设定的支付宝全局设置区间内.minAmt={},maxAmt={}", money, alipayGlobalConfig.getMaxAmount(),
					alipayGlobalConfig.getMaxAmount());
			throw new AppException(RechargeCodeConst.RECHARGE_MAX_CURRENCY, "提交金额大于最高充值金额",
					alipayGlobalConfig.getMaxAmount().stripTrailingZeros().toPlainString());

		}
	}

	public ChannelAlipayUserRules getUsefulRule(Integer uid) {

		List<ChannelAlipayUserRules> lst = alipayUserRulesMapper.getRulesListUseful();
		for (ChannelAlipayUserRules rule : lst) {
			if (AlipayRuleUtil.checkIsConformRule(uid, rule.getRules()) && rule.getStatus().equals(SwitchStatus.ON))
				return rule;
		}
		return null;
	}

}
