package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.TokenOrderIntentionState;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TokenOrderIntention {
	private Integer id;

	private Integer uid;

	private String assetName;

	private BigDecimal sellNum;

	private Integer successTradeNum;

	private Date createDate;

	private Date updateDate;

	private String qq;

	private String wechat;

	private String phone;

	private BigDecimal price;

	private TokenOrderIntentionState state;

}