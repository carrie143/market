package com.gop.currency.transfer.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.code.consts.CommonCodeConst;
import com.gop.config.AlipayConstants;
import com.gop.currency.transfer.dto.ChannelConfigDetailDto;
import com.gop.currency.transfer.dto.ChannelIsOpenDto;
import com.gop.currency.transfer.dto.ChannelRulesDetailDto;
import com.gop.currency.transfer.service.ChannelCurrencyDepositManagerService;
import com.gop.currency.transfer.utils.AlipayRuleUtil;
import com.gop.domain.ChannelAlipayGlobalConfig;
import com.gop.domain.ChannelAlipayUserRules;
import com.gop.domain.enums.SwitchStatus;
import com.gop.exception.AppException;
import com.gop.mapper.ChannelAlipayGlobalConfigMapper;
import com.gop.mapper.ChannelAlipayUserRulesMapper;

import lombok.extern.slf4j.Slf4j;

@Service("channelDepositAlipayManagerService")
@Slf4j
public class ChannelDepositAlipayManagerService implements ChannelCurrencyDepositManagerService {

	@Autowired
	private ChannelAlipayGlobalConfigMapper alipayGlobalConfigMapper;

	@Autowired
	private ChannelAlipayUserRulesMapper alipayUserRulesMapper;

	@Override
	public boolean delete(Integer id) {

		return alipayUserRulesMapper.deleteByPrimaryKey(id) > 0 ? true : false;
	}

	@Override
	public List<ChannelRulesDetailDto> getRulesList() {
		return alipayUserRulesMapper.getRulesList().stream().map(rule -> new ChannelRulesDetailDto(rule))
				.collect(Collectors.toList());
	}

	@Override
	public List<ChannelRulesDetailDto> getUsefulRulesList() {
		return alipayUserRulesMapper.getRulesListUseful().stream().map(rule -> new ChannelRulesDetailDto(rule))
				.collect(Collectors.toList());
	}

	@Override
	public boolean replace(Integer id, String alipayAccount, String alipayName, String alipayCode, String rules,
			SwitchStatus status, String remark, Integer adminId) {
		ChannelAlipayUserRules record = new ChannelAlipayUserRules();
		record.setId(id);
		record.setAccountNo(alipayAccount);
		record.setAccountName(alipayName);
		record.setAccountCode(alipayCode);
		record.setRules(rules);
		record.setStatus(status);
		record.setRemark(remark);
		record.setCreateDate(new Date());
		record.setUpdateDate(new Date());
		record.setCreateUser(adminId);
		record.setUpdateAdminId(adminId);
		return alipayUserRulesMapper.replaceSelective(record) > 0 ? true : false;
	}

	@Override
	public boolean update(Integer id, String alipayAccount, String alipayName, String alipayCode, String rules,
			SwitchStatus status, String remark, Integer adminId) {
		ChannelAlipayUserRules record = new ChannelAlipayUserRules();
		record.setId(id);
		record.setAccountNo(alipayAccount);
		record.setAccountName(alipayName);
		record.setAccountCode(alipayCode);
		record.setRules(rules);
		record.setStatus(status);
		record.setRemark(remark);
		record.setCreateDate(new Date());
		record.setUpdateDate(new Date());
		record.setCreateUser(adminId);
		record.setUpdateAdminId(adminId);
		return alipayUserRulesMapper.updateByPrimaryKeySelective(record) > 0 ? true : false;
	}

	@Override
	public ChannelRulesDetailDto getUsefulRule(Integer uid) {
		List<ChannelRulesDetailDto> lst = getUsefulRulesList();
		for (ChannelRulesDetailDto rule : lst) {
			if (AlipayRuleUtil.checkIsConformRule(uid, rule.getRules())
					&& rule.getStatus().equals(SwitchStatus.ON.name()))
				return rule;
		}
		return null;
	}

	@Override
	public boolean addAndUpdateGlobalConfig(Integer id, BigDecimal minAmount, BigDecimal maxAmount, SwitchStatus status,
			Integer adminId) {

		ChannelAlipayGlobalConfig config = new ChannelAlipayGlobalConfig();
		config.setMinAmount(minAmount);
		config.setMaxAmount(maxAmount);
		config.setStatus(status);
		config.setCreateUser(adminId);
		config.setId(AlipayConstants.ALIPAY_GLOBAL_CONFIG_PK);

		return alipayGlobalConfigMapper.updateByPrimaryKeySelective(config) > 0 ? true : false;
	}

	@Override
	public boolean updateGlobalConfig(Integer id, BigDecimal minAmount, BigDecimal maxAmount, SwitchStatus status,
			Integer adminId) {
		ChannelAlipayGlobalConfig config = new ChannelAlipayGlobalConfig();
		config.setMinAmount(minAmount);
		config.setMaxAmount(maxAmount);
		config.setStatus(status);
		config.setUpdateAdminId(adminId);
		config.setId(id);
		return alipayGlobalConfigMapper.updateByPrimaryKeySelective(config) > 0 ? true : false;
	}

	@Override
	public ChannelConfigDetailDto getGlobalConfig() {

		ChannelAlipayGlobalConfig config = alipayGlobalConfigMapper
				.selectByPrimaryKey(AlipayConstants.ALIPAY_GLOBAL_CONFIG_PK);
		if (null == config) {
			log.error("alipay 全局配置未设置或主键不为1");
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
		return new ChannelConfigDetailDto(config);
	}

	@Override
	public ChannelIsOpenDto isGlobalConfigOpen(Integer uid) {
		ChannelRulesDetailDto alipayUserRules = getUsefulRule(uid);
		ChannelConfigDetailDto alipayGlobalConfig = getGlobalConfig();
		ChannelIsOpenDto dto = new ChannelIsOpenDto();
		System.out.println(alipayUserRules);
		System.out.println(alipayGlobalConfig);
		if (null != alipayGlobalConfig && alipayGlobalConfig.getStatus().equals(SwitchStatus.ON.name())
				&& (null != alipayUserRules)) {
			dto.setAccountCode(alipayUserRules.getCode());
			dto.setAccountName(alipayUserRules.getName());
			dto.setAccountNo(alipayUserRules.getAccount());
			dto.setMinAmount(alipayGlobalConfig.getMinAmount());
			dto.setMaxAmount(alipayGlobalConfig.getMaxAmount());
			dto.setStatus(alipayGlobalConfig.getStatus().toString());

		} else {
			dto.setStatus(SwitchStatus.OFF.toString());
		}
		return dto;
	}

	// @Override
	// public boolean isOpen(Integer uid) {
	// ChannelAlipayUserRules alipayUserRules = getUsefulRule(uid);
	// AlipayGlobalConfig alipayGlobalConfig =
	// alipayGlobalConfigService.getAlipayGlobalConfig(1);
	// JSONObject json = new JSONObject();
	// if (null != alipayGlobalConfig &&
	// alipayGlobalConfig.getStatus().equals(SwitchStatus.ON)
	// && (null != alipayUserRules)) {
	//
	// json = JSONUtils.parseObject(alipayUserRules);
	// json.put("minAmount", alipayGlobalConfig.getMinAmount());
	// json.put("maxAmount", alipayGlobalConfig.getMaxAmount());
	// json.put("status", alipayGlobalConfig.getStatus());
	//
	// } else {
	// json.put("status", SwitchStatus.OFF);
	// }
	// }
}
