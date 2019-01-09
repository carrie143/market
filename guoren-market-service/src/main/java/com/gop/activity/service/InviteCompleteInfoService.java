package com.gop.activity.service;

import java.util.List;

import com.gop.domain.InviteCompleteInfo;

public interface InviteCompleteInfoService {

	public int countInviteCompleteInfoByInviteUidAndActivityId(Integer inviteUid,Integer activityId);
	
	public List<InviteCompleteInfo> selectInviteCompleteInfoByInviteUidAndActivityId(Integer inviteUid,Integer activityId,
			Integer pageNo,Integer pageSize);

	public InviteCompleteInfo getInfoByUidAndActivityId(Integer uid, Integer activityId);

	public int createInfo(InviteCompleteInfo completeInfo);
}
