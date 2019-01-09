package com.gop.currency.withdraw.gateway.service.cibpay.service;

import com.gop.domain.ChannelCibBank;

public interface CibBankService {
	/**
	 * 根据银行名称找到银行信息
	 * @param bankName
	 * @return
	 */
	public ChannelCibBank getCibBankByName(String bankName);
	
	/**
	 * 根据银行短名称找到银行信息
	 * @param shortName
	 * @return
	 */
	public ChannelCibBank getCibBankByShortName(String shortName);
	
	public void savaCibBankByString(String str);

	public void clearCibBank();
	
	public String getBankNoByName(String bankName);
	
	public String getBankNoByShortName(String shortName);
}
