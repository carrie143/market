package com.gop.coin.transfer.dto.mq;

import java.math.BigDecimal;

import com.gop.domain.enums.DepositCoinAssetStatus;

import lombok.Data;

@Data
public class TransferOutCallbackDto {

	String assetCode;

	String orderId;

	String txId;
	
	String toWallet;

	String fromWallet;

	String sendMessage;

	BigDecimal amount;

	DepositCoinAssetStatus assetStatus;

}
