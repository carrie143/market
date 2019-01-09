package com.gop.currency.withdraw.gateway.service.ulpay.common;

/**
 * 系统使用常量
 * @author wujh
 *
 */
public interface Constants {
	
	// 通讯报文字符集
	public static final String transfer_charset = "GBK";
	
	// 加密相关的字符集
	public static final String encrypt_charset = "UTF-8";
	
	/** 数据格式 */
	public static String DATA_TYPE_XML = "0";
	
	/** 处理级别 */
	public static String LEVEL_0 = "0";
	
	/**
	 * 访问成功编码
	 */
	public static final String  RESPONSE_CODE_SUCCESS = "PWM00000";
	
	/**
	 * 访问失败编码
	 */
	public static final String  RESPONSE_CODE_FAIL = "F00001";
	
}
