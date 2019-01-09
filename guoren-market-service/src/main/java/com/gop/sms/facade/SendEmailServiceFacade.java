package com.gop.sms.facade;

import com.gop.vo.ReceivedSmsMessage;

public interface SendEmailServiceFacade {
	public void processMessage(ReceivedSmsMessage message);

	public void processMessage(ReceivedSmsMessage message,Boolean useTemplate,String msgContent);
}
