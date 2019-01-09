package com.gop.sms.service;

import com.gop.domain.EmailLog;

public interface EmailLogService {
	/**
	 * 添加email日志记录
	 * @param emailLog
	 */
	public void addEmailLog(EmailLog emailLog);

	public void SendEmailLog(EmailLog emailLog);
}
