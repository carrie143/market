package com.gop.currency.transfer.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.code.consts.CommonCodeConst;
import com.gop.config.OkpayConstants;
import com.gop.currency.transfer.dto.ChannelConfigDetailDto;
import com.gop.currency.transfer.dto.ChannelIsOpenDto;
import com.gop.currency.transfer.dto.ChannelRulesDetailDto;
import com.gop.currency.transfer.service.ChannelCurrencyDepositManagerService;
import com.gop.currency.transfer.utils.AlipayRuleUtil;
import com.gop.domain.ChannelOkpayGlobalConfig;
import com.gop.domain.ChannelOkpayUserRules;
import com.gop.domain.enums.SwitchStatus;
import com.gop.exception.AppException;
import com.gop.mapper.ChannelOkpayGlobalConfigMapper;
import com.gop.mapper.ChannelOkpayUserRulesMapper;

import lombok.extern.slf4j.Slf4j;

@Service("channelDepositOkpayManagerService")
@Slf4j
public class ChannelDepositOkpayManagerService implements ChannelCurrencyDepositManagerService {

	@Autowired
	private ChannelOkpayGlobalConfigMapper okpayGlobalConfigMapper;

	@Autowired
	private ChannelOkpayUserRulesMapper okpayUserRulesMapper;

	@Override
	public boolean delete(Integer id) {
		return okpayUserRulesMapper.deleteByPrimaryKey(id) > 0 ? true : false;
	}

	@Override
	public List<ChannelRulesDetailDto> getRulesList() {
		return okpayUserRulesMapper.getRulesList().stream().map(rule -> new ChannelRulesDetailDto(rule))
				.collect(Collectors.toList());
	}

	@Override
	public List<ChannelRulesDetailDto> getUsefulRulesList() {
		return okpayUserRulesMapper.getRulesListUseful().stream().map(rule -> new ChannelRulesDetailDto(rule))
				.collect(Collectors.toList());
	}

	@Override
	public boolean replace(Integer id, String okpayAccount, String okpayName, String okpayCode, String rules, SwitchStatus status,
			String remark, Integer adminId) {
		ChannelOkpayUserRules record = new ChannelOkpayUserRules();
		record.setId(id);
		record.setAccountNo(okpayAccount);
		record.setAccountName(okpayName);
		record.setAccountCode(okpayCode);
		record.setRules(rules);
		record.setStatus(status);
		record.setRemark(remark);
		record.setCreateDate(new Date());
		record.setUpdateDate(new Date());
		record.setCreateUser(adminId);
		record.setUpdateUser(adminId);
		return okpayUserRulesMapper.replaceSelective(record) > 0 ? true : false;

	}

	@Override
	public boolean update(Integer id, String okpayAccount, String okpayName, String okpayCode, String rules,
			SwitchStatus status, String remark, Integer adminId) {
		ChannelOkpayUserRules record = new ChannelOkpayUserRules();
		record.setAccountNo(okpayAccount);
		record.setAccountName(okpayName);
		record.setAccountCode(okpayCode);
		record.setRules(rules);
		record.setStatus(status);
		record.setRemark(remark);
		record.setCreateDate(new Date());
		record.setUpdateDate(new Date());
		record.setCreateUser(adminId);
		record.setUpdateUser(adminId);
		return okpayUserRulesMapper.updateByPrimaryKeySelective(record) > 0 ? true : false;

	}

	@Override
	public ChannelRulesDetailDto getUsefulRule(Integer uid) {
		List<ChannelRulesDetailDto> lst = getUsefulRulesList();
		for (ChannelRulesDetailDto rule : lst) {
			if (AlipayRuleUtil.checkIsConformRule(uid, rule.getRules()) && rule.getStatus().equals(SwitchStatus.ON.name()))
				return rule;
		}
		return null;
	}

	@Override
	public boolean addAndUpdateGlobalConfig(Integer id,BigDecimal minAmount, BigDecimal maxAmount, SwitchStatus status,
			Integer adminId) {
		ChannelOkpayGlobalConfig config = new ChannelOkpayGlobalConfig();
		config.setId(id);
		config.setMinAmount(minAmount);
		config.setMaxAmount(maxAmount);
		config.setStatus(status);
		config.setCreateUser(adminId);
		config.setId(OkpayConstants.OKPAY_GLOBAL_CONFIG_PK);

		return okpayGlobalConfigMapper.replaceSelective(config) > 0 ? true : false;

	}

	@Override
	public boolean updateGlobalConfig(Integer id, BigDecimal minAmount, BigDecimal maxAmount, SwitchStatus status,
			Integer adminId) {
		ChannelOkpayGlobalConfig config = new ChannelOkpayGlobalConfig();
		config.setMinAmount(minAmount);
		config.setMaxAmount(maxAmount);
		config.setStatus(status);
		config.setUpdateUser(adminId);
		config.setId(id);
		return okpayGlobalConfigMapper.updateByPrimaryKeySelective(config) > 0 ? true : false;
	}

	@Override
	public ChannelConfigDetailDto getGlobalConfig() {
		ChannelOkpayGlobalConfig config = okpayGlobalConfigMapper.selectByPrimaryKey(OkpayConstants.OKPAY_GLOBAL_CONFIG_PK);
		if(null == config){
			log.error("okpay 全局配置未设置或主键不为1");
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
		return new ChannelConfigDetailDto(config
				);
	}

	@Override
	public ChannelIsOpenDto isGlobalConfigOpen(Integer uid) {
		ChannelRulesDetailDto okpayUserRules = getUsefulRule(uid);
		ChannelConfigDetailDto okpayGlobalConfig = getGlobalConfig();
		ChannelIsOpenDto dto = new ChannelIsOpenDto();
		if (null != okpayGlobalConfig && okpayGlobalConfig.getStatus().equals(SwitchStatus.ON.name())
				&& (null != okpayUserRules)) {
			dto.setAccountCode(okpayUserRules.getCode());
			dto.setAccountName(okpayUserRules.getName());
			dto.setAccountNo(okpayUserRules.getAccount());
			dto.setMinAmount(okpayGlobalConfig.getMinAmount());
			dto.setMaxAmount(okpayGlobalConfig.getMaxAmount());
			dto.setStatus(okpayGlobalConfig.getStatus().toString());

		} else {
			dto.setStatus(SwitchStatus.OFF.toString());
		}
		return dto;
	}

}
