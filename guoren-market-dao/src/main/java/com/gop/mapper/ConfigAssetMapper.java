package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.ConfigAsset;
import com.gop.domain.enums.AssetStatus;

public interface ConfigAssetMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(ConfigAsset record);

	int insertSelective(ConfigAsset record);

	ConfigAsset selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(ConfigAsset record);

	int updateByPrimaryKey(ConfigAsset record);

	ConfigAsset selectByAssetCode(@Param("assetCode") String assetCode);

	List<ConfigAsset> selectAll();
	
	List<ConfigAsset> selectAllByAssetCode(@Param("assetCode") String assetCode);

	List<ConfigAsset> selectByAssetCodeAndStatus(@Param("assetCode") String assetCode);
	
}