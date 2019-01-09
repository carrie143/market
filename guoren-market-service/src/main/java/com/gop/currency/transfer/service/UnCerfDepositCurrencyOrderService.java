package com.gop.currency.transfer.service;

import java.math.BigDecimal;

public interface UnCerfDepositCurrencyOrderService {
	
	/**
	 * 提交充值并直接审核
	 * 该方法只适用商户充值
	 * @param uid
	 * @param outOrder
	 * @param assetCode
	 * @param amount
	 * @param fee
	 * @param address
	 * @param message
	 */
	public void currencyDepositOrderUnCerf(int uid, String outOrder, String assetCode, BigDecimal amount);


}
