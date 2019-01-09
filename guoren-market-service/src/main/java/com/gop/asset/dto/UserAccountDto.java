package com.gop.asset.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.mode.vo.BaseDto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserAccountDto extends BaseDto {
	private Integer userId;

	private Integer AccountId;
	
	private String accountNo;

	private String accountKind;

	private String assetCode;

	private BigDecimal amountAvailable;

	private BigDecimal amountLock;

	private BigDecimal amountLoan;

	private Date updateDate;

}
