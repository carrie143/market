package com.gop.match.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.gop.code.consts.CommonCodeConst;
import com.gop.domain.ConfigSymbolProfile;
import com.gop.domain.enums.ConfigSymbolType;
import com.gop.exception.AppException;
import com.gop.mapper.ConfigSymbolProfileMapper;
import com.gop.match.service.ConfigSymbolProfileService;

import lombok.extern.slf4j.Slf4j;

@Service("ConfigSymbolProfileService")
@Slf4j
public class ConfigSymbolProfileServiceImpl implements ConfigSymbolProfileService {
	@Autowired
	private ConfigSymbolProfileMapper mapper;

	@Override
	public void createOrUpdateConfigSymbolProfile(ConfigSymbolProfile configSymbolProfile) {
		try {

			mapper.insertOnDuplicate(configSymbolProfile);

		} catch (Exception e) {
			log.error("交易对:{},key:{}配置置更新失败", configSymbolProfile);
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "更新交易对配置失败");
		}
	}

	@Override
	public List<ConfigSymbolProfile> getConfigSymbolProfileByKey(ConfigSymbolType key) {
		try {
			List<ConfigSymbolProfile> list = mapper.selectByProfileKey(key);
		
			return list;
		} catch (Exception e) {
		
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "获取交易对配置失败");
		}
	}

	@Override
	public BigDecimal getBigDecimalValue(String symbol, ConfigSymbolType key) {
		ConfigSymbolProfile configSymbolProfile = mapper.selectBySymbolAndProfileKey(symbol, key);
		if (Strings.isNullOrEmpty(symbol) || null == key) {
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "symbol与key不能为空");
		}

		if (null == configSymbolProfile) {
			return BigDecimal.ZERO;
		}
		return new BigDecimal(configSymbolProfile.getProfileValue());
	}

	@Override
	public String getStringValue(String assetCode, ConfigSymbolType key) {
		throw new AppException(CommonCodeConst.SERVICE_ERROR, "暂时不支持的方法");
	}

	@Override
	public List<ConfigSymbolProfile> selectAll() {
		return mapper.selectAll();
	}

}
