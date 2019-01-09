package com.gop.currency.transfer.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.gop.mode.vo.BaseDto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class WithdrawDto extends BaseDto{
	
	@NotBlank
	private String assetCode;
	
	@NotBlank
	private String externalOrderNo;
	
	@NotNull
	private Integer userAccountId;
	
	@NotBlank
	private String channelType;
	
	@NotBlank
	private String memo;
	
	@NotNull
	private BigDecimal amount;
	
}
