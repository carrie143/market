package com.gop.api.cloud.response;

import java.math.BigDecimal;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AssetDto {
	private Long uId;

	private String accountNo;

	private String assetCode;

	private BigDecimal amountAvailable;

	private BigDecimal amountLock;
}
