package com.gop.domain.enums;

/**
 * 人民币转帐，支付类型
 * 
 * <p>Title:TransferCnyPayMode </p>
 * <p>Description: </p>
 * <p>Company: </p>
 * @author lipeng
 * @date 2016年5月26日上午11:39:21
 * @version V1.1.0
 * @see TransferCnyPayMode
 * @since
 */
public enum WithdrawCurrencyPayMode {
	/*
	 * 人工转帐
	 */
	OFFLINE,
	
	/*
	 * 超级代付
	 */
	SUPERPAY,

	/*
	 * 合众支付
	 */
	ULPAY,
	
	/*
	 * okpay
	 */
	OKPAY;
}
