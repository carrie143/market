package com.gop.sms.config;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SmsConfig {
	private String postUrl;
	private String accountId;
	private String accountPassword;
}
