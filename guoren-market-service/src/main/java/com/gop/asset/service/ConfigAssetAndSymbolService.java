package com.gop.asset.service;

import java.util.List;

import com.gop.domain.ConfigAsset;
import com.gop.domain.ConfigSymbol;
import com.gop.domain.ConfigSymbolProfile;

public interface ConfigAssetAndSymbolService {

	public void addConfigAssetAndInitProfile(ConfigAsset configAsset);

	public void addConfigSymbolAndInitProfile(ConfigSymbol configSymbol);
	
	public void addConfigSymbolAndInitProfile(ConfigSymbol configSymbol,List<ConfigSymbolProfile> list);

	public List<ConfigSymbolProfile> initSymbolProfile(String symbol);

}
