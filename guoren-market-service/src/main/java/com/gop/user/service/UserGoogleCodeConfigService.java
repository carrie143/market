package com.gop.user.service;

import com.gop.domain.UserGoogleCodeConfig;
import com.gop.domain.enums.UserGoogleCodeFlagType;

public interface UserGoogleCodeConfigService {
	
	public void updateGoogleCodeConfigByUid(Integer uid,UserGoogleCodeFlagType flag);
	
	public UserGoogleCodeConfig selectUserGoogleCodeConfigByUid(Integer uid);
	
	public void updateGoogleCodeSecretByUid(Integer uid,String secretCode);
	
	public boolean countGoogleCodeConfigByUid(Integer uid);
	
	public void addUserGoogleCodeConfig(Integer uid, UserGoogleCodeFlagType flag,String secretCode);
		
	public boolean checkGoogleCodeCorrect(String email,String googlecode);
	
	public String generateGoogleKeyByEmail(String email);
	
	public void resetUserGoogleCodeConfigByUid(Integer uid, UserGoogleCodeFlagType flag,String secretCode);


}
