package com.gop.user.service.impl;

import com.gop.config.GoogleAuthConstants;
import com.gop.domain.User;
import com.gop.domain.UserGoogleCodeConfig;
import com.gop.user.service.UserGoogleCodeConfigService;
import com.gop.user.service.UserService;
import com.warrenstrange.googleauth.ICredentialRepository;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CredentialRepositoryMock implements ICredentialRepository {

  @Autowired
  private StringRedisTemplate redisTemplate;

  private String prix = GoogleAuthConstants.PRIX;

  private long expireTime = 30;

  @Autowired
  UserService userService;
  
  @Autowired
  UserGoogleCodeConfigService userGoogleCodeConfigService;
  
  @Setter
  private String flag;


  @Override
  public String getSecretKey(String userName) {
	String secretCode = "";
	if("DB".equals(flag)) {
		User user = userService.getUserByEmail(userName);
		UserGoogleCodeConfig userGoogleCodeConfig = userGoogleCodeConfigService.
				selectUserGoogleCodeConfigByUid(user.getUid());
		if (!StringUtils.isEmpty(userGoogleCodeConfig.getSecretCode())) {
			secretCode = userGoogleCodeConfig.getSecretCode();
		}
	}else {
		secretCode = redisTemplate.opsForValue().get(prix + userName);
	}  
	return secretCode;
  }

  @Override
  public void saveUserCredentials(String userName, String secretKey, int validationCode, List<Integer> scratchCodes) {
    redisTemplate.opsForValue().set(prix + userName, secretKey, expireTime, TimeUnit.MINUTES);
  }
}
