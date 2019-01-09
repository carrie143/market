package com.gop.activity.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gop.activity.service.UserRedeemActivityConfigService;
import com.gop.activity.service.UserRedeemActivityService;
import com.gop.asset.service.ConfigAssetService;
import com.gop.code.consts.ActivityCodeConst;
import com.gop.code.consts.CommonCodeConst;
import com.gop.domain.ConfigAsset;
import com.gop.domain.UserRedeemActivity;
import com.gop.domain.UserRedeemActivityConfig;
import com.gop.exception.AppException;
import com.gop.user.facade.UserFacade;

import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户活动
 */
@RestController("UserRedeemActivityController")
@RequestMapping("/redeem-activity")
@Slf4j
public class UserRedeemActivityController {

	@Autowired
	private ConfigAssetService configAssetService;
	@Autowired
	private UserRedeemActivityService userRedeemActivityService;
	@Autowired
	private UserRedeemActivityConfigService userRedeemActivityConfigService;

	// 用户通过兑换码,领取资产活动
	@Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})")) 
	@RequestMapping(value = "/receive", method = RequestMethod.GET) 
	public void receiveRedeemActivity(@AuthForHeader AuthContext context, @RequestParam("assetCode") String assetCode,
			@RequestParam("redeemCode") String redeemCode) {
		Integer uid = context.getLoginSession().getUserId();
		// 币种校验
		List<ConfigAsset> availableAssetList = configAssetService.getAvailableAssetCode();
		Set<String> availableAssetSet = availableAssetList.stream().map(c -> c.getAssetCode())
				.collect(Collectors.toSet());
		if (!availableAssetSet.contains(assetCode)) {
			throw new AppException(ActivityCodeConst.REDEEM_CODE_NOT_EXIST, "无效币种");
		}
		//领取
		userRedeemActivityService.receiveRedeemActivity(uid, assetCode, redeemCode);
	}

}
