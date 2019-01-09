package com.gop.lock.position.service;

import com.gop.domain.UserLockPositionReward;
import com.gop.domain.enums.UserLockPositionRewardStatus;

import java.math.BigDecimal;
import java.util.List;

public interface IUserLockPositionRewardService {

    int insertSelective(UserLockPositionReward record);

    int updateByPrimaryKeySelective(UserLockPositionReward record);

    UserLockPositionReward selectByUserIdAndTime(UserLockPositionReward userLockPositionReward);

    BigDecimal getTotalRewardByUserId(Integer userId,String coinType);

    List<UserLockPositionReward> getRewardList(String coinType, Integer start, Integer pageSize,UserLockPositionRewardStatus status);

    Integer countByCoinType(String coinType,UserLockPositionRewardStatus status);

    List<UserLockPositionReward> getRewardByStatus(UserLockPositionRewardStatus status);
    
    List<UserLockPositionReward> getRewardByYearAndMonth(String assetCode,Integer year,Integer month);
    
    int updateRewardStatusById(Integer id,UserLockPositionRewardStatus beginStatus,UserLockPositionRewardStatus endStatus);

}
