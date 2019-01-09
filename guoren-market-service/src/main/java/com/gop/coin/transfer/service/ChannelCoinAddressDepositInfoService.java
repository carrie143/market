package com.gop.coin.transfer.service;

import com.gop.domain.ChannelCoinAddressDepositInfo;

public interface ChannelCoinAddressDepositInfoService {

	public ChannelCoinAddressDepositInfo getChannelCoinAddressDepositInfoByTargetAssetCode(String targetAssetCode);
}
