//package com.gop.notify.service.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.gop.domain.UserApiKey;
//import com.gop.mapper.UserApiKeyMapper;
//import com.gop.notify.dto.ApiResponseDto;
//import com.gop.notify.dto.ExtractRequestInfoDto;
//import com.gop.notify.service.UserApiKeyService;
//
//@Service
//public class UserApiKeyImpl implements UserApiKeyService {
//	@Autowired
//	private UserApiKeyMapper userApiKeyMapper;
//
//	@Override
//	public UserApiKey getByUid(int uid) {
//		return userApiKeyMapper.selectByUserId(uid);
//	}
//
//	@Override
//	public UserApiKey getByUserNo(String userNo) {
//		// TODO Auto-generated method stub
//		return userApiKeyMapper.selectByUserNo(userNo);
//	}
//
//}
