package com.gop.sms.service;

import com.gop.domain.SmsLog;
import com.gop.vo.ReceivedSmsMessage;

public interface SmsLogService {
	/**
	 * 添加sms日志记录
	 * @param smsLog
	 */
	public void addSmsLog(SmsLog smsLog);
	
	public void SendSmsLog(SmsLog smsLog);
}
