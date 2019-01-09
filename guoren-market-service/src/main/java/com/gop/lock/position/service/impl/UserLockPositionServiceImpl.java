package com.gop.lock.position.service.impl;

import com.gop.domain.UserLockPosition;
import com.gop.lock.position.service.IUserLockPositionService;
import com.gop.mapper.UserLockPositionMapper;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLockPositionServiceImpl implements IUserLockPositionService{

    @Autowired
    private UserLockPositionMapper userLockPositionMapper;

	@Override
	public UserLockPosition selectByUidAndAssetCode(Integer uid, String assetCode) {
		return userLockPositionMapper.selectByUidAndAssetCode(uid, assetCode);
	}

	@Override
	public void addUserLockPosition(UserLockPosition userLockPosition) {
		userLockPositionMapper.addUserLockPosition(userLockPosition);
		
	}

	@Override
	public void updateUserLockPositionAmountByUidAndAssetCode(UserLockPosition userLockPosition) {
		userLockPositionMapper.updateUserLockPositionAmountByUidAndAssetCode(userLockPosition);
	}

	@Override
	public void updateUserLockPositionAmountByid(Integer id, BigDecimal amount) {
		userLockPositionMapper.updateUserLockPositionAmountByid(id,amount);
	}
}
