package com.gop.sms.config;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper=true)
public class LingkaiSmsConfig extends SmsConfig {
	private String cell;
	private String sendTime;
}
