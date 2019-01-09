package com.gop.asset.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gop.coin.transfer.dto.WithdrawCoinFeeQueryDto;
import com.gop.coin.transfer.service.ConfigAssetProfileService;
import com.gop.domain.ConfigAssetProfile;
import com.gop.domain.enums.ConfigAssetType;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

@RestController
@RequestMapping("/asset-config")
public class AssetConfigController {

	@Autowired
	private ConfigAssetProfileService configAssetProfileService;
 
	@RequestMapping(value = "/asset-config-list", method = RequestMethod.GET)
	public List<WithdrawCoinFeeQueryDto> withdrawCoinFeeConfigList(@AuthForHeader AuthContext context,
			@RequestParam("key") ConfigAssetType key,
			@RequestParam(value = "assetCode", required = false) String assetCode) {

		// 设置返回list
		List<WithdrawCoinFeeQueryDto> dtoList = new ArrayList<>();

		// 查询数据库中的对应list
		List<ConfigAssetProfile> list = configAssetProfileService.getConfigAssetProfileList(key);

		// 转换查询list为dtolist
		if (null != list) {
			dtoList = list.stream().map(a -> new WithdrawCoinFeeQueryDto(a)).collect(Collectors.toList());
		} else {
			dtoList = Collections.emptyList();
		}
		return dtoList;
	}
}
