package com.gop.common;

public interface SmsMessageService {

	public void sendPhoneMessage(String phone, String message);

	public void sendEmailMessage(String email, String message);
	
	public void sendEmailMessage(String email, String message, boolean useTemplate,String templateName);
}
