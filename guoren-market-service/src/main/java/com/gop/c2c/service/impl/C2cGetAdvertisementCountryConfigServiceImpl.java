package com.gop.c2c.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.gop.c2c.service.C2cGetAdvertisementCountryConfigService;
import com.gop.domain.C2cCountryConfig;
import com.gop.domain.enums.C2cTransType;
import com.gop.mapper.C2cCountryConfigMapper;

@Service("C2cGetAdvertisementCountryConfigService")

/**
 * 
 * @author zhangliwei
 *
 */
public class C2cGetAdvertisementCountryConfigServiceImpl implements C2cGetAdvertisementCountryConfigService {

	@Autowired
	private C2cCountryConfigMapper c2cCountryConfigMapper;
	
	@Override
	public List<String> getC2cCountryConfig(C2cTransType type) {
		
		List<C2cCountryConfig> configs = c2cCountryConfigMapper.getStatusListedC2cCountryConfigByType(type);
		
		List<String> countryConfig = Lists.newArrayList();
		
		for (C2cCountryConfig config : configs) {
			
			countryConfig.add(config.getCountry());
		}

		return countryConfig;
	}

	@Override
	public Boolean checkStatusListedCountryByType(String country, C2cTransType type) {
		
		C2cCountryConfig config = c2cCountryConfigMapper.getStatusListedC2cCountryConfigByAssetCodeAndType(country, type);
		
		if(config == null) {
			
			return false;
		}
		
		return true;
	}

}
