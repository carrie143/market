package com.gop.activity.service;

import com.gop.domain.UserRedeemActivity;
import com.gop.domain.enums.UserRedeemActivityStatus;

public interface UserRedeemActivityService {
	
	
	public void receiveRedeemActivity(Integer uid,String assetCode,String redeemCode);
	
	public UserRedeemActivity getRedeemActivityByAssetCodeAndRedeemCode(String assetCode,String redeemCode);

	int updateRedeemActivityStatusAndUid(Integer id,Integer uid, UserRedeemActivityStatus beginStatus,
			UserRedeemActivityStatus endStatus);

}
