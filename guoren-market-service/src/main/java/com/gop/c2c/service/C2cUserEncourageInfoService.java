package com.gop.c2c.service;

import com.gop.domain.C2cUserEncourageInfo;

public interface C2cUserEncourageInfoService {
	
	public Integer getC2cUserEncourageCount(Integer uid);
	
	public C2cUserEncourageInfo selectByUid(Integer uid);
	
	public void addUserEncourageIfo(C2cUserEncourageInfo c2cUserEncourageInfo);
	
	public void updateUserEncourageIfoCountByUid(Integer uid);
	
}
