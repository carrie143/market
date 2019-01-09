package com.gop.c2c.service;

import java.util.List;


import com.gop.domain.enums.C2cTransType;

/**
 * 
 * @author zhangliwei
 *
 */

public interface C2cGetAdvertisementCountryConfigService {

	public List<String> getC2cCountryConfig(C2cTransType type);
	
	public Boolean checkStatusListedCountryByType(String country,C2cTransType type);
	
	
}
