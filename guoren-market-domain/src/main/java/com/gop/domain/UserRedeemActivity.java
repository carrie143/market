package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.UserRedeemActivityStatus;

import lombok.Data;

@Data
public class UserRedeemActivity {
	private Integer id;

	private Integer activityId;

	private String redeemCode;

	private String assetCode;

	private BigDecimal amount;

	private UserRedeemActivityStatus status;

	private Integer uid;

	private Date createDate;

	private Date updateDate;

}