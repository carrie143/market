package com.gop.uploadLog.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.domain.UserUploadResourceLog;
import com.gop.mapper.UserUploadResourceLogMapper;
import com.gop.uploadLog.UserUploadResourcLogService;

@Service("UserUploadResourcLogServiceImpl")
public class UserUploadResourcLogServiceImpl implements UserUploadResourcLogService{

	@Autowired
	private UserUploadResourceLogMapper logMapper;
	@Override
	public int loggingUserUpload(UserUploadResourceLog log) {
		return logMapper.insertSelective(log);
	}
	@Override
	public Boolean checkIsUser(Integer uid, String tag) {
		return  logMapper.selectLogByTagAndUid(uid,tag) != null ? true : false;
	}
	@Override
	public UserUploadResourceLog queryLogByTagAndUid(Integer uid, String name) {
		return logMapper.selectLogByTagAndUid(uid,name);
	}

}
