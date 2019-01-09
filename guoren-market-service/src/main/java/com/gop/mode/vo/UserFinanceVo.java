package com.gop.mode.vo;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class UserFinanceVo {
	private BigDecimal currencyLock;

	private BigDecimal currencyBalance;

	private BigDecimal coinBalance;

	private BigDecimal coinLock;

}
