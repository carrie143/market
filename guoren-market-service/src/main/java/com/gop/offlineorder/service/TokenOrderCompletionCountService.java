package com.gop.offlineorder.service;

import com.gop.domain.TokenOrderCompletionCount;

public interface TokenOrderCompletionCountService {

	void saveOrUpdateCount(TokenOrderCompletionCount record);
	
	public Integer selectTotalByUid(Integer uid);

}
