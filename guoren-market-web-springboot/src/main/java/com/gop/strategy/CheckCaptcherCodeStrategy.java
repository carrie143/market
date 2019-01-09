package com.gop.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.gop.code.consts.IdentifyingCodeConst;
import com.gop.common.IdentifyingCodeService;
import com.gop.exception.AppException;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.strategy.AuthStrategy;

import lombok.extern.slf4j.Slf4j;

/**
 * 校验图形验证码策略方法
 * 
 * @author liuze
 *
 */
@Service("checkCaptcherCodeStrategy")
@Slf4j
public class CheckCaptcherCodeStrategy implements AuthStrategy {

	@Autowired
	private IdentifyingCodeService identifyingCodeServiceImpl;

	@Override
	public void pre(AuthContext authContext) {
		if (authContext.getLoginSession() != null) {
			// 登录后不要图形验证码
		} else {
			String code = authContext.getCaptcharCode();
			String captcharNo = authContext.getcaptcharNo();

			String captharCode = identifyingCodeServiceImpl.getCode(captcharNo);

			boolean isValid = code.equalsIgnoreCase(captharCode);
			log.info("redis中获取到的验证码是:{}，用户输入的验证码是:{}", captharCode, code);
			identifyingCodeServiceImpl.delCode(authContext.getcaptcharNo());

			if (!isValid) {
				throw new AppException(IdentifyingCodeConst.GRAPHICAL_IDENTIFYING_CODE_EEROR);
			}

		}

	}

	@Override
	public boolean match(AuthContext authContext) {

		if (authContext.getLoginSession() == null) {
			if (Strings.isNullOrEmpty(authContext.getcaptcharNo())) {
				log.info("captchar-no为null");
				return false;
			} else {
				return true;
			}

		} else {
			return false;
		}

	}

	@Override
	public void after(AuthContext authContext, Throwable throwable) {
		// if (null != throwable) {
		// identifyingCodeServiceImpl.delCode(authContext.getcaptcharNo());
		// }

	}

}
