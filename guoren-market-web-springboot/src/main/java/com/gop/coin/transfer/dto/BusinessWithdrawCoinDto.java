package com.gop.coin.transfer.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.gop.mode.vo.BaseDto;

import lombok.Data;

@Data
public class BusinessWithdrawCoinDto extends BaseDto {

//	@NotBlank
//	String fromAddress;
	
	@NotBlank
	String assetCode;
	
	@NotBlank
	String toAddress;
	
	@NotNull
	BigDecimal amount;
	

	BigDecimal fee;
	

	String message;
	
	@NotBlank
	String orderNo;

}
