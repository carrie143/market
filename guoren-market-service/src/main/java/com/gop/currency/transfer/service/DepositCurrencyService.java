package com.gop.currency.transfer.service;

import java.math.BigDecimal;

import com.gop.domain.enums.DepositCurrencyOrderStatus;

public interface DepositCurrencyService {

	public void confirm(int id, int adminId);
	
	public void confirm(String txid, int adminId, BigDecimal amount);
	
	public void changeStatus(String txid,DepositCurrencyOrderStatus exceptStatus,DepositCurrencyOrderStatus changeStatus);
	
	public void closeOrder(int orderKey);

}
