package com.gop.authentication.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gop.authentication.service.UserResidenceService;
import com.gop.code.consts.AuditCodeConst;
import com.gop.code.consts.CommonCodeConst;
import com.gop.domain.User;
import com.gop.domain.UserResidence;
import com.gop.domain.enums.AuditDealStatus;
import com.gop.domain.enums.AuditFirst;
import com.gop.domain.enums.AuditStatus;
import com.gop.exception.AppException;
import com.gop.mapper.UserResidenceMapper;
import com.gop.mode.vo.PageModel;
import com.gop.user.facade.UserFacade;
import com.gop.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service("UserResidenceServices")
@Slf4j
public class UserResidenceServiceImpl implements UserResidenceService {
	@Autowired
	private UserResidenceMapper userResidenceMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private UserFacade userFacade;

	@Override
	public void insertUserResidence(UserResidence userResidence) {
		UserResidence lastResidence = userResidenceMapper.getLastResidenceInfoByUid(userResidence.getUid());

		if (lastResidence != null && !AuditStatus.FAIL.equals(lastResidence.getAuditStatus())) {
			log.info("用户地址认证异常,存在已认证过或在审批");
			throw new AppException(AuditCodeConst.UNVERIFY_EXIST, null);
		}

		if (lastResidence != null) {
			userResidence.setAuditFirst(AuditFirst.NO);
		} else {
			userResidence.setAuditFirst(AuditFirst.YES);
		}

		userResidence.setAuditStatus(AuditStatus.INIT);
		userResidence.setStatus(AuditDealStatus.INIT);
		userResidenceMapper.insertSelective(userResidence);
	}

	@Override
	public UserResidence getUserResidenceInfoById(Integer id) {
		UserResidence residenceInfoById = userResidenceMapper.getResidenceInfoById(id);
		if (null == residenceInfoById) {
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
		return residenceInfoById;
	}

	@Override
	public PageModel<UserResidence> getUserResidencePageModel(Integer uid, AuditDealStatus status, Integer pageNo,
			Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		PageModel<UserResidence> pageModel = new PageModel<>();
		try {
			PageInfo<UserResidence> pageInfo = new PageInfo<>(userResidenceMapper.getResidenceInfoList(uid, status));
			pageModel.setPageNo(pageNo);
			pageModel.setPageSize(pageInfo.getPageNum());
			pageModel.setPageNum(pageInfo.getPages());
			pageModel.setTotal(pageInfo.getTotal());
			// pageModel.setList(residenceInfoList);
			// pageModel.setList(pageInfo.getList());
			pageModel.setList(pageInfo.getList().stream().map(r -> {
				User user = userFacade.getUser(null != uid ? uid : r.getUid());
				String email = null!=user?user.getEmail():"";
				r.setEmail(email);
				return r;
			}).collect(Collectors.toList()));
		} catch (Exception e) {
			log.error("查询居住地址页面失败", e);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
		return pageModel;
	}

	@Override
	public void updateUserResidenc(UserResidence userResidenceInfoById) {
		try {
			userResidenceMapper.updateByPrimaryKeySelective(userResidenceInfoById);
		} catch (Exception e) {
			log.error("跟新用户地址认证信息失败", e);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}

	}

	@Override
	public UserResidence getLastUserResidenceInfo(Integer uid) {
		try {
			UserResidence lastResidenceInfo = userResidenceMapper.getLastResidenceInfoByUid(uid);
			return lastResidenceInfo;
		} catch (Exception e) {
			log.error("查询最后一条用户地址认证数据异常", e);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
	}

	@Override
	public List<UserResidence> getIdentityHistoryListByLimitNo(Integer uid, Integer limitNo) {
		try {
			List<UserResidence> list = userResidenceMapper.getIdentityHistoryListByLimitNo(uid, limitNo);
			return list;
		} catch (Exception e) {
			log.error("查询限定数量的用户地址认证数据异常", e);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
	}

	@Override
	public Integer getAmountOfResidenceWithStatus(AuditStatus status) {
		try {
			return userResidenceMapper.getAmountOfResidenceWithStatus(status);
		} catch (Exception e) {
			log.error("查询限定状态的用户地址认证数据异常", e);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
	}

	@Override
	public UserResidence getUserByUidAndStatusAndAuditStatus(Integer uid, AuditDealStatus dealStatus,
			AuditStatus status) {
		try {
			return userResidenceMapper.getUserByUidAndStatusAndAuditStatus(uid, dealStatus, status);
		} catch (Exception e) {
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
	}
	@Override
	public int countUserLevel(Date date) {
		return userResidenceMapper.countUserLevel(date);
	}

}
