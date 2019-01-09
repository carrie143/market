package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.C2cAssetCodeConfig;
import com.gop.domain.C2cCountryConfig;
import com.gop.domain.enums.C2cTransType;

public interface C2cCountryConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(C2cCountryConfig record);

    int insertSelective(C2cCountryConfig record);

    C2cCountryConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(C2cCountryConfig record);

    int updateByPrimaryKey(C2cCountryConfig record);
    
    List<C2cCountryConfig> getStatusListedC2cCountryConfigByType(@Param("type")C2cTransType type);

    C2cCountryConfig getStatusListedC2cCountryConfigByAssetCodeAndType(@Param("country")String country,@Param("type")C2cTransType type);
    
}