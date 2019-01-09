package com.gop.user.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.UserCodeConst;
import com.gop.config.GoogleAuthConstants;
import com.gop.domain.UserGoogleCodeConfig;
import com.gop.domain.enums.UserGoogleCodeFlagType;
import com.gop.exception.AppException;
import com.gop.user.facade.UserFacade;
import com.gop.user.service.UserGoogleCodeConfigService;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;


import lombok.extern.slf4j.Slf4j;

/**
 * 用户谷歌验证码配置
 */
@RestController("UserGoogleAuthInfoController")	
@RequestMapping("/user")
@Slf4j
public class UserGoogleAuthInfoController {
	@Autowired
	private UserGoogleCodeConfigService userGoogleCodeConfigService;
	@Autowired
	private UserFacade userFacade;
		  
	@Autowired
	private StringRedisTemplate redisTemplate; 
	
	private String prPrix = GoogleAuthConstants.PRIX;
	
	@Value("${googleAuth.urlDomain}")
	private String urlDomain;
	
	//用户非首次开启谷歌验证码
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})")})
	@RequestMapping(value = "/googlecode-on", method = RequestMethod.GET)
	public void updateGoogleCodeConfigOnByUid(@AuthForHeader AuthContext context) {
		Integer uid = context.getLoginSession().getUserId();
		userGoogleCodeConfigService.updateGoogleCodeConfigByUid(uid, UserGoogleCodeFlagType.ON);
	}
	
	//用户关闭谷歌验证码
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkServiceCodeStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkGoogleCodeStrategy'}})")})
	@RequestMapping(value = "/googlecode-off", method = RequestMethod.GET)
	public void updateGoogleCodeConfigOffByUid(@AuthForHeader AuthContext context) {
		Integer uid = context.getLoginSession().getUserId();
		userGoogleCodeConfigService.updateGoogleCodeConfigByUid(uid, UserGoogleCodeFlagType.OFF);
	}
	
	//查询用户谷歌验证码开关状态及是否绑定过谷歌验证码
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})")})
	@RequestMapping(value = "/googlecode-query", method = RequestMethod.GET)
	public Map<String,Object> selectGoogleCodeConfigStatusByUid(@AuthForHeader AuthContext context) {
		Integer uid = context.getLoginSession().getUserId();
		UserGoogleCodeConfig userGoogleCodeConfig=userGoogleCodeConfigService.selectUserGoogleCodeConfigByUid(uid);
		ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>(); 
		if (null == userGoogleCodeConfig) {
			map.put("status", UserGoogleCodeFlagType.OFF);
		}else {
			map.put("status", userGoogleCodeConfig.getFlag());
		}
		boolean isBound = userGoogleCodeConfigService.countGoogleCodeConfigByUid(uid);
		map.put("isBound", isBound);
		return map;
	}
	
	//获取谷歌验证二维码及相关信息
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})")})
	@RequestMapping(value = "/googlecode-get", method = RequestMethod.GET)
	public Map<String, String> getGoogleCode(@AuthForHeader AuthContext context) {
		String email = userFacade.getUser(context.getLoginSession().getUserId()).getEmail();
		String secretCode = userGoogleCodeConfigService.generateGoogleKeyByEmail(email);
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
		StringBuffer stringBuffer = new StringBuffer("otpauth://totp/");
		stringBuffer.append(urlDomain).append(":").append(email)
		.append("?secret=").append(secretCode).append("&issuer=").append(urlDomain);
		map.put("googleAuthenticator", stringBuffer.toString());
		map.put("secretcode", secretCode);
		return map;
    }
	
	//首次开启校验谷歌验证码并保存信息
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkLoginPasswordStrategy'}})") })
	@RequestMapping(value = "/googlecode-firstcheck", method = RequestMethod.GET)
	public void checkGoogleCodeFirst(@AuthForHeader AuthContext context) {
		Integer uid = context.getLoginSession().getUserId();
		String email = userFacade.getUser(uid).getEmail();
		String googlecode = context.getGoogleCode();
		//校验谷歌验证码
		String secret = redisTemplate.opsForValue().get(prPrix + email);
		if (StringUtils.isEmpty(secret)) {
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
		boolean isCodeValid = userGoogleCodeConfigService.checkGoogleCodeCorrect(email, googlecode);
		if (isCodeValid) {
			userGoogleCodeConfigService.addUserGoogleCodeConfig(uid, UserGoogleCodeFlagType.ON,secret);
		}else {
			throw new AppException(UserCodeConst.GOOGLE_CODE_ERROR);
		}
	}
	
	//重置时校验谷歌验证码并保存信息
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkServiceCodeStrategy'}})")})
	@RequestMapping(value = "/googlecode-resetcheck", method = RequestMethod.GET)
	public void checkGoogleCodeReset(@AuthForHeader AuthContext context) {
		Integer uid = context.getLoginSession().getUserId();
		String email = userFacade.getUser(uid).getEmail();
		String googlecode = context.getGoogleCode();
		//校验谷歌验证码
		String secret = redisTemplate.opsForValue().get(prPrix + email);
		if (StringUtils.isEmpty(secret)) {
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
		boolean isCodeValid = userGoogleCodeConfigService.checkGoogleCodeCorrect(email, googlecode);
		if (isCodeValid) {
			userGoogleCodeConfigService.resetUserGoogleCodeConfigByUid(uid, UserGoogleCodeFlagType.ON,secret);
		}else {
			throw new AppException(UserCodeConst.GOOGLE_CODE_ERROR);
		}
	}
}
