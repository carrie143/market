package com.gop.sms.strategy;

import com.gop.sms.config.SmsConfig;

public interface ServiceStrategy {
	/**
	 * 发送短信策略接口
	 * @param smsConfig
	 * @param phone
	 * @param message
	 * @return
	 */
	public String sendSms(SmsConfig smsConfig, String phone, String message);
}
