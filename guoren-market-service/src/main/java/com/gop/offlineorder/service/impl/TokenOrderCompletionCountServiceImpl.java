package com.gop.offlineorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.domain.TokenOrderCompletionCount;
import com.gop.mapper.TokenOrderCompletionCountMapper;
import com.gop.offlineorder.service.TokenOrderCompletionCountService;

@Service("TokenOrderCompletionCountService")
public class TokenOrderCompletionCountServiceImpl implements TokenOrderCompletionCountService {
	@Autowired
	private TokenOrderCompletionCountMapper tokenOrderCompletionCountMapper;

	@Override
	public void saveOrUpdateCount(TokenOrderCompletionCount record) {
		tokenOrderCompletionCountMapper.insertSelective(record);
	}

	@Override
	public Integer selectTotalByUid(Integer uid) {
		
		TokenOrderCompletionCount tokenOrderCompletionCount = tokenOrderCompletionCountMapper.selectByUid(uid);
		if(tokenOrderCompletionCount == null) {
			return 0;
		}
		
		return tokenOrderCompletionCount.getTotalCount().intValue();
	}	
	
}
