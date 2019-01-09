package com.gop.match.dto;

import com.gop.domain.enums.ConfigAssetType;

import lombok.Data;

@Data
public class WithdrawCoinFeeConfigDto {
	private Integer id;
	private String assetCode;
	private ConfigAssetType profileKey;
	private String profileValue;
}
