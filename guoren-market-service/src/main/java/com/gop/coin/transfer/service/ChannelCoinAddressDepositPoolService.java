package com.gop.coin.transfer.service;

import com.gop.domain.enums.CoinAddressStatus;

public interface ChannelCoinAddressDepositPoolService {
	public Integer getAmountByAssetCodeAndAddressStatus(String assetCode,CoinAddressStatus status);
}
