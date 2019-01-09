package com.gop.lock.position.service;

import com.gop.domain.UserLockPositionTotalReward;
import com.gop.mode.vo.PageModel;

public interface IUserLockPositionTotalRewardService {
	int insertSelective(UserLockPositionTotalReward record);

	PageModel<UserLockPositionTotalReward> getTotalRewardList(String assetCode, Integer pageNo, Integer pageSize);
	
	UserLockPositionTotalReward selectTotalRewardByAssetCodeAndYearAndMonth(String assetCode,Integer year,Integer month);
}
