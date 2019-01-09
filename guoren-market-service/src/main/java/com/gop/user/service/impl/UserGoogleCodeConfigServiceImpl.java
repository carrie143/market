package com.gop.user.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.code.consts.CommonCodeConst;
import com.gop.domain.UserGoogleCodeConfig;
import com.gop.domain.enums.UserGoogleCodeFlagType;
import com.gop.exception.AppException;
import com.gop.mapper.UserGoogleCodeConfigMapper;
import com.gop.mapper.UserMapper;
import com.gop.user.service.UserGoogleCodeConfigService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserGoogleCodeConfigServiceImpl implements UserGoogleCodeConfigService {
	@Autowired
	UserGoogleCodeConfigMapper userGoogleCodeConfigMapper;
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	private CredentialRepositoryMock credentialRepositoryMock;
	
	@Override
	public void updateGoogleCodeConfigByUid(Integer uid, UserGoogleCodeFlagType flag) {
		if (userMapper.selectByPrimaryKey(uid) == null) {
			log.info("用户不存在, uid:{}", uid);
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
		if(countGoogleCodeConfigByUid(uid)) {
			userGoogleCodeConfigMapper.updateUserGoogleCodeFlagByUid(uid, flag);
		}else {
			log.info("不存在用户设置谷歌验证码记录, uid:{}", uid);
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
	}

	@Override
	public UserGoogleCodeConfig selectUserGoogleCodeConfigByUid(Integer uid) {
		if (userMapper.selectByPrimaryKey(uid) == null) {
			log.info("用户不存在, uid:{}", uid);
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
		return userGoogleCodeConfigMapper.selectByUid(uid);
	}

	@Override
	public void updateGoogleCodeSecretByUid(Integer uid, String secretCode) {
		UserGoogleCodeConfig userGoogleCodeConfig = new UserGoogleCodeConfig();
		userGoogleCodeConfig.setUid(uid);
		userGoogleCodeConfig.setSecretCode(secretCode);
		userGoogleCodeConfigMapper.updateSelectiveByUid(userGoogleCodeConfig);
	}

	@Override
	public boolean countGoogleCodeConfigByUid(Integer uid) {
		int count = userGoogleCodeConfigMapper.countGoogleCodeConfigByUid(uid);
		return count > 0;
	}

	@Override
	public void addUserGoogleCodeConfig(Integer uid, UserGoogleCodeFlagType flag, String secretCode) {
		UserGoogleCodeConfig userGoogleCodeConfig = new UserGoogleCodeConfig();
		userGoogleCodeConfig.setUid(uid);
		userGoogleCodeConfig.setFlag(flag);
		userGoogleCodeConfig.setSecretCode(secretCode);
		userGoogleCodeConfigMapper.insertSelective(userGoogleCodeConfig);
	}

	@Override
	public boolean checkGoogleCodeCorrect(String email, String googlecode) {
		GoogleAuthenticator gAuth = new GoogleAuthenticator();
		credentialRepositoryMock.setFlag("REDIS");
		gAuth.setCredentialRepository(credentialRepositoryMock);
		boolean isCodeValid = gAuth.authorizeUser(email,Integer.parseInt(googlecode));
		return isCodeValid;
	}

	@Override
	public String generateGoogleKeyByEmail(String email) {
		GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
		googleAuthenticator.setCredentialRepository(credentialRepositoryMock);
		GoogleAuthenticatorKey key = googleAuthenticator.createCredentials(email);
		return key.getKey();
	}

	@Override
	public void resetUserGoogleCodeConfigByUid(Integer uid, UserGoogleCodeFlagType flag, String secretCode) {
		UserGoogleCodeConfig userGoogleCodeConfig = new UserGoogleCodeConfig();
		userGoogleCodeConfig.setUid(uid);
		userGoogleCodeConfig.setFlag(flag);
		userGoogleCodeConfig.setSecretCode(secretCode);
		userGoogleCodeConfig.setResetDate(new Date());
		userGoogleCodeConfigMapper.updateSelectiveByUid(userGoogleCodeConfig);
		
	}
}
