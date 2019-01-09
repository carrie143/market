package com.gop.currency.transfer.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class ApiTransferCurrencyDto {

	@NotBlank
	String assetCode;
	
	@NotBlank
	String externalOrderNo;
	
	@NotBlank
	String accountName;
	
	@NotNull
	BigDecimal payAmount;
	
	@NotBlank
	String bankName;
	
	@NotBlank
	String bankNo;

}
