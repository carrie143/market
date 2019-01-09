package com.gop.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.domain.UserInfo;
import com.gop.mapper.UserInfoMapper;
import com.gop.user.service.UserInfoService;

@Service("UserInfoService")
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public UserInfo selectByPrimaryKey(Integer uid) {
		return userInfoMapper.selectByPrimaryKey(uid);
	}

	@Override
	public void insertSelective(UserInfo userInfo) {
		userInfoMapper.insertSelective(userInfo);
	}

}
