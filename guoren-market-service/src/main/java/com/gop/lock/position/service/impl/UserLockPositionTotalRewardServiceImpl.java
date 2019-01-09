package com.gop.lock.position.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gop.domain.UserLockPositionTotalReward;
import com.gop.lock.position.service.IUserLockPositionTotalRewardService;
import com.gop.mapper.UserLockPositionTotalRewardMapper;
import com.gop.mode.vo.PageModel;
@Service
public class UserLockPositionTotalRewardServiceImpl implements IUserLockPositionTotalRewardService {
	@Autowired
	private UserLockPositionTotalRewardMapper userLockPositionTotalRewardMapper;

	@Override
	public int insertSelective(UserLockPositionTotalReward record) {
		return userLockPositionTotalRewardMapper.insertSelective(record);
	}

	@Override
	public PageModel<UserLockPositionTotalReward> getTotalRewardList(String assetCode, Integer pageNo,
			Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		PageModel<UserLockPositionTotalReward> pageModel = new PageModel<>();
		PageInfo<UserLockPositionTotalReward> info = new PageInfo<>(
				userLockPositionTotalRewardMapper.selectByAssetCode(assetCode));
		pageModel.setList(info.getList());
		pageModel.setPageNo(info.getPageNum());
		pageModel.setPageNum(info.getPages());
		pageModel.setPageSize(info.getSize());
		pageModel.setTotal(info.getTotal());
		return pageModel;
	}

	@Override
	public UserLockPositionTotalReward selectTotalRewardByAssetCodeAndYearAndMonth(String assetCode, Integer year,
			Integer month) {
		return userLockPositionTotalRewardMapper.selectTotalRewardByAssetCodeAndYearAndMonth(assetCode, year, month);
	}

}
