package com.gop.asset.dto;

import java.math.BigDecimal;

import com.gop.domain.Finance;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class AssetDto {
	private Integer userId;

	private String assetCode;

	private BigDecimal amountAvailable;
	
	private BigDecimal amountLock;
}
