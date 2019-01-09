package com.gop.coin.transfer.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BusinessTransferOutDto {
	
	@NotNull
	String orderNo;
	
}
