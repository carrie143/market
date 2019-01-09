package com.gop.domain.enums;

public enum ServiceCode {
	/**
	 * (验证码，订单，提醒，密码重置，注册等）----单个号码下发
	 */
	VERIFY_CODE,
	
	/**
	 * （客户通知等，生日祝福）-----可群发，可单号码提交
	 */
	NOTICE,
	
	/**
	 * （营销类，商家促销，相关产品活动等）：-----可群发，可单条提交
	 */
	MARKET,

	/**
	 * 财务日报
	 */
	REPORT_DAILY
	
}
