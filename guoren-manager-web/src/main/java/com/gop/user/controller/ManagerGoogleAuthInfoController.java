package com.gop.user.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.GoogleCodeConst;
import com.gop.code.consts.UserCodeConst;
import com.gop.domain.ManagerGoogleCodeConfig;
import com.gop.domain.enums.DelFlag;
import com.gop.exception.AppException;
import com.gop.user.service.AdministractorService;
import com.gop.user.service.ManagerGoogleCodeConfigService;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;


import lombok.extern.slf4j.Slf4j;

/**
 * 用户谷歌验证码配置
 */
@RestController("ManagerGoogleAuthInfoController")	
@RequestMapping("/manager")
@Slf4j
public class ManagerGoogleAuthInfoController {
	@Autowired
	private ManagerGoogleCodeConfigService managerGoogleCodeConfigService;

	@Autowired
	private StringRedisTemplate redisTemplate; 
	
	private String prPrix = "ManagerGoogleAuthenticator:";
	
	@Autowired
	private AdministractorService administractorService;
	
	@Value("${googleAuth.urlDomain}")
	private String urlDomain;
	
	//获取谷歌验证二维码及相关信息
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
		@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})")})
	@RequestMapping(value = "/googlecode-get", method = RequestMethod.GET)
	public Map<String, String> getGoogleCode(@AuthForHeader AuthContext context) {
		int adminId = context.getLoginSession().getUserId();
		String mobile = administractorService.getAdministractor(adminId).getMobile();
		String secretCode = managerGoogleCodeConfigService.generateGoogleKeyByMobile(mobile);
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
		StringBuffer stringBuffer = new StringBuffer("otpauth://totp/");
		stringBuffer.append(urlDomain).append(":").append(mobile)
		.append("?secret=").append(secretCode).append("&issuer=").append(urlDomain);
		map.put("googleAuthenticator", stringBuffer.toString());
		map.put("secretcode", secretCode);
		return map;
    }
	
	//管理员开启谷歌验证码并保存信息
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
		@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})")})
	@RequestMapping(value = "/googlecode-firstcheck", method = RequestMethod.GET)
	public void checkGoogleCodeFirst(@AuthForHeader AuthContext context) {
		Integer adminId = context.getLoginSession().getUserId();
		String mobile = administractorService.getAdministractor(adminId).getMobile();
		String googlecode = context.getGoogleCode();
		//校验谷歌验证码
		String secret = redisTemplate.opsForValue().get(prPrix + mobile);
		if (StringUtils.isEmpty(secret)) {
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
		boolean isCodeValid = managerGoogleCodeConfigService.checkGoogleCodeCorrect(mobile, googlecode);
		if (isCodeValid) {
			managerGoogleCodeConfigService.addManagerGoogleCodeConfig(adminId, secret, DelFlag.FALSE);
		}else {
			throw new AppException(UserCodeConst.GOOGLE_CODE_ERROR);
		}
	}
	
	//超管重置谷歌验证码
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkLoginPasswordStrategy'}})")})
	@RequestMapping(value = "/googlecode-reset", method = RequestMethod.GET)
	public void googleCodeReset(@AuthForHeader AuthContext context,@RequestParam("adminId") Integer adminId) {
		ManagerGoogleCodeConfig managerGoogleCodeConfig = managerGoogleCodeConfigService.selectManagerGoogleCodeConfigByAdminId(adminId, DelFlag.FALSE);
		if (null == managerGoogleCodeConfig) {
			throw new AppException(GoogleCodeConst.MANAGER_CAN_NOT_RESET_GOOGLE_CODE);
		}
		managerGoogleCodeConfigService.updateManagerGoogleCodeConfigByAdminIdAndDelFlag(adminId, DelFlag.FALSE, DelFlag.TRUE);
	}
}
