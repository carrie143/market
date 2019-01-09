package com.gop.coin.transfer.service;

import java.math.BigDecimal;

public interface BrokerAssetOperService {
	
	public void brokerAssetOperDeposit(Integer uid,String assetCode,BigDecimal amount,Integer operUid);
	
	public void brokerAssetOperWithdraw(Integer uid,String assetCode,BigDecimal amount,Integer operUid);

}
