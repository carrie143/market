package com.gop.financecheck.enums;

/*
 * 借贷方向
 */

public enum AccountDirection {
	/**
	 * 
	 * 贷方 - 资产减少，费用减少，负债增加，权益增加，收入增加
	 */
	CREDIT,
	
	/**
	 * 借方 - 资产增加，费用增加，负债减少，权益减少，收入减少
	 */
	DEBIT;
}
