package com.gop.match.service;


import com.gop.domain.UserTransactionFeeWhiteList;
import com.gop.mode.vo.PageModel;

public interface UserTransactionFeeWhiteListService {
	
	public boolean checkUserinWhiteList(Integer uid);
	
	public PageModel<UserTransactionFeeWhiteList> getUserTransactionFeeWhiteList(UserTransactionFeeWhiteList userTransactionFeeWhiteList,Integer pageNo,
			Integer pageSize);
	
	public void addUserTransactionFeeWhiteList(UserTransactionFeeWhiteList userTransactionFeeWhiteList);
	
	public void updateByUidInValid(Integer uid);
		
}
