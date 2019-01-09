package com.gop.c2c.service;

import com.gop.domain.C2cAlipayInfo;
/**
 * 
 * @author zhangliwei
 *
 */
public interface C2cAlipayInfoService {

	public C2cAlipayInfo selectById(Integer payChannelId);
	
	public void addC2cUserAlipay(C2cAlipayInfo c2cAlipayInfo , Integer uid);

	public C2cAlipayInfo selectByUid(Integer uid);
	
}
