package com.gop.user.service;

import com.gop.domain.UserInfo;

public interface UserInfoService {

	public UserInfo selectByPrimaryKey(Integer uid);

	public void insertSelective(UserInfo userInfo);
	
}
