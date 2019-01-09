package com.gop.user.service;

import com.gop.domain.ManagerGoogleCodeConfig;
import com.gop.domain.enums.DelFlag;

public interface ManagerGoogleCodeConfigService {
	
	boolean countManagerGoogleCodeConfigByAdminId(Integer adminId);
	
	void addManagerGoogleCodeConfig(Integer adminId,String secretCode,DelFlag delFlag);
	
    boolean checkGoogleCodeCorrect(String mobile,String googlecode);
	
	String generateGoogleKeyByMobile(String mobile);
	
	ManagerGoogleCodeConfig selectManagerGoogleCodeConfigByAdminId(Integer adminId,DelFlag delFlag);
	
	void updateManagerGoogleCodeConfigByAdminIdAndDelFlag(Integer adminId,DelFlag beginFlag,DelFlag endFlag);

}
