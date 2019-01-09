package com.gop.c2c.service;

import com.gop.domain.C2cBankInfo;
/**
 * 
 * @author zhangliwei
 *
 */
public interface C2cBankInfoService {

	public C2cBankInfo selectById(Integer payChannelId);
	
	public void addC2cUserBank(C2cBankInfo c2cBankInfo , Integer uid);
	
	public C2cBankInfo selectByUid(Integer uid);
	
}
