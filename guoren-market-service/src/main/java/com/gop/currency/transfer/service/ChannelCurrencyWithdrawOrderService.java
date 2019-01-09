package com.gop.currency.transfer.service;

import java.math.BigDecimal;

public interface ChannelCurrencyWithdrawOrderService {

	public void withdrawOrder(Integer channelWithdrawId, String externalOrderNo, String assetCode, Integer uid, BigDecimal money, String memo);

	public void withdrawOrder(int uid, String assetCode, String externalOrderNo, String accountName,
			BigDecimal money, BigDecimal fee,String bankName, String bankNo);
	
	
	public BigDecimal getWithdrawFee(String assetCode, BigDecimal money) ;

}
