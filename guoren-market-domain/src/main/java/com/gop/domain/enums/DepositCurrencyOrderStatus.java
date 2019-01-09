package com.gop.domain.enums;

public enum DepositCurrencyOrderStatus {
	/*
	 * 转帐申请：已提交
	 */
	WAIT, 
	
	/*
	 * 转帐申请：处理中
	 */
	PROCESSING,
	
	/*
	 * 转帐申请：已成功
	 */
	SUCCESS, 
	
	/*
	 * 转帐申请：已失败
	 * 此处包括两种情况：
	 * 1， 风控不通过，拒绝
	 * 2，支付失败，拒绝
	 */
	FAILURE,
	
	/*
	 * 转帐申请：支付失败
	 */
	UNKNOWN;
}
