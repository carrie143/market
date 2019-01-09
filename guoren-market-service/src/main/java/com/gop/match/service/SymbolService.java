package com.gop.match.service;

import java.util.List;
import java.util.Map;

import com.gop.domain.ConfigSymbol;
import com.gop.domain.enums.SymbolStatus;

public interface SymbolService {
	public ConfigSymbol getConfigSymbolBYSymbol(String symbol);

	public List<ConfigSymbol> getConfigSymbols();
	

}
