package com.gop.currency.transfer.service;

import java.math.BigDecimal;
import java.util.List;

import com.gop.currency.transfer.dto.ChannelConfigDetailDto;
import com.gop.currency.transfer.dto.ChannelIsOpenDto;
import com.gop.currency.transfer.dto.ChannelRulesDetailDto;
import com.gop.domain.enums.SwitchStatus;

public interface ChannelCurrencyDepositManagerService {

	public boolean delete(Integer id);

	public List<ChannelRulesDetailDto> getRulesList();

	public List<ChannelRulesDetailDto> getUsefulRulesList();

	public boolean replace(Integer id, String okpayAccount, String okpayName, String okpayCode, String rules, SwitchStatus status,
			String remark, Integer adminId);

	public boolean update(Integer id, String okpayAccount, String okpayName, String okpayCode, String rules,
			SwitchStatus status, String remark, Integer adminId);

	public ChannelRulesDetailDto getUsefulRule(Integer uid);

	/**
	 * 添加okpay全局设置
	 * 
	 * @param minAmount
	 * @param maxAmount
	 * @param status
	 * @return
	 */
	public boolean addAndUpdateGlobalConfig(Integer id, BigDecimal minAmount, BigDecimal maxAmount, SwitchStatus status,
			Integer adminId);

	/**
	 * 更新okpay全局设置
	 * 
	 * @param id
	 * @param minAmount
	 * @param maxAmount
	 * @param status
	 * @return
	 */
	public boolean updateGlobalConfig(Integer id, BigDecimal minAmount, BigDecimal maxAmount, SwitchStatus status,
			Integer adminId);

	/**
	 * 获取okpay全局设置
	 * 
	 * @param id
	 * @return
	 */
	public ChannelConfigDetailDto getGlobalConfig();

	/**
	 * 支付宝全局设置是否开启
	 * 
	 * @return
	 */
	public ChannelIsOpenDto isGlobalConfigOpen(Integer uid);

}
