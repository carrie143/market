package com.gop.activity.service;

import com.gop.domain.UserActivityRecord;

public interface UserActivityRecordService {
	
	public int getCountByUidAndActivityType(Integer uid,String type);
	
	public int saveUserActivityRecord(UserActivityRecord record);
	
	public int getCountByActivityTypeAndCardTypeAndCardNo(String activityType,String cardType,String cardNo);
}
