package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.C2cCountryConfig;
import com.gop.domain.C2cCurrencyConfig;
import com.gop.domain.enums.C2cTransType;

public interface C2cCurrencyConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(C2cCurrencyConfig record);

    int insertSelective(C2cCurrencyConfig record);

    C2cCurrencyConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(C2cCurrencyConfig record);

    int updateByPrimaryKey(C2cCurrencyConfig record);
    
    List<C2cCurrencyConfig> getStatusListedC2cCurrencyConfigByType(@Param("type")C2cTransType type);

    C2cCurrencyConfig getStatusListedC2cCurrencyConfigByAssetCodeAndType(@Param("assetCode")String assetCode,@Param("type")C2cTransType type);
    
}