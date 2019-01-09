package com.gop.match.dto;

import java.math.BigDecimal;

import com.gop.domain.enums.TradeCoinFlag;

public class SellMatchDto {
	
	BigDecimal sellCoinNum;
	TradeCoinFlag tradeCoinFlag;
	BigDecimal sellPrice;
	String outTradeNo;
}
