package com.gop.coin.transfer.controller.api;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Optional;
import com.gop.asset.service.ConfigAssetService;
import com.gop.code.consts.UserAssetCodeConst;
import com.gop.code.consts.WithdrawalsCodeConst;
import com.gop.coin.transfer.dto.ApiTransferOutReturnDto;
import com.gop.coin.transfer.dto.BusinessTransferInDto;
import com.gop.coin.transfer.dto.BusinessTransferOutDto;
import com.gop.coin.transfer.dto.BusinessWithdrawCoinDto;
import com.gop.coin.transfer.dto.DepositCoinDto;
import com.gop.coin.transfer.dto.WithdrawCoinDetailDto;
import com.gop.coin.transfer.factory.WithdrawCoinServiceFactory;
import com.gop.coin.transfer.service.DepositCoinQueryService;
import com.gop.coin.transfer.service.WithdrawCoinQueryService;
import com.gop.coin.transfer.service.WithdrawCoinService;
import com.gop.domain.ConfigAsset;
import com.gop.domain.DepositCoinOrderUser;
import com.gop.domain.WithdrawCoinOrderUser;
import com.gop.domain.enums.CurrencyType;
import com.gop.exception.AppException;
import com.gop.util.CheckStateUtil;
import com.gop.util.DateUtils;

import lombok.extern.slf4j.Slf4j;

@RestController("businessTransferGopControllerV1")
@RequestMapping("/api")
@Slf4j
public class ApiTransferGopController {

	@Autowired
	private WithdrawCoinQueryService withdrawQueryCoinService;

	@Autowired
	private DepositCoinQueryService depositQueryCoinService;
	@Autowired
	private ConfigAssetService configAssetService;

	@Autowired
	private WithdrawCoinServiceFactory withdrawCoinServiceFactory;

	// @RequestMapping(value = "/transferOut/order", method =
	// RequestMethod.POST)
	public WithdrawCoinDetailDto getTransferOutOrder(@RequestHeader("user-id") Integer uid,
			@RequestHeader("broker-id") Integer brokerId, @Validated @RequestBody BusinessTransferOutDto dto) throws Exception{

		WithdrawCoinOrderUser order = null;

		String outOrder = dto.getOrderNo();
		log.info("receive data: userId={}, orderId={}", uid, outOrder);

		order = withdrawQueryCoinService.getTransferOutOrderByExternalOrderId(uid, outOrder);

		if (order == null) {
			return new WithdrawCoinDetailDto();
		} else {
			return new WithdrawCoinDetailDto(order);
		}
	}

	// @RequestMapping(value = "/transferIn/order", method = RequestMethod.POST)
	public List<DepositCoinDto> getTransferInOrders(@RequestHeader("user-id") Integer uid,
			@RequestHeader("broker-id") Integer brokerId, @RequestBody BusinessTransferInDto dto) {
		Date beginDate = null;
		Date endDate = null;
		long periods = 0l;

		log.info("receive data: businessNo={}, fromDate={}, toDate={}", uid, dto.getFromDate(), dto.getToDate());

		try {
			beginDate = DateUtils.parseDate(dto.getFromDate());
			endDate = DateUtils.parseDate(dto.getToDate());
			Optional.of(beginDate);
			Optional.of(endDate);
		} catch (Exception e) {
			throw new IllegalArgumentException("参数校验异常");
		}

		periods = DateUtils.subtract(beginDate, endDate, TimeUnit.DAYS);
		CheckStateUtil.checkArgumentState(periods >= 1, "开始时间必须小于结束时间，且时间间隔必须小于1天");

		List<DepositCoinOrderUser> lst = depositQueryCoinService.queryOrder(uid, beginDate, endDate);
		return lst.stream().map(order -> new DepositCoinDto(order)).collect(Collectors.toList());
	}

	// @RequestMapping(value = "/transferOut", method = RequestMethod.POST)
//	public void transferOut(@RequestHeader("user-id") Integer uid, @RequestHeader("broker-id") Integer brokerId,
//			@Valid @RequestBody BusinessWithdrawCoinDto dto) throws Exception {
//
//		ConfigAsset configAsset = configAssetService.getAssetConfig(dto.getAssetCode());
//		if (configAsset == null || !configAsset.getCurrencyType().equals(CurrencyType.COIN)) {
//			throw new AppException(UserAssetCodeConst.INVALID_ASSET_CODE);
//		}
//		WithdrawCoinService withdrawCoinService = withdrawCoinServiceFactory.getService(dto.getAssetCode());
//
//		WithdrawCoinOrderUser order = null;
//
//		String orderNo = dto.getOrderNo();
//		String toAddress = dto.getToAddress();
//		BigDecimal amount = dto.getAmount();
//		String message = dto.getMessage();
//
//		log.info("receive data: uid={}, orderNo={}, toAddress={}, amount={}, message={}", uid, orderNo, toAddress,
//				amount, message);
//
//		BigDecimal fee = withdrawCoinService.getWithdrawFee(amount);
//
//		order = withdrawQueryCoinService.getTransferOutOrderByExternalOrderId(uid, orderNo);
//
//		if (order == null) {
//
//			try {
//				withdrawCoinService.withdrawCoinOrder(uid, dto.getOrderNo(), dto.getAssetCode(), dto.getAmount().add(fee),
//            dto.getFee(), dto.getToAddress(), dto.getMessage());
//			} catch (Exception e) {
//				throw new Exception(e);
//			}
//
//		} else {
//			throw new AppException(WithdrawalsCodeConst.WITHDRAWAL_COIN_RECORD_HAS_EXIST, "重复订单号");
//		}
//
//	}
}
