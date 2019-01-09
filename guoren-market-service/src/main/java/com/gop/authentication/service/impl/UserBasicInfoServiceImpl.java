package com.gop.authentication.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.authentication.service.UserBasicInfoService;
import com.gop.code.consts.CommonCodeConst;
import com.gop.domain.UserBasicInfo;
import com.gop.exception.AppException;
import com.gop.mapper.UserBasicInfoMapper;

import lombok.extern.slf4j.Slf4j;

@Service("UserBasicInfoService")
@Slf4j
public class UserBasicInfoServiceImpl implements UserBasicInfoService {
	@Autowired
	private UserBasicInfoMapper userBasicInfoMapper;

	@Override
	public void insertOrUpdate(UserBasicInfo userBasicInfo) {
		try {

			UserBasicInfo basicInfo = userBasicInfoMapper.selectByUid(userBasicInfo.getUid());
			if (null == basicInfo) {
				userBasicInfoMapper.insertSelective(userBasicInfo);
			} else {
				userBasicInfoMapper.updateByUid(userBasicInfo);
			}
		} catch (Exception e) {
			log.error("用户level0-1基础信息提交异常{}",e);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}

	}

	@Override
	public UserBasicInfo getBasicInfoByUid(Integer uid) {
		UserBasicInfo selectByUid = userBasicInfoMapper.selectByUid(uid);
		return selectByUid;
	}

}
