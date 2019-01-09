package com.gop.activity.service;

import com.gop.domain.UserRedeemActivityConfig;
import com.gop.domain.enums.UserRedeemActivityConfigStatus;
import com.gop.mode.vo.PageModel;

public interface UserRedeemActivityConfigService {

	
	public UserRedeemActivityConfig selectUserRedeemActivityConfigById(Integer id);
	
	void createOrUpdateConfig(UserRedeemActivityConfig config);

	PageModel<UserRedeemActivityConfig> queryConfigList(
			UserRedeemActivityConfigStatus status, Integer pageNo, Integer pageSize);

}
