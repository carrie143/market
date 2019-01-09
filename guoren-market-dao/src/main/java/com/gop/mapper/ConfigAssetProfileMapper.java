package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.ConfigAssetProfile;
import com.gop.domain.enums.ConfigAssetType;

public interface ConfigAssetProfileMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(ConfigAssetProfile record);

	int insertSelective(ConfigAssetProfile record);

	ConfigAssetProfile selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(ConfigAssetProfile record);

	int updateByPrimaryKey(ConfigAssetProfile record);

	ConfigAssetProfile selectParamByAssetCodeAndProfileKey(@Param("assetCode") String assetCode,
			@Param("key") ConfigAssetType key);

	int createOrUpdateConfigAssetProfile(ConfigAssetProfile configAssetProfile);

	List<ConfigAssetProfile> selectByProfileKey(@Param("profileKey") ConfigAssetType configAssetType);

	List<ConfigAssetProfile> selectAll();

	List<ConfigAssetProfile> selectByAssetCodeOrProfileKey(@Param("assetCode") String assetCode,
			@Param("profileKey") ConfigAssetType key);

  List<ConfigAssetProfile> selectByAssetCode(@Param("assetCode") String assetCode);
}