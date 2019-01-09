package com.gop.asset.controller;

import com.github.pagehelper.PageInfo;
import com.gop.api.cloud.request.BrokerAssetAccountRequest;
import com.gop.api.cloud.request.WithdrawQueryRequest;
import com.gop.api.cloud.response.WithdrawCoinDetailDto;
import com.gop.api.cloud.service.CloudApiService;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.gop.asset.dto.AssetDto;
import com.gop.asset.dto.FinanceDetailDto;
import com.gop.asset.dto.UserWithdrawnDto;
import com.gop.asset.service.ConfigAssetService;
import com.gop.coin.transfer.service.CheckWithdrawCoinService;
import com.gop.domain.enums.WithdrawCoinOrderStatus;
import com.gop.mode.vo.PageModel;
import com.gop.util.DateUtils;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

@RestController
@RequestMapping("/asset")
public class UserAssetController {

	@Autowired
	private ConfigAssetService configAssetService;
	@Autowired
	private CheckWithdrawCoinService checkWithdrawCoinService;
	@Autowired
	private CloudApiService cloudApiService;

	// 查询用户资产列表
	@Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))
	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public List<AssetDto> getUserAccountAssets(@AuthForHeader AuthContext context) {


		Set<String> configSet = configAssetService.getAvailableAssetCode().stream().map(a -> a.getAssetCode())
				.collect(Collectors.toSet());

		Integer uid = context.getLoginSession().getUserId();
		BrokerAssetAccountRequest request = new BrokerAssetAccountRequest();
		request.setUid(uid.longValue());
		request.setAssetList(new ArrayList<>(configSet));
		List<com.gop.api.cloud.response.AssetDto> userAccountAssets = cloudApiService
				.getUserAccountAssets(request);
		List<AssetDto> assetDtos = Lists.newLinkedList();
		userAccountAssets.stream().forEach(assetDto -> {
			AssetDto assetDto1 = new AssetDto();
			BeanUtils.copyProperties(assetDto, assetDto1, "uId");
			assetDto1.setUserId(assetDto.getUId().intValue());
			assetDtos.add(assetDto1);
		});
		return assetDtos;
	}

	// 查询用户单日提现总额
	@Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))
	@RequestMapping(value = "/withdrawn", method = RequestMethod.POST)
	public BigDecimal getUserWithdrawnAmount(@AuthForHeader AuthContext context, @RequestBody UserWithdrawnDto dto) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = format.format(dto.getDate()) + " 00:00:00";
		String endDate = format.format(dto.getDate()) + " 23:59:59";
		WithdrawQueryRequest request = new WithdrawQueryRequest();
		request.setAssetCode(dto.getAssetCode());
		request.setUid(context.getLoginSession().getUserId().longValue());
		request.setStartDate(startDate);
		request.setEndDate(endDate);
		PageInfo<WithdrawCoinDetailDto> withdrawCoinDetailDtoPageInfo = cloudApiService
				.withdrawCoinDetail(request);
		List<WithdrawCoinDetailDto> withdrawDailyList = withdrawCoinDetailDtoPageInfo.getList();
		BigDecimal withdrawDailyAlreadyUsed = BigDecimal.ZERO;
		for (WithdrawCoinDetailDto detailDto : withdrawDailyList) {
			if (WithdrawCoinOrderStatus.SUCCESS.name().equals(detailDto.getStatus())
					|| WithdrawCoinOrderStatus.PROCESSING.name().equals(detailDto.getStatus())) {
				withdrawDailyAlreadyUsed = withdrawDailyAlreadyUsed.add(detailDto.getAmount());
			}
		}
		return withdrawDailyAlreadyUsed;
	}
}
