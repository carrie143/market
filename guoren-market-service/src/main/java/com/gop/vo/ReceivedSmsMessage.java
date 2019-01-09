package com.gop.vo;

import com.gop.domain.enums.ServiceCode;
import com.gop.domain.enums.SysCode;
import com.gop.vo.enums.SendStrategy;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ReceivedSmsMessage {
	/*
	 * 系统编码
	 */
	private SysCode sysCode;

	/*
	 * 服务编码
	 */
	private ServiceCode serviceCode;

	/*
	 * 账户（手机号／email）
	 */
	private String account;

	/*
	 * 短信内容
	 */
	private String msgContent;

	/*
	 * 短信发送策略
	 */
	private SendStrategy sendStrategy;
}
