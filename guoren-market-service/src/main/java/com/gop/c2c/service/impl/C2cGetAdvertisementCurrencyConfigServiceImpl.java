package com.gop.c2c.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.gop.c2c.service.C2cGetAdvertisementCurrencyConfigService;
import com.gop.domain.C2cCurrencyConfig;
import com.gop.domain.enums.C2cTransType;
import com.gop.mapper.C2cCurrencyConfigMapper;

/**
 * 
 * @author zhangliwei
 *
 */
@Service("C2cGetAdvertisementCurrencyConfigService")


public class C2cGetAdvertisementCurrencyConfigServiceImpl implements C2cGetAdvertisementCurrencyConfigService {

	@Autowired
	private C2cCurrencyConfigMapper c2cCurrencyConfigMapper;
	
	
	@Override
	public List<String> getC2cCurrencyConfig(C2cTransType type) {
		
		List<C2cCurrencyConfig> configs = c2cCurrencyConfigMapper.getStatusListedC2cCurrencyConfigByType(type);
		
		List<String> currencyConfig = Lists.newArrayList();
		
		for (C2cCurrencyConfig config : configs) {
			
			currencyConfig.add(config.getAssetCode());
		}
		return currencyConfig;
	}

	@Override
	public Boolean checkStatusListedCurrencyByType(String assetCode, C2cTransType type) {
		
		C2cCurrencyConfig config = c2cCurrencyConfigMapper.getStatusListedC2cCurrencyConfigByAssetCodeAndType(assetCode, type);

				if(config == null) {
					
					return false;
				}
				return true;
	}
}
