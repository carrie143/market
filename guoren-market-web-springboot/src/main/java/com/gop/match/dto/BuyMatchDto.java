package com.gop.match.dto;

import java.math.BigDecimal;

import com.gop.domain.enums.TradeCoinFlag;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BuyMatchDto {
	

	private BigDecimal buyCoinNum;

	private TradeCoinFlag tradeCoinFlag;

	private BigDecimal buyPrice;

	private String outTradeNo;
	
	private BigDecimal buyInAmount;
}
