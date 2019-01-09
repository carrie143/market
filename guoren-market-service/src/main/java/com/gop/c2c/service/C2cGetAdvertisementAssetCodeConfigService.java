package com.gop.c2c.service;

import java.util.List;


import com.gop.domain.enums.C2cTransType;


/**
 * 
 * @author zhangliwei
 *
 */
public interface C2cGetAdvertisementAssetCodeConfigService {

	public List<String> getC2cAssetCodeConfig(C2cTransType type);
	
	public Boolean checkStatusListedAssetCodeByType(String assetCode,C2cTransType type);
	
}
