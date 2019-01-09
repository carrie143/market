package com.gop.asset.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.asset.service.ConfigAssetService;
import com.gop.domain.ConfigAsset;
import com.gop.domain.enums.AssetStatus;
import com.gop.exception.AppException;
import com.gop.mapper.ConfigAssetMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConfigAssetServiceImpl implements ConfigAssetService {

	@Autowired
	private ConfigAssetMapper configAssetMapper;

	@Override
	public List<ConfigAsset> getAvailableAssetCode() {

		return configAssetMapper.selectAll().stream().filter(c -> c.getStatus().equals(AssetStatus.LISTED))
				.collect(Collectors.toList());
	}

	@Override
	public ConfigAsset getAssetConfig(String assetCode) {
		try {
			return configAssetMapper.selectByAssetCode(assetCode);
		} catch (Exception e) {
			log.error("币种" + assetCode + "查询失败", e);
			throw new AppException("币种为:" + assetCode + ",查询失败");
		}
	}

	@Override
	public boolean validateAssetConfig(String asset, AssetStatus listed) {
		try {
			ConfigAsset result = configAssetMapper.selectByAssetCode(asset);
			if (null != result) {
				return result.getStatus().equals(listed);
			}
		} catch (Exception e) {
			throw new AppException("币种为:" + asset + ",查询失败");
		}
		return false;
	}

	@Override
	public void updateConfigAsset(ConfigAsset configAsset) {
		try {
			configAssetMapper.updateByPrimaryKeySelective(configAsset);
		} catch (Exception e) {
			log.error("已有币种" + configAsset.getAssetCode() + "更新失败", e);
			throw new AppException("币种为:" + configAsset.getAssetCode() + ",更新失败");
		}
	}

	@Override
	public void saveConfigAsset(ConfigAsset configAsset) {
		try {
			configAssetMapper.insertSelective(configAsset);
		} catch (Exception e) {
			log.error("已有币种" + configAsset.getAssetCode() + "保存失败", e);
			throw new AppException("币种为:" + configAsset.getAssetCode() + ",保存失败");
		}

	}

	@Override
	public List<ConfigAsset> getAllAssetCode() {
		try {
			return configAssetMapper.selectAll();
		} catch (Exception e) {
			log.error("查询现有币种列表失败", e);
			throw new AppException("查询现有币种列表失败");
		}
	}

	@Override
	public List<ConfigAsset> getAllAssetCodeByAssetCode(String assetCode) {
		try {
			return configAssetMapper.selectAllByAssetCode(assetCode);
		} catch (Exception e) {
			log.error("查询现有币种列表失败", e);
			throw new AppException("查询现有币种列表失败");
		}
	}

	@Override
	public PageInfo<ConfigAsset> getAvailableAssetCode(String assetCode, Integer pageNo, Integer pageSize) {
		PageHelper.startPage(pageNo,pageSize);
		PageHelper.orderBy("id desc");
		return new PageInfo<>(configAssetMapper.selectByAssetCodeAndStatus(assetCode));
	}
}
