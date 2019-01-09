package com.gop.user.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.code.consts.GoogleCodeConst;
import com.gop.domain.ManagerGoogleCodeConfig;
import com.gop.domain.enums.DelFlag;
import com.gop.exception.AppException;
import com.gop.mapper.ManagerGoogleCodeConfigMapper;
import com.gop.user.service.ManagerGoogleCodeConfigService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ManagerGoogleCodeConfigServiceImpl implements ManagerGoogleCodeConfigService {
	@Autowired
	private ManagerGoogleCodeConfigMapper managerGoogleCodeConfigMapper;
	
	@Autowired
	private ManagerCredentialRepositoryMock managerCredentialRepositoryMock;
	
	@Override
	public boolean countManagerGoogleCodeConfigByAdminId(Integer adminId) {
		int count = managerGoogleCodeConfigMapper.countManagerGoogleCodeConfigByAdminId(adminId);
		return count > 0;
	}

	@Override
	public void addManagerGoogleCodeConfig(Integer adminId, String secretCode, DelFlag delFlag) {
		//校验该管理员是否设置过谷歌验证码
		if (null != selectManagerGoogleCodeConfigByAdminId(adminId,DelFlag.FALSE)) {
			throw new AppException(GoogleCodeConst.MANAGER_GOOGLE_CODE_HAS_SET);
		}
		ManagerGoogleCodeConfig managerGoogleCodeConfig  = new ManagerGoogleCodeConfig();
		managerGoogleCodeConfig.setAdminId(adminId);
		managerGoogleCodeConfig.setSecretCode(secretCode);
		managerGoogleCodeConfig.setDelFlag(delFlag);
		managerGoogleCodeConfigMapper.addManagerGoogleCodeConfig(managerGoogleCodeConfig);
	}
	
	@Override
	public boolean checkGoogleCodeCorrect(String mobile, String googlecode) {
		GoogleAuthenticator gAuth = new GoogleAuthenticator();
		managerCredentialRepositoryMock.setFlag("REDIS");
		gAuth.setCredentialRepository(managerCredentialRepositoryMock);
		boolean isCodeValid = gAuth.authorizeUser(mobile,Integer.parseInt(googlecode));
		return isCodeValid;
	}

	@Override
	public String generateGoogleKeyByMobile(String mobile) {
		GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
		googleAuthenticator.setCredentialRepository(managerCredentialRepositoryMock);
		GoogleAuthenticatorKey key = googleAuthenticator.createCredentials(mobile);
		return key.getKey();
	}

	@Override
	public ManagerGoogleCodeConfig selectManagerGoogleCodeConfigByAdminId(Integer adminId, DelFlag delFlag) {
		return managerGoogleCodeConfigMapper.selectManagerGoogleCodeConfigByAdminId(adminId, delFlag);
	}

	@Override
	public void updateManagerGoogleCodeConfigByAdminIdAndDelFlag(Integer adminId, DelFlag beginFlag, DelFlag endFlag) {
		managerGoogleCodeConfigMapper.updateManagerGoogleCodeConfigByAdminIdAndDelFlag(adminId, beginFlag, endFlag);
	}
	
}
