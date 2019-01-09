package com.gop.match.service;

import java.math.BigDecimal;
import java.util.List;

import com.gop.domain.ConfigSymbolProfile;
import com.gop.domain.enums.ConfigSymbolType;

public interface ConfigSymbolProfileService {

	public void createOrUpdateConfigSymbolProfile(ConfigSymbolProfile configSymbolProfile);

	public List<ConfigSymbolProfile> getConfigSymbolProfileByKey(ConfigSymbolType key);
	
	public List<ConfigSymbolProfile> selectAll();

	public BigDecimal getBigDecimalValue(String symbol, ConfigSymbolType key);

	public String getStringValue(String symbol, ConfigSymbolType key);

}
