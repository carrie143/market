package com.gop.coin.transfer.dto;

import com.gop.domain.ConfigAssetProfile;
import com.gop.domain.enums.ConfigAssetType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WithdrawCoinFeeQueryDto {
	private String assetCode;
	private ConfigAssetType profileKey;
	private String profileValue;

	public WithdrawCoinFeeQueryDto(ConfigAssetProfile configAssetProfile) {
		this.assetCode = configAssetProfile.getAssetCode();
		this.profileKey = configAssetProfile.getProfileKey();
		this.profileValue = configAssetProfile.getProfileValue();
	}
}
