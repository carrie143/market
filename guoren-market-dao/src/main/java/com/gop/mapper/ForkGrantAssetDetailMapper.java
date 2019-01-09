package com.gop.mapper;

import com.gop.domain.ForkGrantAssetDetail;

public interface ForkGrantAssetDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ForkGrantAssetDetail record);

    int insertSelective(ForkGrantAssetDetail record);

    ForkGrantAssetDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ForkGrantAssetDetail record);

    int updateByPrimaryKey(ForkGrantAssetDetail record);
}