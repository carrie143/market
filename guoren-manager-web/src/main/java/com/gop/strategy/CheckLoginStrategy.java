package com.gop.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.gop.code.consts.SecurityCodeConst;
import com.gop.code.consts.UserCodeConst;
import com.gop.domain.Administrators;
import com.gop.domain.enums.AdministratorsRole;
import com.gop.domain.enums.LockStatus;
import com.gop.exception.AppException;
import com.gop.user.service.AdministractorService;
import com.gop.util.TokenHelper;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.strategy.AuthStrategy;
import com.gop.web.base.model.LoginSession;

import lombok.extern.slf4j.Slf4j;

@Service("checkLoginStrategy")
@Slf4j
public class CheckLoginStrategy implements AuthStrategy {
	@Autowired
	private TokenHelper tokenHelper;
	@Autowired
	private AdministractorService administractorService;
	@Override
	public void pre(AuthContext authContext) {

		String token = authContext.getToke();
		if (null == token) {
			throw new AppException(SecurityCodeConst.NO_LOGIN);
		}
		LoginSession loginsession = null;
		int uid = 0;
		try {
			uid = tokenHelper.validToken(token);
		} catch (Exception e) {
			throw new AppException(SecurityCodeConst.TOKE_HAS_INVALID);
		}
		Administrators administrators = administractorService.getAdministractor(uid);
		if (null == administrators) {
			throw new AppException(UserCodeConst.NO_REGISTER);
		}
		
		if (LockStatus.LOCK.equals(administrators.getLocked())) {
			throw new AppException(SecurityCodeConst.NO_PERMISSION);
		}
		loginsession = new LoginSession();
		loginsession.setUserId(uid);
		authContext.setLoginSession(loginsession);
	}

	@Override
	public boolean match(AuthContext authContext) {
		if (authContext == null) {
			return false;
		}
		String token = authContext.getToke();
		if (Strings.isNullOrEmpty(token)) {
			return false;
		}
		return true;
	}

	@Override
	public void after(AuthContext authContext, Throwable throwable) {
		
	}

}
