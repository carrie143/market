package com.gop.lock.position.service.impl;

import com.gop.domain.UserLockPositionOperDetail;
import com.gop.lock.position.service.IUserLockPositionOperDetailService;
import com.gop.mapper.UserLockPositionOperDetailMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLockPositionOperDetailServiceImpl implements IUserLockPositionOperDetailService{

    @Autowired
    private UserLockPositionOperDetailMapper userLockPositionOperDetailMapper;

    @Override
    public List<UserLockPositionOperDetail> getUserOperationList(Integer userId,String assetCode, Integer start, Integer pageSize) {
        return userLockPositionOperDetailMapper.getUserOperationList(userId,assetCode, start, pageSize);
    }

    @Override
    public Integer countUserOperation(Integer userId) {
        return userLockPositionOperDetailMapper.countUserOperation(userId);
    }

	@Override
	public void addUserLockPositionOperDetail(UserLockPositionOperDetail userLockPositionOperDetail) {
		userLockPositionOperDetailMapper.addUserLockPositionOperDetail(userLockPositionOperDetail);
	}
}
