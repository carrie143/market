package com.gop.activity.service;

import com.gop.domain.InviteUserInfo;

public interface InviteUserInfoService {

	public InviteUserInfo getInviteUserInfoByUid(Integer uid);
	
	public int addInviteUserInfo(InviteUserInfo inviteUserInfo);
	
	public InviteUserInfo getInviteUserInfoByInviteCode(String inviteCode);
}
