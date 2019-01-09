package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class TradeMatchResult {
	private Long id;

	private String symbol;

	private String buyInnerOrderNo;

	private String sellInnerOrderNo;

	private Integer buyBrokerId;

	private Integer sellBrokerId;

	private String buyRequestNo;

	private String sellRequestNo;

	private BigDecimal number;

	private BigDecimal price;

	private Integer buyUid;

	private Integer sellUid;

	private BigDecimal buyFee;

	private BigDecimal sellFee;

	private Date createTime;

}