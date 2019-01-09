package com.gop.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.gop.common.Environment;
import com.gop.user.facade.UserFacade;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.strategy.AuthStrategy;

import lombok.extern.slf4j.Slf4j;

@Service("checkPayPasswordStregy")
@Slf4j
public class CheckPayPasswordStrategy implements AuthStrategy {

	@Autowired
	Environment environment;

	@Autowired
	private UserFacade userFacade;

	@Override
	public void pre(AuthContext authContext) {

		Integer userId = authContext.getLoginSession().getUserId();
       
		String payPassword = authContext.getpayPassword();
		 
		userFacade.checkPayPassword(userId, payPassword);
	}

	@Override
	public boolean match(AuthContext authContext) {
		if (authContext == null) {
			log.info("authContext为null");
			return false;
		}
		if (authContext.getLoginSession() == null) {
			return false;
		}
		if (Strings.isNullOrEmpty(authContext.getpayPassword())) {
			log.info("pay-password为null");
			return false;
		}
		return true;
	}

	@Override
	public void after(AuthContext authContext, Throwable throwable) {
		// TODO Auto-generated method stub

	}

}
