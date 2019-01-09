package com.gop.user.facade.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.UserCodeConst;
import com.gop.domain.Administrators;
import com.gop.domain.ManagerGoogleCodeConfig;
import com.gop.domain.enums.DelFlag;
import com.gop.exception.AppException;
import com.gop.user.facade.AdministratorsFacade;
import com.gop.user.service.AdministractorService;
import com.gop.user.service.ManagerGoogleCodeConfigService;
import com.gop.user.service.impl.ManagerCredentialRepositoryMock;
import com.gop.util.ConstantUtil;
import com.gop.util.CryptoUtils;
import com.gop.util.MD5Util;
import com.warrenstrange.googleauth.GoogleAuthenticator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdministratorsFacadeImpl implements AdministratorsFacade {

	@Autowired
	private AdministractorService administractorService;
	
	@Autowired
	private ManagerCredentialRepositoryMock managerCredentialRepositoryMock;
	
	@Autowired
	private ManagerGoogleCodeConfigService managerGoogleCodeConfigService;

	@Override
	public boolean checkLoginPwd(String account, String loginPasswod) {

		if (StringUtils.isEmpty(loginPasswod)) {
			log.info("登录密码为null");
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
		loginPasswod = ConstantUtil.charConvert(loginPasswod);

		Administrators administrators = administractorService.getAdministractor(account);
		if (administrators == null) {
			throw new AppException(UserCodeConst.NO_REGISTER);
		}
		String salt = MD5Util.genMD5Code(administrators.getCreateAdminId() + administrators.getMobile());
		return CryptoUtils.verify(administrators.getLoginPassword(), MD5Util.genMD5Code(loginPasswod), salt);
	}

	@Override
	public boolean checkLoginPwd(int uid, String loginPassword) {
		if (StringUtils.isEmpty(loginPassword)) {
			log.info("登录密码为null");
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
		loginPassword = ConstantUtil.charConvert(loginPassword);

		Administrators administrators = administractorService.getAdministractor(uid);
		if (administrators == null) {
			throw new AppException(UserCodeConst.NO_REGISTER);
		}
		String salt = MD5Util.genMD5Code(administrators.getCreateAdminId() + administrators.getMobile());
		return CryptoUtils.verify(administrators.getLoginPassword(), MD5Util.genMD5Code(loginPassword), salt);
	}

	@Override
	public void checkManagerGoogleCode(Integer adminId, String mobile, String code) {
		ManagerGoogleCodeConfig managerGoogleCodeConfig = managerGoogleCodeConfigService.selectManagerGoogleCodeConfigByAdminId(adminId, DelFlag.FALSE);
		if (null != managerGoogleCodeConfig) {
			if (Strings.isNullOrEmpty(code)) {
				throw new AppException(UserCodeConst.GOOGLE_CODE_ERROR);
			}
			GoogleAuthenticator gAuth = new GoogleAuthenticator();
			managerCredentialRepositoryMock.setFlag("DB");
			gAuth.setCredentialRepository(managerCredentialRepositoryMock);
			boolean isCodeValid = gAuth.authorizeUser(mobile, Integer.parseInt(code));
			if (!isCodeValid) {
				throw new AppException(UserCodeConst.GOOGLE_CODE_ERROR);
			}
		}
	}
}
