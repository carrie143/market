package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.UserLockPositionConfig;
import com.gop.domain.UserLockPositionConfigType;

public interface UserLockPositionConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLockPositionConfig record);

    int insertSelective(UserLockPositionConfig record);

    UserLockPositionConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLockPositionConfig record);

    int updateByPrimaryKey(UserLockPositionConfig record);

    UserLockPositionConfig selectConfigByAssetCodeAndKey(@Param("assetCode")String assetCode, @Param("key")UserLockPositionConfigType key);
	
	int createOrUpdateConfig(UserLockPositionConfig record);
	
	List<UserLockPositionConfig> selectConfigListByAssetCode(@Param("assetCode") String assetCode);
}