package com.gop.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.gop.code.consts.SecurityCodeConst;
import com.gop.code.consts.UserCodeConst;
import com.gop.domain.User;
import com.gop.exception.AppException;
import com.gop.user.dto.CheckLoginLockedDto;
import com.gop.user.facade.UserFacade;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.strategy.AuthStrategy;

import lombok.extern.slf4j.Slf4j;

/**
 * 校验登录锁定情况，进行相应处理；并且当满足需要图形验证时校验图形验证码
 * 
 * @author liuze
 *
 */
@Service("checkLoginPasswordWithLoginStrategy")
@Slf4j
public class CheckLoginPasswordWithLoginStrategy implements AuthStrategy {

	@Autowired
	private CheckCaptcherCodeStrategy checkCaptcherCodeStrategy;

	@Autowired
	private UserFacade userFacade;

	@Override
	public void pre(AuthContext authContext) {
		User user = userFacade.getUser(authContext.getUserAccount());
		if (null == user) {
			throw new AppException(UserCodeConst.NO_REGISTER);
		}
		CheckLoginLockedDto checkLoginLockedDto = userFacade.LoginPasswordLockNum(user.getUid());
		Integer lockedNum = checkLoginLockedDto.getLockedNum();
		// 根据用户表锁定次数判断
		log.info("lockedNum========" + lockedNum);

		// 等于10次 抛出异常
		if (lockedNum >= 10) {
			authContext.setLoginSession(null);
			throw new AppException(SecurityCodeConst.LOGIN_ACCOUNT_LOCK);
		}

		// 3次到10次之间 调用图形验证码策略
		if (lockedNum >= 3 && lockedNum < 10) {
			boolean flag = checkCaptcherCodeStrategy.match(authContext);
			if (!flag) {
				log.error("大于等于3次输入错误，需要图形验证码");
				throw new AppException(SecurityCodeConst.NEED_VERIFICATION_CODE);
			}
			checkCaptcherCodeStrategy.pre(authContext);
		}

		userFacade.checkLoginPassword(user.getUid(), authContext.getLoginPassword());

	}

	@Override
	public boolean match(AuthContext authContext) {
		if (authContext == null) {
			return false;
		}
		if (Strings.isNullOrEmpty(authContext.getUserAccount())) {

			return false;
		}
	
		if (Strings.isNullOrEmpty(authContext.getLoginPassword())) {

			return false;
		}
		return true;
	}

	@Override
	public void after(AuthContext authContext, Throwable throwable) {
		// TODO Auto-generated method stub

	}

}
