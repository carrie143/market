package com.gop.mapper;

import com.gop.domain.ForkLockPositionDetail;

public interface ForkLockPositionDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ForkLockPositionDetail record);

    int insertSelective(ForkLockPositionDetail record);

    ForkLockPositionDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ForkLockPositionDetail record);

    int updateByPrimaryKey(ForkLockPositionDetail record);
}