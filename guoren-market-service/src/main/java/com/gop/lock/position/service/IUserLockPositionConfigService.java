package com.gop.lock.position.service;

import com.gop.domain.UserLockPositionConfig;
import com.gop.domain.UserLockPositionConfigType;
import com.gop.mode.vo.PageModel;

public interface IUserLockPositionConfigService {

	public UserLockPositionConfig getConfigByAssetCodeAndKey(String coinType, UserLockPositionConfigType lockday);

	public int createOrUpdateConfig(UserLockPositionConfig config);

	public PageModel<UserLockPositionConfig> getConfigList(String assetCode, Integer pageNo, Integer pageSize);

}
