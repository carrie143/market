package com.gop.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gop.domain.UserLoginLog;
import com.gop.mapper.UserLoginLogMapper;
import com.gop.mode.vo.PageModel;
import com.gop.user.service.UserLoginLogService;

@Service
public class UserLoginLogServiceImpl implements UserLoginLogService {

	@Autowired
	private UserLoginLogMapper userLoginLogMapper;
	
	@Override
	public PageModel<UserLoginLog> getUserLoginLog(UserLoginLog userLoginLog,Integer pageNo,
			Integer pageSize) {
		PageHelper.orderBy("create_date desc");
		PageHelper.startPage(pageNo, pageSize);
		List<UserLoginLog> userLoginLogs = userLoginLogMapper.selectByIpAddress(userLoginLog.getIpAddress());
		PageModel<UserLoginLog> pageModel = new PageModel<>();
		if (null == userLoginLogs || userLoginLogs.isEmpty()) {
			return new PageModel<>();
		}
		Page<UserLoginLog> page = (Page<UserLoginLog>) userLoginLogs;
		pageModel.setPageNo(pageNo);
		pageModel.setPageNum(page.getPageNum());
		pageModel.setPageSize(pageSize);
		pageModel.setTotal(page.getTotal());
		pageModel.setList(userLoginLogs);
		return pageModel;
	}

	@Override
	public UserLoginLog getFirstLoginByUid(Integer uid) {
		return userLoginLogMapper.getFirstLoginByUid(uid);
	}

}
