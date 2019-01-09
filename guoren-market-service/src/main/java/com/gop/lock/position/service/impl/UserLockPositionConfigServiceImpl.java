package com.gop.lock.position.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gop.domain.UserLockPositionConfig;
import com.gop.domain.UserLockPositionConfigType;
import com.gop.lock.position.service.IUserLockPositionConfigService;
import com.gop.mapper.UserLockPositionConfigMapper;
import com.gop.mode.vo.PageModel;

@Service
public class UserLockPositionConfigServiceImpl implements IUserLockPositionConfigService {
	@Autowired
	private UserLockPositionConfigMapper userLockPositionConfigMapper;

	@Override
	public UserLockPositionConfig getConfigByAssetCodeAndKey(String assetCode, UserLockPositionConfigType lockday) {
		return userLockPositionConfigMapper.selectConfigByAssetCodeAndKey(assetCode, lockday);
	}

	@Override
	public int createOrUpdateConfig(UserLockPositionConfig config) {
		return userLockPositionConfigMapper.createOrUpdateConfig(config);
	}

	@Override
	public PageModel<UserLockPositionConfig> getConfigList(String assetCode, Integer pageNo, Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		PageInfo<UserLockPositionConfig> info = new PageInfo<>(userLockPositionConfigMapper.selectConfigListByAssetCode(assetCode));
		PageModel<UserLockPositionConfig> pageModel = new PageModel<>();
		pageModel.setList(info.getList());
		pageModel.setPageNo(info.getPageNum());
		pageModel.setPageNum(info.getPages());
		pageModel.setPageSize(info.getSize());
		pageModel.setTotal(info.getTotal());
		
		return pageModel;
	}
}
