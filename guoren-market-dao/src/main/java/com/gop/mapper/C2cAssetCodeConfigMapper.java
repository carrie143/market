package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.C2cAssetCodeConfig;
import com.gop.domain.enums.C2cTransType;

public interface C2cAssetCodeConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(C2cAssetCodeConfig record);

    int insertSelective(C2cAssetCodeConfig record);

    C2cAssetCodeConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(C2cAssetCodeConfig record);

    int updateByPrimaryKey(C2cAssetCodeConfig record);
    
    List<C2cAssetCodeConfig> getStatusListedC2cAssetCodeConfigByType(@Param("type")C2cTransType type);
   
    C2cAssetCodeConfig getStatusListedC2cAssetCodeConfigByAssetCodeAndType(@Param("assetCode")String assetCode,@Param("type")C2cTransType type);
    
}