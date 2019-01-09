package com.gop.match.dto;

import com.gop.domain.enums.ConfigAssetType;

import lombok.Data;

@Data
public class WithdrawCoinDailyLimitConfigDto {
	private Integer id;
	private String profileValue;
	private String assetCode;
	private ConfigAssetType profileKey;
}
