package com.gop.coin.transfer.dto;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class BusinessTransferInDto {
	
	@NotBlank
	String fromDate;
	
	@NotBlank
	String toDate;
	
}
