package com.gop.asset.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gop.domain.ConfigAsset;
import com.gop.domain.enums.AssetStatus;

public interface ConfigAssetService {
	public List<ConfigAsset> getAvailableAssetCode();
	
	public ConfigAsset getAssetConfig(String assetCode);

	public boolean validateAssetConfig(String asset, AssetStatus listed);

	public void updateConfigAsset(ConfigAsset configAsset);

	public void saveConfigAsset(ConfigAsset configAsset);

	public List<ConfigAsset> getAllAssetCode();

	public List<ConfigAsset> getAllAssetCodeByAssetCode(String assetCode);

	PageInfo<ConfigAsset> getAvailableAssetCode(String assetCode, Integer pageNo,Integer pageSize);

}
