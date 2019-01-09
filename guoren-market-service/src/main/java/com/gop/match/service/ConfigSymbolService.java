package com.gop.match.service;

import java.util.List;

import com.gop.domain.ConfigSymbol;
import com.gop.domain.enums.SymbolStatus;

public interface ConfigSymbolService {

	boolean validateConfigSymbol(String symbol, SymbolStatus listed);

	ConfigSymbol getConfigSymbol(String symbol);

	void updateConfigAsset(ConfigSymbol configSymbol);

	void saveConfigAsset(ConfigSymbol configSymbol);

	List<ConfigSymbol> getAllSymbol();

	List<ConfigSymbol> getAllSymbolBySymbol(String symbol);


}
