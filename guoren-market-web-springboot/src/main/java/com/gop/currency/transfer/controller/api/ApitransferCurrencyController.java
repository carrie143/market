package com.gop.currency.transfer.controller.api;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.gop.asset.service.ConfigAssetService;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.UserAssetCodeConst;
import com.gop.coin.transfer.service.ConfigAssetProfileService;
import com.gop.currency.transfer.dto.ApiTransferCurrencyDto;
import com.gop.currency.transfer.dto.WithdrawCurrencyDetailDto;
import com.gop.currency.transfer.factory.WithdrawOrderServiceFactory;
import com.gop.currency.transfer.service.ChannelCurrencyWithdrawOrderService;
import com.gop.currency.transfer.service.WithdrawCurrencyService;
import com.gop.domain.ConfigAsset;
import com.gop.domain.WithdrawCurrencyOrderUser;
import com.gop.domain.enums.CurrencyType;
import com.gop.exception.AppException;
import com.gop.util.BigDecimalUtils;

import lombok.extern.slf4j.Slf4j;

@RestController("businesstransferCurrencyControllerV2")
@RequestMapping("/api")
@Slf4j
public class ApitransferCurrencyController {

	private final String defaultPayMode = "SUPERPAY";

	@Autowired
	private WithdrawOrderServiceFactory withdrawOrderServiceFactory;

//	@Autowired
//	private WithdrawCurrencyService withdrawCurrencyService;
	@Autowired
	private ConfigAssetService configAssetService;

	@Autowired
	private ConfigAssetProfileService configAssetProfileService;

	@RequestMapping(value = "/currency", method = RequestMethod.POST)
	@ResponseBody
	public void transferCny(@RequestHeader("user-id") Integer uid, @RequestHeader("broker-id") Integer brokerId,
			@Validated @RequestBody ApiTransferCurrencyDto addAccountDto) {
		ConfigAsset configAsset = configAssetService.getAssetConfig(addAccountDto.getAssetCode());
		if (configAsset == null || !configAsset.getCurrencyType().equals(CurrencyType.CASH)) {
			throw new AppException(UserAssetCodeConst.INVALID_ASSET_CODE);
		}
		String payMode = "BANK";

		ChannelCurrencyWithdrawOrderService channelCurrencyWithdrawOrderService = withdrawOrderServiceFactory
				.getService(payMode);

		BigDecimal fee = channelCurrencyWithdrawOrderService.getWithdrawFee(addAccountDto.getAssetCode(),
				addAccountDto.getPayAmount());

		channelCurrencyWithdrawOrderService.withdrawOrder(uid, addAccountDto.getAssetCode(),
				addAccountDto.getExternalOrderNo(), addAccountDto.getAccountName(),
				addAccountDto.getPayAmount().add(fee), fee, addAccountDto.getBankName(), addAccountDto.getBankNo());
		BigDecimal uncerfAmount = null;
		try {
			
		} catch (Exception e) {
			log.error("自动审核异常{}", e);
		}
	}

	@RequestMapping(value = "/currency/query", method = RequestMethod.POST)
	@ResponseBody
	public WithdrawCurrencyDetailDto queryTransferCnyOrder(@RequestBody JSONObject json,
			@RequestHeader("user-id") Integer uid, @RequestHeader("broker-id") Integer brokerId) {
		String externalOrderNo = json.getString("externalOrderNo");
		if (Strings.isNullOrEmpty(externalOrderNo)) {
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
//		WithdrawCurrencyOrderUser order = withdrawCurrencyService.getOrder(uid, externalOrderNo);
//		return new WithdrawCurrencyDetailDto(order);
		return null;
	}

}
