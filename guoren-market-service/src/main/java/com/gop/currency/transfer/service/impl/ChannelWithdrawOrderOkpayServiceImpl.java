package com.gop.currency.transfer.service.impl;

import java.math.BigDecimal;
import com.gop.currency.transfer.service.ChannelCurrencyWithdrawOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("withdrawOrderOkpayServiceImpl")
@Slf4j
public class ChannelWithdrawOrderOkpayServiceImpl implements ChannelCurrencyWithdrawOrderService {@Override
	public void withdrawOrder(Integer channelWithdrawId, String externalOrderNo, String assetCode, Integer uid,
			BigDecimal money, String memo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void withdrawOrder(int uid, String assetCode, String externalOrderNo, String accountName, BigDecimal money,
			BigDecimal fee, String bankName, String bankNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BigDecimal getWithdrawFee(String assetCode, BigDecimal money) {
		// TODO Auto-generated method stub
		return null;
	}

}
