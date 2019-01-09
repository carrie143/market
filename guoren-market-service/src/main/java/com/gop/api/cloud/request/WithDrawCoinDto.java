package com.gop.api.cloud.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class WithDrawCoinDto {

	private BigDecimal amount;
	
	private BigDecimal fee;

	private String outOrder;

	private String address;

	private String assetCode;

	private String message;

}
