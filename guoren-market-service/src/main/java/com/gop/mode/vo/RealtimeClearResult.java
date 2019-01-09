package com.gop.mode.vo;

import java.math.BigDecimal;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RealtimeClearResult {

	private Integer userId;

	private String accountNo;

	private String assetCode;

	private Integer brokerId;

	private Integer balanceVersion;

	private Integer checkTime;

	private BigDecimal initBalance;

	private BigDecimal endBalance;

	private BigDecimal balanceMoment;

	private BigDecimal diffAmount;

}
