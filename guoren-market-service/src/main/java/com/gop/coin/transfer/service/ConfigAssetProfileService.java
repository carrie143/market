package com.gop.coin.transfer.service;

import java.math.BigDecimal;
import java.util.List;

import com.gop.domain.ConfigAssetProfile;
import com.gop.domain.enums.ConfigAssetType;

public interface ConfigAssetProfileService {

	public BigDecimal getBigDecimalValue(String assetCode, ConfigAssetType key);

	public Integer getIntegerValue(String assetCode, ConfigAssetType key);

	public String getStringValue(String assetCode, ConfigAssetType key);

	public void createOrUpdateConfigAssetProfile(ConfigAssetProfile configAssetProfile);

	public List<ConfigAssetProfile> getConfigAssetProfileList(ConfigAssetType key);
	
	public List<ConfigAssetProfile> selectAll();

	List<ConfigAssetProfile> selectByAssetCodeOrProfileKey(String assetCode, ConfigAssetType key);

	List<ConfigAssetProfile> selectByAssetCode(String assetCode);
}
