package com.gop.lock.position.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.domain.UserLockPositionGrantRewardDetail;
import com.gop.lock.position.service.IUserLockPositionGrantRewardDetailService;
import com.gop.mapper.UserLockPositionGrantRewardDetailMapper;

@Service
public class UserLockPositionGrantRewardDetailServiceImpl implements IUserLockPositionGrantRewardDetailService{

	@Autowired
	private UserLockPositionGrantRewardDetailMapper userLockPositionGrantRewardDetailMapper;
	
	@Override
	public void addUserLockPositionGrantRewardDetail(
			UserLockPositionGrantRewardDetail userLockPositionGrantRewardDetail) {
		userLockPositionGrantRewardDetailMapper.addUserLockPositionGrantRewardDetail(userLockPositionGrantRewardDetail);
	}
}
