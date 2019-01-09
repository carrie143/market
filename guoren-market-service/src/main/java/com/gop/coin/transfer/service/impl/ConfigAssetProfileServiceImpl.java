package com.gop.coin.transfer.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.gop.code.consts.CommonCodeConst;
import com.gop.coin.transfer.service.ConfigAssetProfileService;
import com.gop.domain.ConfigAssetProfile;
import com.gop.domain.enums.ConfigAssetType;
import com.gop.exception.AppException;
import com.gop.mapper.ConfigAssetProfileMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConfigAssetProfileServiceImpl implements ConfigAssetProfileService {

	@Autowired
	ConfigAssetProfileMapper configAssetProfileMapper;

	@Override
	public BigDecimal getBigDecimalValue(String assetCode, ConfigAssetType key) {
		ConfigAssetProfile configAssetProfile = get(assetCode, key);
		if (null == configAssetProfile) {
			return null;
		}
		return new BigDecimal(configAssetProfile.getProfileValue());
	}

	@Override
	public Integer getIntegerValue(String assetCode, ConfigAssetType key) {
		ConfigAssetProfile configAssetProfile = get(assetCode, key);
		if (null == configAssetProfile) {
			return null;
		}
		return new Integer(configAssetProfile.getProfileValue());
	}

	private ConfigAssetProfile get(String assetCode, ConfigAssetType key) {
		if (Strings.isNullOrEmpty(assetCode) || key == null) {
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "assetCode以及key不能为空");
		}
		ConfigAssetProfile config = configAssetProfileMapper.selectParamByAssetCodeAndProfileKey(assetCode, key);
		
		return config;
	}

	@Override
	public String getStringValue(String assetCode, ConfigAssetType key) {
		ConfigAssetProfile configAssetProfile = get(assetCode, key);
		if (null == configAssetProfile) {
			return null;
		}
		return configAssetProfile.getProfileValue();
	}

	@Override
	public void createOrUpdateConfigAssetProfile(ConfigAssetProfile configAssetProfile) {

		if (configAssetProfileMapper.createOrUpdateConfigAssetProfile(configAssetProfile) < 1) {
			log.info("更新资产配置失败");
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
	}

	@Override
	public List<ConfigAssetProfile> getConfigAssetProfileList(ConfigAssetType configAssetType) {

		return configAssetProfileMapper.selectByProfileKey(configAssetType);
	}

	@Override
	public List<ConfigAssetProfile> selectAll() {
		return configAssetProfileMapper.selectAll();
	}
	@Override
	public List<ConfigAssetProfile> selectByAssetCodeOrProfileKey(String assetCode, ConfigAssetType key) {
		return configAssetProfileMapper.selectByAssetCodeOrProfileKey(assetCode,key);
	}

	@Override
	public List<ConfigAssetProfile> selectByAssetCode(String assetCode) {
		return configAssetProfileMapper.selectByAssetCode(assetCode);
	}

}
