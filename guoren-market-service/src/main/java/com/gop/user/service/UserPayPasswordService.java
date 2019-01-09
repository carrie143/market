package com.gop.user.service;

import com.gop.domain.UserPayPassword;
import com.gop.user.dto.CheckPayPasswordLockedDto;

public interface UserPayPasswordService {

	public CheckPayPasswordLockedDto CheckPayPasswordLockedTimes(Integer userId);

	public Integer addLockTimes(Integer uid);

	int insertSelective(UserPayPassword record);

	public void lockPayNumZero(Integer uid);
}
