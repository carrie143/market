package com.gop.activity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.activity.service.UserRedeemActivityDetailService;
import com.gop.domain.UserRedeemActivityDetail;
import com.gop.mapper.UserRedeemActivityDetailMapper;

@Service
public class UserRedeemActivityDetailServiceImpl implements UserRedeemActivityDetailService {
	
	@Autowired
	private UserRedeemActivityDetailMapper userRedeemActivityDetailMapper;
	
	@Override
	public void addUserRedeemActivityDetail(UserRedeemActivityDetail userRedeemActivityDetail) {
		userRedeemActivityDetailMapper.addUserRedeemActivityDetail(userRedeemActivityDetail);
	}

}
