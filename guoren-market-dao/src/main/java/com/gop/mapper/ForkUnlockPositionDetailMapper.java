package com.gop.mapper;

import com.gop.domain.ForkUnlockPositionDetail;

public interface ForkUnlockPositionDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ForkUnlockPositionDetail record);

    int insertSelective(ForkUnlockPositionDetail record);

    ForkUnlockPositionDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ForkUnlockPositionDetail record);

    int updateByPrimaryKey(ForkUnlockPositionDetail record);
}