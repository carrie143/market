package com.gop.code.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.RateLimiter;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.UserCodeConst;
import com.gop.code.dto.CodeDto;
import com.gop.common.CheckCodeService;
import com.gop.common.SmsMessageService;
import com.gop.conetxt.EnvironmentContxt;
import com.gop.domain.User;
import com.gop.exception.AppException;
import com.gop.sms.dto.VerifyCodeDto;
import com.gop.sms.service.MessageGenerator;
import com.gop.user.facade.UserFacade;
//import com.gop.sms.dto.VerifyCodeDto;
//import com.gop.sms.service.MessageGenerator;
import com.gop.util.EmailVerify;
import com.gop.util.PhoneVerify;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/service")
@Slf4j
public class ServiceCodeController {

	@Autowired
	private SmsMessageService smsMessageService;
	@Autowired
	@Qualifier("verifyCodeMessageGenerator")
	private MessageGenerator<VerifyCodeDto> verifyCodeMessageGenerator;
	@Autowired
	EnvironmentContxt environmentContxt;
	@Autowired
	UserFacade userFacade;

	@Autowired
	private CheckCodeService checkCodeService;

	private static final RateLimiter limiter = RateLimiter.create(10.0);// 每秒不超过10个任务被提交

	// 发送验证码
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkCaptcherCodeStrategy'},{'checkLoginStrategy'}})") })
	@RequestMapping(value = "/code", method = RequestMethod.POST)
	public void sendServiceCode(@AuthForHeader AuthContext authContext, @RequestBody CodeDto codeDto) {

		String code = "";

		if (codeDto == null) {
			log.error("CodeDto为null");
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}

		String sendAccount = codeDto.getSendAccount();

		if (StringUtils.isEmpty(sendAccount)) {
			log.info("codeDto中没有sendAccount");
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}

		User user = null;

		if (authContext.getLoginSession() != null) {// 已登录
			user = userFacade.getUser(authContext.getLoginSession().getUserId());
			if (PhoneVerify.validMobileNumber(sendAccount)) {
				if (!Strings.isNullOrEmpty(user.getMobile())) {
					if (!sendAccount.equals(user.getMobile())) {
						log.info("用户手机号与邦迪手机号不匹配");
						throw new AppException(CommonCodeConst.FIELD_ERROR);
					}
				}
			}
			code = checkCodeService.SaveUserSendCode(authContext.getLoginSession().getUserId(), sendAccount);
		} else {// 未登录
			code = checkCodeService.SaveUserSendCode(sendAccount);
		}
		// 去掉对邮箱是否注册的验证
		User userInfo = userFacade.getUser(sendAccount);
		if (userInfo == null) {
			throw new AppException(CommonCodeConst.USER_DONOT_EXIST);
		}

		log.info("sendCode email={},code={}", sendAccount, code);

		if (PhoneVerify.validMobileNumber(sendAccount)) {
			try {
				String message = String.format("Your Verification Code is : %s", code);
				smsMessageService.sendPhoneMessage(sendAccount, message);
			} catch (Exception e) {
				log.error("发送手机验证码异常:phone:{}", e);

				Throwables.propagateIfInstanceOf(e, AppException.class);
				throw new AppException(CommonCodeConst.SERVICE_ERROR);
			}
		} else {

			if (!EmailVerify.validEmailNumber(sendAccount)) {
				throw new AppException(UserCodeConst.EMAIL_FORMAT_ERROR);
			}
			try {
				VerifyCodeDto verifyCodeDto = new VerifyCodeDto();
				verifyCodeDto.setEmailText(code);
				verifyCodeDto.setTemplateName("sms.ftl");
				String message = verifyCodeMessageGenerator.generatorMessage(environmentContxt.getSystemEnvironMent(),
						verifyCodeDto);

				// 从RateLimiter获取一个许可，该方法会被阻塞直到获取到请求。
				limiter.acquire();
				smsMessageService.sendEmailMessage(sendAccount, message);

			} catch (Exception e) {
				log.error("发送邮箱验证码异常:email:{}", e);
				Throwables.propagateIfInstanceOf(e, AppException.class);
				throw new AppException(CommonCodeConst.SERVICE_ERROR);

			}

		}

	}

	// 绑定邮箱时发送验证码
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkCaptcherCodeStrategy'},{'checkLoginStrategy'}})") })
	@RequestMapping(value = "emailCode", method = RequestMethod.POST)
	public void sendEmailCode(@AuthForHeader AuthContext authContext, @RequestBody CodeDto codeDto) {

		String code = "";

		if (codeDto == null) {
			log.error("CodeDto为null");
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}

		String sendAccount = codeDto.getSendAccount();

		if (StringUtils.isEmpty(sendAccount)) {
			log.info("codeDto中没有sendAccount");
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}

		User user = null;

		if (authContext.getLoginSession() != null) {// 已登录
			user = userFacade.getUser(authContext.getLoginSession().getUserId());
			if (PhoneVerify.validMobileNumber(sendAccount)) {
				if (!Strings.isNullOrEmpty(user.getMobile())) {
					if (!sendAccount.equals(user.getMobile())) {
						log.info("用户手机号与邦迪手机号不匹配");
						throw new AppException(CommonCodeConst.FIELD_ERROR);
					}
				}
			}
			code = checkCodeService.SaveUserSendCode(authContext.getLoginSession().getUserId(), sendAccount);
		} else {// 未登录
			code = checkCodeService.SaveUserSendCode(sendAccount);
		}
		// 去掉对邮箱是否注册的验证
//		User userInfo = userFacade.getUser(sendAccount);
//		if (userInfo == null) {
//			throw new AppException(CommonCodeConst.USER_DONOT_EXIST);
//		}
		log.info("sendCode email={},code={}", sendAccount, code);

		if (PhoneVerify.validMobileNumber(sendAccount)) {
			try {
				String message = String.format("Your Verification Code is : %s", code);
				smsMessageService.sendPhoneMessage(sendAccount, message);
			} catch (Exception e) {
				log.error("发送手机验证码异常:phone:{}", e);

				Throwables.propagateIfInstanceOf(e, AppException.class);
				throw new AppException(CommonCodeConst.SERVICE_ERROR);
			}
		} else {

			if (!EmailVerify.validEmailNumber(sendAccount)) {
				throw new AppException(UserCodeConst.EMAIL_FORMAT_ERROR);
			}
			try {
				VerifyCodeDto verifyCodeDto = new VerifyCodeDto();
				verifyCodeDto.setEmailText(code);
				verifyCodeDto.setTemplateName("sms.ftl");
				String message = verifyCodeMessageGenerator.generatorMessage(environmentContxt.getSystemEnvironMent(),
						verifyCodeDto);

				// 从RateLimiter获取一个许可，该方法会被阻塞直到获取到请求。
				limiter.acquire();
				smsMessageService.sendEmailMessage(sendAccount, message);

			} catch (Exception e) {
				log.error("发送邮箱验证码异常:email:{}", e);
				Throwables.propagateIfInstanceOf(e, AppException.class);
				throw new AppException(CommonCodeConst.SERVICE_ERROR);

			}

		}

	}

}