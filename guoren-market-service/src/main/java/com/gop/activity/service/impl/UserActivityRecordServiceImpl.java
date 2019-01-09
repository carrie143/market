package com.gop.activity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.activity.service.UserActivityRecordService;
import com.gop.domain.UserActivityRecord;
import com.gop.mapper.UserActivityRecordMapper;

@Service("UserActivityRecordService")
public class UserActivityRecordServiceImpl implements UserActivityRecordService {
	@Autowired
	private UserActivityRecordMapper userActivityRecordMapper;

	@Override
	public int getCountByUidAndActivityType(Integer uid, String type) {
		return userActivityRecordMapper.getCountByUidAndActivityType(uid, type);
	}

	@Override
	public int saveUserActivityRecord(UserActivityRecord record) {
		return userActivityRecordMapper.insertRecord(record);
	}

	@Override
	public int getCountByActivityTypeAndCardTypeAndCardNo(String activityType,String cardType, String cardNo) {
		return userActivityRecordMapper.getCountByActivityTypeAndCardTypeAndCardNo(activityType,cardType,cardNo);
	}
}
