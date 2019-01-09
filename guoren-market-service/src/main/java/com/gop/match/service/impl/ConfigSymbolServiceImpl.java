package com.gop.match.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.code.consts.CommonCodeConst;
import com.gop.domain.ConfigSymbol;
import com.gop.domain.enums.ConfigSymbolType;
import com.gop.domain.enums.SymbolStatus;
import com.gop.exception.AppException;
import com.gop.mapper.ConfigSymbolMapper;
import com.gop.match.service.ConfigSymbolService;

import lombok.extern.slf4j.Slf4j;

@Service("ConfigSymbolServiceImpl")
@Slf4j
public class ConfigSymbolServiceImpl implements ConfigSymbolService {
	@Autowired
	private ConfigSymbolMapper configSymbolMapper;

	@Override
	public boolean validateConfigSymbol(String symbol, SymbolStatus listed) {
		try {
			ConfigSymbol result = configSymbolMapper.selectBySymbolOnLised(symbol, listed);
			if (null != result) {
				return true;
			}
			log.info("交易对名称验证");
		} catch (Exception e) {
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "交易对名称验证失败");
		}
		return false;
	}

	@Override
	public ConfigSymbol getConfigSymbol(String symbol) {
		try {
			return configSymbolMapper.selectBySymbol(symbol);
		} catch (Exception e) {
			log.error("交易对:" + symbol + ",查询失败", e);
			throw new AppException(CommonCodeConst.SERVICE_ERROR,"交易对:" + symbol + ",查询失败");
		}
	}

	@Override
	public void updateConfigAsset(ConfigSymbol configSymbol) {
		try {
			configSymbolMapper.updateByPrimaryKeySelective(configSymbol);
		} catch (Exception e) {
			log.error("交易对:" + configSymbol.getSymbol() + ",更新失败", e);
			throw new AppException(CommonCodeConst.SERVICE_ERROR,"交易对:" + configSymbol.getSymbol() + ",更新失败");
		}
		
	}

	@Override
	public void saveConfigAsset(ConfigSymbol configSymbol) {
		try {
			configSymbolMapper.insertSelective(configSymbol);
		} catch (Exception e) {
			log.error("交易对:" + configSymbol.getSymbol() + ",添加失败", e);
			throw new AppException(CommonCodeConst.SERVICE_ERROR,"交易对:" + configSymbol.getSymbol() + ",添加失败");
		}
		
	}

	@Override
	public List<ConfigSymbol> getAllSymbol() {
		try {
			return configSymbolMapper.selectAll();
		} catch (Exception e) {
			log.error("查询交易对列表失败", e);
			throw new AppException(CommonCodeConst.SERVICE_ERROR,"查询交易对列表失败");
		}
	}

	@Override
	public List<ConfigSymbol> getAllSymbolBySymbol(String symbol) {
		try {
			return configSymbolMapper.selectAllSymbolBySymbol(symbol);
		} catch (Exception e) {
			log.error("查询交易对列表失败", e);
			throw new AppException(CommonCodeConst.SERVICE_ERROR,"查询交易对列表失败");
		}
	}


}
