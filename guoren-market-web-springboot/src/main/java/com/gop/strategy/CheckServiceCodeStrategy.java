package com.gop.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.gop.code.consts.IdentifyingCodeConst;
import com.gop.common.CheckCodeService;
import com.gop.common.IdentifyingCodeService;
import com.gop.domain.User;
import com.gop.exception.AppException;
import com.gop.user.facade.UserFacade;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.strategy.AuthStrategy;

import lombok.extern.slf4j.Slf4j;

/**
 * 校验servicecode（手机验证码或者境外邮箱验证码）
 * 
 * @author liuze
 *
 */
@Service("checkServiceCodeStrategy")
@Slf4j
public class CheckServiceCodeStrategy implements AuthStrategy {

	@Autowired
	private UserFacade userfacade;
	@Autowired
	private CheckCodeService checkCodeService;

	@Override
	public void pre(AuthContext authContext) {
		boolean checkFlag = false;
		if (authContext.getLoginSession() != null) {
			checkFlag = checkCodeService.checkUserCode(authContext.getLoginSession().getUserId(),
					authContext.getServiceCode());
			if (!checkFlag) {
				throw new AppException(IdentifyingCodeConst.IDENTIFYING_CODE_EEROR);
			}

			authContext.setUserAccount(checkCodeService.getUserAccount(authContext.getLoginSession().getUserId()));
		} else {
			checkFlag = checkCodeService.checkUserCode(authContext.getUserAccount(), authContext.getServiceCode());
			if (!checkFlag) {
				throw new AppException(IdentifyingCodeConst.IDENTIFYING_CODE_EEROR);
			}

		}

		
	}

	@Override
	public boolean match(AuthContext authContext) {
		if (authContext == null) {

			return false;
		}
		if (Strings.isNullOrEmpty(authContext.getServiceCode())) {

			return false;
		}
		if (Strings.isNullOrEmpty(authContext.getUserAccount()) && (null == authContext.getLoginSession())) {
			return false;
		}

		return true;
	}

	@Override
	public void after(AuthContext authContext, Throwable throwable) {

		if (throwable == null) {
			if (authContext.getLoginSession() != null) {

				checkCodeService.delete(authContext.getLoginSession().getUserId());
			} else {
				checkCodeService.delete(authContext.getUserAccount());
			}

		}
	}

}
