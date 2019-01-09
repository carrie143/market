package com.gop.coin.transfer.dto.mq;

import java.math.BigDecimal;

import com.gop.domain.enums.DepositCoinAssetStatus;

import lombok.Data;

@Data
public class TransferInDto {
	
	String assetCode;
	
	String orderId;
	
	String toWallet;
	
	String fromWallet;
	
	String sendMessage;
	
	BigDecimal amount;
	
	DepositCoinAssetStatus assetStatus;
	
}
