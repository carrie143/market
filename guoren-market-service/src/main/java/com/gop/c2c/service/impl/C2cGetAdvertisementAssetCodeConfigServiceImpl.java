package com.gop.c2c.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.gop.c2c.service.C2cGetAdvertisementAssetCodeConfigService;
import com.gop.domain.C2cAssetCodeConfig;

import com.gop.domain.enums.C2cTransType;
import com.gop.mapper.C2cAssetCodeConfigMapper;

/**
 * 
 * @author zhangliwei
 *
 */
@Service("C2cGetAdvertisementAssetCodeConfigService")
public class C2cGetAdvertisementAssetCodeConfigServiceImpl implements C2cGetAdvertisementAssetCodeConfigService {

	@Autowired
	private C2cAssetCodeConfigMapper c2cAssetCodeConfigMapper;
	
	@Override

	public List<String> getC2cAssetCodeConfig(C2cTransType type) {
		
		 List<C2cAssetCodeConfig>  configs = c2cAssetCodeConfigMapper.getStatusListedC2cAssetCodeConfigByType(type);
		
		 List<String> assetCodeConfig = Lists.newArrayList();
		 
         for ( C2cAssetCodeConfig  config:configs) {
        	 	
        	 	assetCodeConfig.add(config.getAssetCode());
         }
		
		return assetCodeConfig;
	}

	@Override
	public Boolean checkStatusListedAssetCodeByType(String assetCode, C2cTransType type) {

		C2cAssetCodeConfig config = c2cAssetCodeConfigMapper.getStatusListedC2cAssetCodeConfigByAssetCodeAndType(assetCode, type);
		
		if(config == null) {
			
			return false;
		}
		
		return true;
	}

}
