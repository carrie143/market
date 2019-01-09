package com.gop.sms.config;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper=true)
public class BaiwuSmsConfig extends SmsConfig {
	private String msgId;
	private String ext;
}
