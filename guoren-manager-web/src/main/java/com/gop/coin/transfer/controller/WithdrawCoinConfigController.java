package com.gop.coin.transfer.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gop.coin.transfer.service.ConfigAssetProfileService;
import com.gop.domain.ConfigAssetProfile;
import com.gop.domain.enums.ConfigAssetType;
import com.gop.match.dto.WithdrawCoinFeeConfigDto;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

import lombok.extern.slf4j.Slf4j;

/**
 * 提币手续费与每日提币限额的设置
 * 
 * @author caleb
 *
 */
@RestController("WithdrawCoinConfigController")
@RequestMapping("/withdraw-config")
@Slf4j
public class WithdrawCoinConfigController {
	@Autowired
	private ConfigAssetProfileService configAssetProfileService;


	// 转账配置查询
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	@RequestMapping(value = "/config-list", method = RequestMethod.GET)
	public List<WithdrawCoinFeeConfigDto> withdrawCoinFeeConfigList(@AuthForHeader AuthContext context,
			@RequestParam("key") ConfigAssetType key,
			@RequestParam(value = "assetCode", required = false) String assetCode) {

		// 设置返回list
		List<WithdrawCoinFeeConfigDto> dtoList = new ArrayList<>();

		// 查询数据库中的对应list
		List<ConfigAssetProfile> list = configAssetProfileService.getConfigAssetProfileList(key);

		// 转换查询list为dtolist
		if (null != list) {
			for (ConfigAssetProfile configAssetProfile : list) {
				WithdrawCoinFeeConfigDto dto = new WithdrawCoinFeeConfigDto();
				dto.setId(configAssetProfile.getId());
				dto.setAssetCode(configAssetProfile.getAssetCode());
				dto.setProfileKey(configAssetProfile.getProfileKey());
				dto.setProfileValue(configAssetProfile.getProfileValue());
				dtoList.add(dto);
			}
		} else {
			dtoList = Collections.EMPTY_LIST;
		}
		return dtoList;
	}

}
