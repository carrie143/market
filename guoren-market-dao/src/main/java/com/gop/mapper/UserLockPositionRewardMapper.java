package com.gop.mapper;

import com.gop.domain.UserLockPositionReward;
import com.gop.domain.enums.UserLockPositionRewardStatus;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface UserLockPositionRewardMapper {

    int insert(UserLockPositionReward record);

    int insertSelective(UserLockPositionReward record);

    UserLockPositionReward selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLockPositionReward record);

    int updateByPrimaryKey(UserLockPositionReward record);

    UserLockPositionReward selectByUserIdAndTime(UserLockPositionReward userLockPositionReward);

    BigDecimal getTotalRewardByUserId(@Param("uid") Integer userId, @Param("assetCode") String coinType);

    List<UserLockPositionReward> getRewardList(@Param("assetCode") String coinType, @Param("start") Integer start, @Param("pageSize") Integer pageSize, @Param("status") UserLockPositionRewardStatus status);

    Integer countByCoinType(@Param("assetCode") String coinType,@Param("status") UserLockPositionRewardStatus status);

    List<UserLockPositionReward> getRewardByStatus(@Param("status") UserLockPositionRewardStatus status);
    
    List<UserLockPositionReward> getRewardByYearAndMonth(@Param("assetCode") String assetCode ,@Param("rewardYear")Integer rewardYear ,@Param("rewardMonth")Integer rewardMonth);
    
    int updateRewardStatusById(@Param("id")Integer id,@Param("beginStatus") UserLockPositionRewardStatus beginStatus,@Param("endStatus") UserLockPositionRewardStatus endStatus);
}