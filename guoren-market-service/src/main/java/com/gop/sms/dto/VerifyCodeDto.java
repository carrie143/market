package com.gop.sms.dto;

import lombok.Data;

@Data
public class VerifyCodeDto {

	private String emailText;
	
	private String templateName;

}
