package com.gop.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.gop.code.consts.SecurityCodeConst;
import com.gop.common.Environment;
import com.gop.exception.AppException;
import com.gop.user.dto.CheckLoginLockedDto;
import com.gop.user.facade.UserFacade;
import com.gop.util.ConstantUtil;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.strategy.AuthStrategy;

import lombok.extern.slf4j.Slf4j;

/**
 * 校验登录密码正确与否
 * 
 * @author liuze
 *
 */
@Service("checkLoginPasswordStrategy")
@Slf4j
public class CheckLoginPasswordStrategy implements AuthStrategy {
	@Autowired
	private Environment environment;

	@Autowired
	private UserFacade userFacade;

	@Override
	public void pre(AuthContext authContext) {
		int uid = authContext.getLoginSession().getUserId();
		String loginPassword = authContext.getLoginPassword();
		loginPassword = ConstantUtil.charConvert(loginPassword);

		CheckLoginLockedDto checkLoginLockedDto = userFacade
				.LoginPasswordLockNum(authContext.getLoginSession().getUserId());
		if (checkLoginLockedDto.getLockedNum() >= 10) {
			authContext.setLoginSession(null);
			throw new AppException(SecurityCodeConst.LOGIN_ACCOUNT_LOCK);
		}

		userFacade.checkLoginPassword(uid, loginPassword);

	}

	@Override
	public boolean match(AuthContext authContext) {
		if (authContext == null) {
			return false;
		}
		if (authContext.getLoginSession() == null) {
			return false;
		}
		if (Strings.isNullOrEmpty(authContext.getLoginPassword())) {
			log.info("login-password为null");
			return false;
		}
		return true;
	}

	@Override
	public void after(AuthContext authContext, Throwable throwable) {

	}

}
