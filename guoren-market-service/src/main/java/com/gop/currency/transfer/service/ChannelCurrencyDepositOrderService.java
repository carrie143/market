package com.gop.currency.transfer.service;

import java.math.BigDecimal;

import com.gop.currency.transfer.dto.DepositOrderReturnDto;

//
public interface ChannelCurrencyDepositOrderService {
	public DepositOrderReturnDto depositOrder(Integer channelWithdrawId,  String assetCode,
			Integer uid, BigDecimal money, String memo);
	
	
	
}
