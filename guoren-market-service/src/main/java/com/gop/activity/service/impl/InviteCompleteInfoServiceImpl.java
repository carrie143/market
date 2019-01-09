package com.gop.activity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.gop.activity.service.InviteCompleteInfoService;
import com.gop.domain.InviteCompleteInfo;
import com.gop.mapper.InviteCompleteInfoMapper;

@Service
public class InviteCompleteInfoServiceImpl implements InviteCompleteInfoService {
	
	@Autowired
	private InviteCompleteInfoMapper inviteCompleteInfoMapper;

	@Override
	public int countInviteCompleteInfoByInviteUidAndActivityId(Integer inviteUid, Integer activityId) {
		return inviteCompleteInfoMapper.countInviteCompleteInfoByInviteUidAndActivityId(inviteUid,activityId);
	}

	@Override
	public List<InviteCompleteInfo> selectInviteCompleteInfoByInviteUidAndActivityId(Integer inviteUid,
			Integer activityId, Integer pageNo, Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		PageHelper.orderBy("id asc");
		return inviteCompleteInfoMapper.selectInviteCompleteInfoByInviteUidAndActivityId(inviteUid, activityId);
	}

	@Override
	public InviteCompleteInfo getInfoByUidAndActivityId(Integer uid, Integer activityId) {
		return inviteCompleteInfoMapper.selectInviteCompleteInfoByUidAndActivityId(uid, activityId);
	}

	@Override
	public int createInfo(InviteCompleteInfo completeInfo) {
		return inviteCompleteInfoMapper.insertSelective(completeInfo);
	}

}
