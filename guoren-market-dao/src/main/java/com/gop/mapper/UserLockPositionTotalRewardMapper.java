package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.UserLockPositionTotalReward;

public interface UserLockPositionTotalRewardMapper {

    int insert(UserLockPositionTotalReward record);

    int insertSelective(UserLockPositionTotalReward record);

    UserLockPositionTotalReward selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLockPositionTotalReward record);

    int updateByPrimaryKey(UserLockPositionTotalReward record);

	List<UserLockPositionTotalReward> selectByAssetCode(@Param("assetCode")String assetCode);
	
	UserLockPositionTotalReward selectTotalRewardByAssetCodeAndYearAndMonth(@Param("assetCode")String assetCode,
			@Param("rewardYear")Integer rewardYear,@Param("rewardMonth")Integer rewardMonth);
}