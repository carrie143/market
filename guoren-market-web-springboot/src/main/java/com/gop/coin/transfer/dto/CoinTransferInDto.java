package com.gop.coin.transfer.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CoinTransferInDto {
	
	String assetCode;
	
	String orderId;
	
	String toWallet;
	
	String fromWallet;
	
	String sendMessage;
	
	BigDecimal amount;
	
	String assetStatus;
	// enum('CONFIRM','SUCCESS','FAILURE') COMMENT '资产状体：已经入帐，待确认，已经确认'
}
