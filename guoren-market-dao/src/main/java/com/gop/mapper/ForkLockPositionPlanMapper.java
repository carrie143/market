package com.gop.mapper;

import com.gop.domain.ForkLockPositionPlan;

public interface ForkLockPositionPlanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ForkLockPositionPlan record);

    int insertSelective(ForkLockPositionPlan record);

    ForkLockPositionPlan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ForkLockPositionPlan record);

    int updateByPrimaryKey(ForkLockPositionPlan record);
}