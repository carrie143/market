package com.gop.sms.config;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper=true)
public class WeiwangSmsConfig extends SmsConfig {
	private String corpId;
	private String prdId;
}
