package com.gop.user.service.impl;

import com.gop.domain.ManagerGoogleCodeConfig;
import com.gop.domain.enums.DelFlag;
import com.gop.user.service.AdministractorService;
import com.gop.user.service.ManagerGoogleCodeConfigService;
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
public class ManagerCredentialRepositoryMock implements ICredentialRepository {

  @Autowired
  private StringRedisTemplate redisTemplate;

  private String prix = "ManagerGoogleAuthenticator:";

  private long expireTime = 30;

  @Autowired
  private AdministractorService administractorService;
  
  @Autowired
  private ManagerGoogleCodeConfigService managerGoogleCodeConfigService;
  
  @Setter
  private String flag;


  @Override
  public String getSecretKey(String userName) {
	String secretCode = "";
	if("DB".equals(flag)) {
		Integer adminId = administractorService.getAdministractor(userName).getAdminId();
		ManagerGoogleCodeConfig managerGoogleCodeConfig = managerGoogleCodeConfigService.
				selectManagerGoogleCodeConfigByAdminId(adminId,DelFlag.FALSE);
		if (!StringUtils.isEmpty(managerGoogleCodeConfig.getSecretCode())) {
			secretCode = managerGoogleCodeConfig.getSecretCode();
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
