package com.gop.domain;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TotalTradeInfo {
	private Integer id;

	private Integer uid;

	private BigDecimal amount;

	private Integer symbol;

	private Integer rankId;

}