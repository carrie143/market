package com.gop.coin.transfer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.coin.transfer.service.ChannelCoinAddressDepositInfoService;
import com.gop.coin.transfer.service.ChannelCoinAddressDepositPoolService;
import com.gop.domain.ChannelCoinAddressDepositInfo;
import com.gop.domain.enums.CoinAddressStatus;
import com.gop.mapper.ChannelCoinAddressDepositInfoMapper;
import com.gop.mapper.ChannelCoinAddressDepositPoolMapper;

@Service
public class ChannelCoinAddressDepositInfoServiceImpl implements ChannelCoinAddressDepositInfoService {
	@Autowired
	private ChannelCoinAddressDepositInfoMapper channelCoinAddressDepositInfoMapper;

	@Override
	public ChannelCoinAddressDepositInfo getChannelCoinAddressDepositInfoByTargetAssetCode(String targetAssetCode) {
		
		return channelCoinAddressDepositInfoMapper.getChannelCoinAddressDepositInfoByTargetAssetCode(targetAssetCode);
	}
	
	
}
