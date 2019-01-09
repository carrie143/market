package com.gop.user.service;


import com.gop.domain.UserLoginLog;
import com.gop.mode.vo.PageModel;

public interface UserLoginLogService {
	
	public PageModel<UserLoginLog> getUserLoginLog(UserLoginLog userLoginLog,Integer pageNo,
			Integer pageSize);
	public UserLoginLog getFirstLoginByUid(Integer uid);
} 
