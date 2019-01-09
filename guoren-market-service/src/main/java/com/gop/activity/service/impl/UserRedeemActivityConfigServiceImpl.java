package com.gop.activity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gop.activity.service.UserRedeemActivityConfigService;
import com.gop.code.consts.ActivityCodeConst;
import com.gop.domain.UserRedeemActivityConfig;
import com.gop.domain.enums.UserRedeemActivityConfigStatus;
import com.gop.exception.AppException;
import com.gop.mapper.UserRedeemActivityConfigMapper;
import com.gop.mode.vo.PageModel;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service("UserRedeemActivityConfigService")
public class UserRedeemActivityConfigServiceImpl implements UserRedeemActivityConfigService {
	@Autowired
	private UserRedeemActivityConfigMapper userRedeemActivityConfigMapper;

	@Override
	public UserRedeemActivityConfig selectUserRedeemActivityConfigById(Integer id) {
		return userRedeemActivityConfigMapper.selectByPrimaryKey(id);
	}
	@Override
	public void createOrUpdateConfig(UserRedeemActivityConfig config) {
		int result = userRedeemActivityConfigMapper.insertOrUpdate(config);
		if (result <= 0) {
			throw new AppException(ActivityCodeConst.ACTIVITY_CONFIG_ERROR);
		}
	}

	@Override
	public PageModel<UserRedeemActivityConfig> queryConfigList(
			UserRedeemActivityConfigStatus status, Integer pageNo, Integer pageSize) {
		PageHelper.startPage(pageNo,pageSize);
		PageHelper.orderBy("status,id desc");
		PageInfo<UserRedeemActivityConfig> pageInfo = new PageInfo<>(
				userRedeemActivityConfigMapper.getConfigListByStatus(status));
		PageModel<UserRedeemActivityConfig> pageModel = new PageModel<>();
		pageModel.setList(pageInfo.getList());
		pageModel.setPageNo(pageInfo.getPageNum());
		pageModel.setPageSize(pageInfo.getPageSize());
		pageModel.setTotal(pageInfo.getTotal());
		return pageModel;
	}

}
