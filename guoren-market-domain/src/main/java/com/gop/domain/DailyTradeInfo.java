package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class DailyTradeInfo {
	private Integer id;

	private Integer uid;

	private Date fromDate;

	private BigDecimal tradeNumber;

	private Integer lastTradeId;

	private Integer rankId;

}