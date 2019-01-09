package com.gop.activity.service;

import com.gop.domain.InviteActivityConfig;
import com.gop.domain.enums.InviteActivityConfigStatus;
import com.gop.mode.vo.PageModel;

public interface InviteActivityConfigService {
      
	InviteActivityConfig getInviteActivityConfigByActivityName(String activityName);

	void createOrUpdate(InviteActivityConfig config);

	PageModel<InviteActivityConfig> queryInviteActivityConfig(Integer pageNo, Integer pageSize, String activityName);

	InviteActivityConfig getInviteActivityConfigById(Integer activityId);
	
	int countInviteActivityConfigByStatus(InviteActivityConfigStatus status);
}
