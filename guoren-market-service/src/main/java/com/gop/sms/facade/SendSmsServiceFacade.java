package com.gop.sms.facade;

import com.gop.vo.ReceivedSmsMessage;

public interface SendSmsServiceFacade {
	public void processMessage(ReceivedSmsMessage message);
	

}
