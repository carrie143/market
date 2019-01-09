package com.gop.mode.vo;

import java.math.BigDecimal;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TradeRecordVo {
	private String buyOrderNo;
	private String sellOrderNo;
	private BigDecimal price;
	private BigDecimal num;
	private String buyTradeTransferInOrderNo;
	private String sellTradeTransferInOrderNo;
//	private UserType buyUserType;
//	private UserType sellUserType;
	private int buyId;
	private int sellId;
}
