package com.gop.activity.service;

import com.gop.domain.InviteActivityRewardConfig;
import com.gop.domain.enums.InviteActivityRewardConfigInviteType;
import com.gop.domain.enums.InviteActivityRewardConfigStatus;
import com.gop.mode.vo.PageModel;

import java.util.List;


public interface InviteActivityRewardConfigService {

	int createOrUpdate(InviteActivityRewardConfig config);

	public List<InviteActivityRewardConfig> getInviteActivityRewardConfigListByActivityId(Integer activityId,
			InviteActivityRewardConfigInviteType inviteType,InviteActivityRewardConfigStatus status);

	PageModel<InviteActivityRewardConfig> queryInviteRewardConfig(Integer pageNo, Integer pageSize, Integer activityId);
}
