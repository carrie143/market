package com.gop.match.dto;

import java.math.BigDecimal;

import com.gop.domain.enums.TradeCoinFlag;
import com.gop.domain.enums.TradeCoinType;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MatchOrderDto {

	private Integer userId;

	private String outOrderNo;

	private String symbol;
	
	private TradeCoinFlag tradeCoinFlag;
	
	private TradeCoinType tradeCoinType;
	
	private BigDecimal price;
	
	private BigDecimal amount;
	
	private BigDecimal market;
}
