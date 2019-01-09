package com.gop.match.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.UserCodeConst;
import com.gop.domain.UserTransactionFeeWhiteList;
import com.gop.domain.enums.Status;
import com.gop.exception.AppException;
import com.gop.mapper.UserMapper;
import com.gop.mapper.UserTransactionFeeWhiteListMapper;
import com.gop.match.service.UserTransactionFeeWhiteListService;
import com.gop.mode.vo.PageModel;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserTransactionFeeWhiteListServiceImpl implements UserTransactionFeeWhiteListService {
	@Autowired
	UserTransactionFeeWhiteListMapper userTransactionFeeWhiteListMapper;

	@Autowired
	UserMapper userMapper;

	@Override
	public boolean checkUserinWhiteList(Integer uid) {
		// 查询白名单结果(xml将flag写死为valid)
		int count = userTransactionFeeWhiteListMapper.countUserInWhiteByUid(uid);
		return count > 0;
	}

	@Override
	public PageModel<UserTransactionFeeWhiteList> getUserTransactionFeeWhiteList(
			UserTransactionFeeWhiteList userTransactionFeeWhiteList, Integer pageNo, Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		// List<UserTransactionFeeWhiteList> userTransactionFeeWhiteLists =
		// userTransactionFeeWhiteListMapper
		// .selectByUid(userTransactionFeeWhiteList.getUid());
		// 查询selectall,无参数
		List<UserTransactionFeeWhiteList> userTransactionFeeWhiteLists = userTransactionFeeWhiteListMapper.selectAll();

		PageModel<UserTransactionFeeWhiteList> pageModel = new PageModel<>();
		if (null == userTransactionFeeWhiteLists || userTransactionFeeWhiteLists.isEmpty()) {
//			userTransactionFeeWhiteLists = Collections.EMPTY_LIST;
			 return new PageModel<>();
		}
		Page<UserTransactionFeeWhiteList> page = (Page<UserTransactionFeeWhiteList>) userTransactionFeeWhiteLists;
		pageModel.setPageNo(pageNo);
		pageModel.setPageNum(page.getPageNum());
		pageModel.setPageSize(pageSize);
		pageModel.setTotal(page.getTotal());
		pageModel.setList(userTransactionFeeWhiteLists);
		return pageModel;

	}

	@Override
	public void addUserTransactionFeeWhiteList(UserTransactionFeeWhiteList userTransactionFeeWhiteList) {
		if (userMapper.selectByPrimaryKey(userTransactionFeeWhiteList.getUid()) == null) {
			log.info("用户不存在, uid:{}", userTransactionFeeWhiteList.getUid());
			throw new AppException(UserCodeConst.USER_ID_NOT_EXIST);
		}
		userTransactionFeeWhiteList.setFlag(Status.VALID);
		if (checkUidinWhiteList(userTransactionFeeWhiteList.getUid())) {
			userTransactionFeeWhiteListMapper.updateByUidSelective(userTransactionFeeWhiteList);
		} else {
			userTransactionFeeWhiteListMapper.insertSelective(userTransactionFeeWhiteList);
		}
	}

	@Override
	public void updateByUidInValid(Integer uid) {
		if (userTransactionFeeWhiteListMapper.updateByUidInValid(uid) < 1) {
			log.error("删除白名单用户失败, uid:{}", uid);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
	}

	private boolean checkUidinWhiteList(Integer uid) {
		int count = userTransactionFeeWhiteListMapper.checkUidInWhiteListByUid(uid);
		return count > 0;
	}

}
