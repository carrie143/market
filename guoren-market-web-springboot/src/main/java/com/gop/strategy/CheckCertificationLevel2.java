package com.gop.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.code.consts.UserCodeConst;
import com.gop.domain.User;
import com.gop.domain.enums.AuthLevel;
import com.gop.exception.AppException;
import com.gop.user.facade.UserFacade;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.strategy.AuthStrategy;

@Service("checkCertificationLevel2")
public class CheckCertificationLevel2 implements AuthStrategy {
	@Autowired
	UserFacade userFacade;

	@Override
	public void pre(AuthContext authContext) {

		User user = userFacade.getUser(authContext.getLoginSession().getUserId());

		Boolean result = user.getAuthLevel().isBigerOrEqual(AuthLevel.LEVEL2);

		if (!result) {
			throw new AppException(UserCodeConst.NO_CERTIFICATION_LEVEL2);
		}
	}

	@Override
	public void after(AuthContext authContext, Throwable throwable) {

	}

	@Override
	public boolean match(AuthContext authContext) {
		if (authContext.getUserAccount() == null) {
			return false;
		}
		if (authContext.getLoginSession() == null) {
			return false;
		}
		return true;
	}
}
