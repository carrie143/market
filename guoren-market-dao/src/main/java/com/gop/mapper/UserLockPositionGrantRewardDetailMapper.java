package com.gop.mapper;

import com.gop.domain.UserLockPositionGrantRewardDetail;

public interface UserLockPositionGrantRewardDetailMapper {

    int insert(UserLockPositionGrantRewardDetail record);

    int insertSelective(UserLockPositionGrantRewardDetail record);

    UserLockPositionGrantRewardDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLockPositionGrantRewardDetail record);

    int updateByPrimaryKey(UserLockPositionGrantRewardDetail record);
    
    int addUserLockPositionGrantRewardDetail(UserLockPositionGrantRewardDetail record);
}