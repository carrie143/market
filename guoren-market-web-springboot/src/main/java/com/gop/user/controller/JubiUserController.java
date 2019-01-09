package com.gop.user.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.IdentifyingCodeConst;
import com.gop.code.consts.UserCodeConst;
import com.gop.domain.JubiUserInformation;
import com.gop.domain.User;
import com.gop.domain.enums.JubiRegisterFlag;
import com.gop.exception.AppException;
import com.gop.user.dto.JubiAssetDto;
import com.gop.user.dto.JubiAuthDto;
import com.gop.user.dto.JubiUserDto;
import com.gop.user.facade.UserFacade;
import com.gop.user.service.JubiUserInfoService;
import com.gop.user.service.UserService;
import com.gop.util.ConstantUtil;
import com.gop.util.EmailVerify;
import com.gop.util.PasswordUtil;
import com.gop.util.PhoneVerify;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

import lombok.extern.slf4j.Slf4j;


/**
 * 聚币用户配置
 */
@RestController("JubiUserController")	
@RequestMapping("/user")
@Slf4j
public class JubiUserController {
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	private JubiUserInfoService jubiUserInfoService;
	
	@Autowired
	private UserFacade userFacade;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	private long expireTime = 15;
	
	private final String randomKeyPrix = "RandomKey";
	
	//获取用户资产状况
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkServiceCodeStrategy'}})")})
	@RequestMapping(value = "/jubi-query", method = RequestMethod.POST)
	public JubiAssetDto getJubiUserAssetsInfo(@AuthForHeader AuthContext context) {
		String jubiAccount = context.getUserAccount();
		List<JubiUserInformation> jubiinfos = new ArrayList<JubiUserInformation>();
		if (EmailVerify.validEmailNumber(jubiAccount)) {
			jubiinfos = jubiUserInfoService.getJubiUserInfosByEmail(jubiAccount);
		}else if(PhoneVerify.validMobileNumber(jubiAccount)){
			jubiinfos = jubiUserInfoService.getJubiUserInfosByPhone(jubiAccount);
		}else {
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
		List<JubiAuthDto> jubiAuthDtos = new ArrayList<JubiAuthDto>();
		for(JubiUserInformation jubiinfo : jubiinfos) {
			if(JubiRegisterFlag.UNREGISTER.equals(jubiinfo.getRegisterFlag())) {
				JubiAuthDto jubiAuthDto = new JubiAuthDto();
				jubiAuthDto.setAssetCode(jubiinfo.getAssetCode());
				jubiAuthDto.setAssetCodeAmount(jubiinfo.getAmount().setScale(2, BigDecimal.ROUND_DOWN).toPlainString());
				jubiAuthDtos.add(jubiAuthDto);
			}
		}
		if(jubiAuthDtos.isEmpty()) {
			throw new AppException(UserCodeConst.JUBI_USER_HAS_REGISTER);
		}
		String randomKey=UUID.randomUUID().toString().trim().replaceAll("-", "");
	    redisTemplate.opsForValue().set(jubiAccount + ":" + randomKeyPrix, randomKey, expireTime, TimeUnit.MINUTES);
		JubiAssetDto jubiAssetDto = new JubiAssetDto();
		jubiAssetDto.setAssetList(jubiAuthDtos);
		jubiAssetDto.setRandomkey(randomKey);
	    return jubiAssetDto;
	}
	
	//聚币用户注册
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkServiceCodeStrategy'}})")})
	@RequestMapping(value = "/jubi-register", method = RequestMethod.POST)
	public void jubiUserRegister(@AuthForHeader AuthContext context,@RequestBody JubiUserDto jubiuserDto) {	
		String userAccount = context.getUserAccount();
		String jubiAccount = jubiuserDto.getJubiAccount();
		String redisRandomKey = redisTemplate.opsForValue().get(jubiAccount + ":" + randomKeyPrix);
		//校验及查询聚币用户信息
		List<JubiUserInformation> jubiinfos = new ArrayList<JubiUserInformation>();
		if (EmailVerify.validEmailNumber(jubiAccount)) {
			jubiinfos = jubiUserInfoService.getJubiUserInfosByEmail(jubiAccount);
		}else if(PhoneVerify.validMobileNumber(jubiAccount)){
			jubiinfos = jubiUserInfoService.getJubiUserInfosByPhone(jubiAccount);
		}else {
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
		//校验聚币用户是否所有资产均已注册
		boolean isAllRegister = true;
		List<JubiUserInformation> unRegJubiInfo= new ArrayList<JubiUserInformation>();
		for (JubiUserInformation jubiinfo : jubiinfos) {
			if(JubiRegisterFlag.UNREGISTER.equals(jubiinfo.getRegisterFlag())) {
				unRegJubiInfo.add(jubiinfo);
				isAllRegister=false;
			}
		}
		if (isAllRegister) {
			throw new AppException(UserCodeConst.JUBI_USER_HAS_REGISTER);
		}
				
		//校验用户邮箱格式
		if (!EmailVerify.validEmailNumber(userAccount)) {
			throw new AppException(UserCodeConst.EMAIL_FORMAT_ERROR);
		}
		
		//校验邮箱是否已注册
		Boolean isReg = userService.isMailRegister(userAccount);
		if (isReg) {
			throw new AppException(UserCodeConst.HAS_REGISTER);
		}
		
		//校验用户randomKey
		if (StringUtils.isEmpty(redisRandomKey)) {
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
		if (!jubiuserDto.getRandomkey().equals(redisRandomKey)) {
			throw new AppException(IdentifyingCodeConst.IDENTIFYING_CODE_EEROR);
		}
		
		//校验登录密码及支付密码
		String loginPassword = jubiuserDto.getLoginPassword();
		String payPassword = jubiuserDto.getPayPassword();
		loginPassword = ConstantUtil.charConvert(loginPassword);
		payPassword = ConstantUtil.charConvert(payPassword);

		if (loginPassword.length() < 6 || loginPassword.length() > 20) {
			throw new AppException(UserCodeConst.LOGIN_PASSWORD_VALID_ERROR);
		}

		if (!PasswordUtil.checkPasswordFormat(loginPassword)) {
			throw new AppException(UserCodeConst.LOGIN_PASSWORD_VALID_ERROR);
		}

		if (payPassword.length() < 8 || payPassword.length() > 20) {
			throw new AppException(UserCodeConst.PAY_PASSWORD_VALID_ERROR);
		}

		if (!PasswordUtil.checkPasswordFormat(payPassword)) {
			throw new AppException(UserCodeConst.PAY_PASSWORD_VALID_ERROR);
		}
		
		//开始注册聚币账户
		User user = userFacade.createUser(userAccount, null, loginPassword, payPassword, 0, "", 10003);
		
		//开始为聚币账户加各项资产
//		if (null != user) {
//			for(JubiUserInformation jubiinfo : unRegJubiInfo) {
//				jubiUserInfoService.assetOperationJubiUser(user.getUid(),jubiinfo.getAmount(),jubiinfo.getId(),jubiinfo.getAssetCode());
//			}
//		}
		return;
	}
}
