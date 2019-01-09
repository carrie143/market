package com.gop.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.gop.authentication.facade.AuthenticationFacade;
import com.gop.common.Environment;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.strategy.AuthStrategy;

@Service("checkPassportStraegy")
public class CheckPassportStrategy implements AuthStrategy {
	@Autowired
	Environment environment;
	@Autowired
	AuthenticationFacade authenticationFacade;

	@Override
	public void pre(AuthContext authContext) {

		String passort = authContext.getpassort();
		authenticationFacade.checkPassport(authContext.getLoginSession().getUserId(), passort);
	}

	@Override
	public boolean match(AuthContext authContext) {
		if (authContext.getLoginSession() == null) {
			return false;
		}
		if (Strings.isNullOrEmpty(authContext.getpassort())) {
			return false;
		}
		return true;
	}

	@Override
	public void after(AuthContext authContext, Throwable throwable) {
		// TODO Auto-generated method stub

	}

}
