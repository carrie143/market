package com.gop.fork.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gop.asset.service.ConfigAssetService;
import com.gop.code.consts.ForkCodeConst;
import com.gop.domain.ConfigAsset;
import com.gop.domain.ForkUserReceiveCandy;
import com.gop.domain.enums.ForkUserReceiveCandyStatus;
import com.gop.exception.AppException;
import com.gop.fork.ForkUserReceiveCandyService;
import com.gop.fork.dto.ForkUserReceiveCandyDto;
import com.gop.fork.enums.ForkUserReceiveCandyDtoStatus;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

@RestController
@RequestMapping("/fork")
public class ForkUserReceiveCandyController {
	@Autowired
	private ForkUserReceiveCandyService forkUserReceiveCandyService;
	@Autowired
	private ConfigAssetService configAssetService;

	@RequestMapping(value = "/fork-check", method = RequestMethod.GET)
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	public ForkUserReceiveCandyDto forkCheck(@AuthForHeader AuthContext context,
			@RequestParam("forkAssetCode") String forkAssetCode,
			@RequestParam("targetAssetCode") String targetAssetCode) {
		Integer uid = context.getLoginSession().getUserId();
		// 币种校验
		List<ConfigAsset> configAssets = configAssetService.getAvailableAssetCode();
		Set<String> assetCodes = configAssets.stream().map(a -> a.getAssetCode()).collect(Collectors.toSet());

		if (!(assetCodes.contains(forkAssetCode) && assetCodes.contains(targetAssetCode))) {
			throw new AppException(ForkCodeConst.FORK_TARGET_ASSET_INVALID,"无效的糖果");
		}
		ForkUserReceiveCandy candy = forkUserReceiveCandyService.getForkUserReceiveCandyByUidAndAssetCode(uid,
				forkAssetCode, targetAssetCode);
		ForkUserReceiveCandyDto dto = new ForkUserReceiveCandyDto();
		if (null == candy) {
			dto.setCandyDtoStatus(ForkUserReceiveCandyDtoStatus.DISQUALIFIED);
			return dto;
		}
		if (ForkUserReceiveCandyStatus.RECEIVED.equals(candy.getStatus())) {
			dto.setCandyDtoStatus(ForkUserReceiveCandyDtoStatus.RECEIVED);
			return dto;
		}
		dto.setCandyDtoStatus(ForkUserReceiveCandyDtoStatus.UNRECEIVE);
		return dto;
	}

	@RequestMapping(value = "/fork-receive", method = RequestMethod.GET)
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	public void forkReceive(@AuthForHeader AuthContext context, @RequestParam("forkAssetCode") String forkAssetCode,
			@RequestParam("targetAssetCode") String targetAssetCode) {
		Integer uid = context.getLoginSession().getUserId();
		// 币种校验
		List<ConfigAsset> configAssets = configAssetService.getAvailableAssetCode();
		Set<String> assetCodes = configAssets.stream().map(a -> a.getAssetCode()).collect(Collectors.toSet());

		if (!(assetCodes.contains(forkAssetCode) && assetCodes.contains(targetAssetCode))) {
			throw new AppException(ForkCodeConst.FORK_TARGET_ASSET_INVALID,"无效的糖果");
		}
		forkUserReceiveCandyService.receiveForkUserReceiveCandy(uid, forkAssetCode, targetAssetCode);
	}
}
