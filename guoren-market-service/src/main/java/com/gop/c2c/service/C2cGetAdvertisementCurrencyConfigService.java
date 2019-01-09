package com.gop.c2c.service;

import java.util.List;

import com.gop.domain.enums.C2cTransType;

/**
 * 
 * @author zhangliwei
 *
 */

public interface C2cGetAdvertisementCurrencyConfigService {

	public List<String> getC2cCurrencyConfig(C2cTransType type);

	public Boolean checkStatusListedCurrencyByType(String assetCode,C2cTransType type);
}
