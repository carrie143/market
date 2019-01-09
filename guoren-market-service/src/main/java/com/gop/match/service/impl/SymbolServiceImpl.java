package com.gop.match.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.domain.ConfigSymbol;
import com.gop.domain.enums.SymbolStatus;
import com.gop.mapper.ConfigSymbolMapper;
import com.gop.match.service.SymbolService;

@Service("symbolServiceImpl")

public class SymbolServiceImpl implements SymbolService {

	@Autowired
	private ConfigSymbolMapper configSymbolMapper;

	@Override
	public ConfigSymbol getConfigSymbolBYSymbol(String symbol) {
		return configSymbolMapper.selectBySymbol(symbol);
	}

	@Override
	public List<ConfigSymbol> getConfigSymbols() {
		return configSymbolMapper.selectAll();
	}


}
