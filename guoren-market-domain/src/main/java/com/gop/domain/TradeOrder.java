package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.SendStatus;
import com.gop.domain.enums.TradeCoinFlag;
import com.gop.domain.enums.TradeCoinStatus;
import com.gop.domain.enums.TradeCoinType;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TradeOrder {
	private Integer id;

	private Integer uid;

	private Integer brokerId;

	private Integer accountId;

	private String symbol;

	private String outerOrderNo;

	private String innerOrderNo;

	private String requestNo;

	private BigDecimal number;

	private BigDecimal price;

	private BigDecimal money;

	private BigDecimal numberOver;

	private BigDecimal moneyOver;

	private TradeCoinType orderType;

	private TradeCoinFlag tradeFlag;

	private BigDecimal tradedNumber;

	private BigDecimal tradedMoney;

	private TradeCoinStatus status;

	private SendStatus sendStatus;

	private Date createDate;

	private Date updateDate;

	private String failMessageCode;

	private String failMessageDes;
	
	private BigDecimal fee;

}