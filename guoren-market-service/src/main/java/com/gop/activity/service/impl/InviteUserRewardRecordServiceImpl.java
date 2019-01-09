package com.gop.activity.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.gop.activity.service.InviteUserRewardRecordService;
import com.gop.domain.InviteUserRewardRecord;
import com.gop.domain.enums.InviteActivityRewardConfigInviteType;
import com.gop.mapper.InviteUserRewardRecordMapper;

@Service
public class InviteUserRewardRecordServiceImpl implements InviteUserRewardRecordService {

	@Autowired
	private InviteUserRewardRecordMapper inviteUserRewardRecordMapper;
	
	@Override
	public List<InviteUserRewardRecord> getInviteUserRewardRecordListByUidAndActivityId(Integer uid,
			Integer activityId) {
		return inviteUserRewardRecordMapper.getInviteUserRewardRecordListByUidAndActivityId(uid, activityId);
	}

	@Override
	public List<InviteUserRewardRecord> selectInviteUserRewardRecord(Integer uid, Integer activityId, Integer pageNo,
			Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		PageHelper.orderBy("id asc");
		return inviteUserRewardRecordMapper.getInviteUserRewardRecordListByUidAndActivityId(uid, activityId);
	}

	@Override
	public List<InviteUserRewardRecord> getInviteUserRewardRecordListByUidAndActivityIdAndType(Integer uid,
			Integer activityId, InviteActivityRewardConfigInviteType inviteType) {
		return inviteUserRewardRecordMapper.getInviteUserRewardRecordListByUidAndActivityIdAndType(uid, activityId,inviteType);
	}

	@Override
	public int countInviteUserRewardRecordByUidAndActivityIdAndType(Integer uid, Integer activityId,
			InviteActivityRewardConfigInviteType inviteType) {
		return inviteUserRewardRecordMapper.countInviteUserRewardRecordByUidAndActivityIdAndType(uid, activityId, inviteType);
	}

	@Override
	public int createRewardRecord(InviteUserRewardRecord record) {
		return inviteUserRewardRecordMapper.insertSelective(record);
	}
}
