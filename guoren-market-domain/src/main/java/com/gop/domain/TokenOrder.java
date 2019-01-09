package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.TokenOrderState;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TokenOrder {

	private String tradeAsset;

	private Integer id;

	private Integer tokenIntentionId;

	private String orderNo;

	private BigDecimal price;

	private String assetName;

	private BigDecimal num;

	private Integer buyUid;

	private Integer sellUid;

	private TokenOrderState state;

	private String password;

	private String buyOrderRequestNo;

	private BigDecimal money;

	private Date createDate;

	private Date updateDate;

	private BigDecimal sellFee;

	private String sellOrderRequestNo;

	private Date bundTime;

	private BigDecimal pay;

	private String fromAddress;
	private String toAddress;

	public Date getServerTime() {
		return new Date();
	}

}