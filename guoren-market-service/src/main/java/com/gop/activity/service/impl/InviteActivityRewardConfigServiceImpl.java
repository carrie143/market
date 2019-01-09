package com.gop.activity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gop.activity.service.InviteActivityRewardConfigService;
import com.gop.domain.InviteActivityRewardConfig;
import com.gop.domain.enums.InviteActivityRewardConfigInviteType;
import com.gop.domain.enums.InviteActivityRewardConfigStatus;
import com.gop.mapper.InviteActivityRewardConfigMapper;
import com.gop.mode.vo.PageModel;
@Service("InviteActivityRewardConfigService")
public class InviteActivityRewardConfigServiceImpl implements InviteActivityRewardConfigService {
	@Autowired
	private InviteActivityRewardConfigMapper inviteActivityRewardConfigMapper;
	@Override
	public int createOrUpdate(InviteActivityRewardConfig config) {
		return inviteActivityRewardConfigMapper.createOrUpdate(config);
	}

	@Override
	public List<InviteActivityRewardConfig> getInviteActivityRewardConfigListByActivityId(Integer activityId,
			InviteActivityRewardConfigInviteType inviteType,InviteActivityRewardConfigStatus status) {
		return inviteActivityRewardConfigMapper.getInviteActivityRewardConfigListByActivityId(activityId,inviteType,status);
	}

	@Override
	public PageModel<InviteActivityRewardConfig> queryInviteRewardConfig(Integer pageNo, Integer pageSize,
			Integer activityId) {
		PageHelper.startPage(pageNo, pageSize);
		PageHelper.orderBy(" activity_id ");;
		PageModel<InviteActivityRewardConfig> pageModel = new PageModel<>();
		PageInfo<InviteActivityRewardConfig> pageInfo = new PageInfo<>(inviteActivityRewardConfigMapper.queryRewardConfig(activityId));
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(pageSize);
		pageModel.setPageNum(pageInfo.getPages());
		pageModel.setTotal(pageInfo.getTotal());
		pageModel.setList(pageInfo.getList());
		return pageModel;
	}

}
