package com.gop.coin.transfer.service;

import com.gop.api.cloud.request.DepositCallbackDto;
public interface DepositCoinService {
	/**
	 * 云平台充值回调
	 */
	void depositConfirmCallback(DepositCallbackDto t);
}
