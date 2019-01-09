package com.gop.activity.service;

import java.util.List;

import com.gop.domain.InviteUserRewardRecord;
import com.gop.domain.enums.InviteActivityRewardConfigInviteType;

public interface InviteUserRewardRecordService {

	public List<InviteUserRewardRecord> getInviteUserRewardRecordListByUidAndActivityId(Integer uid,Integer activityId);
	
	public List<InviteUserRewardRecord> selectInviteUserRewardRecord(Integer uid,Integer activityId,Integer pageNo,Integer pageSize);
	
	public List<InviteUserRewardRecord> getInviteUserRewardRecordListByUidAndActivityIdAndType(Integer uid,Integer activityId,
			InviteActivityRewardConfigInviteType inviteType);
	
	public int countInviteUserRewardRecordByUidAndActivityIdAndType(Integer uid,Integer activityId,
			InviteActivityRewardConfigInviteType inviteType);

	public int createRewardRecord(InviteUserRewardRecord record);
}
