package com.gop.activity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gop.activity.service.InviteActivityConfigService;
import com.gop.domain.InviteActivityConfig;
import com.gop.domain.enums.InviteActivityConfigStatus;
import com.gop.mapper.InviteActivityConfigMapper;
import com.gop.mode.vo.PageModel;

@Service
public class InviteActivityConfigServiceImpl implements InviteActivityConfigService {

	@Autowired
	private InviteActivityConfigMapper inviteActivityConfigMapper;

	@Override
	public InviteActivityConfig getInviteActivityConfigByActivityName(String activityName) {

		return inviteActivityConfigMapper.getInviteActivityConfigByActivityName(activityName);
	}

	@Override
	public void createOrUpdate(InviteActivityConfig config) {
		inviteActivityConfigMapper.createOrUpdate(config);
	}

	@Override
	public PageModel<InviteActivityConfig> queryInviteActivityConfig(Integer pageNum, Integer pageSize,
			String activityName) {
		PageHelper.startPage(pageNum, pageSize);
		PageModel<InviteActivityConfig> pageModel = new PageModel<>();
		PageInfo<InviteActivityConfig> pageInfo = new PageInfo<>(inviteActivityConfigMapper.queryConfig(activityName));
		pageModel.setPageNo(pageNum);
		pageModel.setPageSize(pageSize);
		pageModel.setPageNum(pageInfo.getPages());
		pageModel.setTotal(pageInfo.getTotal());
		pageModel.setList(pageInfo.getList());

		return pageModel;
	}

	@Override
	public InviteActivityConfig getInviteActivityConfigById(Integer activityId) {
		return inviteActivityConfigMapper.selectByPrimaryKey(activityId);
	}

	@Override
	public int countInviteActivityConfigByStatus(InviteActivityConfigStatus status) {
		return inviteActivityConfigMapper.countInviteActivityConfigByStatus(status);
	}
}
