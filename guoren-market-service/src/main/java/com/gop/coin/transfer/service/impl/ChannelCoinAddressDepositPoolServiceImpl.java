package com.gop.coin.transfer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.coin.transfer.service.ChannelCoinAddressDepositPoolService;
import com.gop.domain.enums.CoinAddressStatus;
import com.gop.mapper.ChannelCoinAddressDepositPoolMapper;

@Service("ChannelCoinAddressDepositPoolService")
public class ChannelCoinAddressDepositPoolServiceImpl implements ChannelCoinAddressDepositPoolService {
	@Autowired
	private ChannelCoinAddressDepositPoolMapper channelCoinAddressDepositPoolMapper;
	
	@Override
	public Integer getAmountByAssetCodeAndAddressStatus(String assetCode, CoinAddressStatus status) {
		return channelCoinAddressDepositPoolMapper.selectByAssetCodeAndAddressStatus(assetCode,status);
	}
	
}
