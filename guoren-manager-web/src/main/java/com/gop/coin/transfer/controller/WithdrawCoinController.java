package com.gop.coin.transfer.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import com.github.pagehelper.PageInfo;
import com.gop.api.cloud.request.WithdrawConfirmRequest;
import com.gop.api.cloud.request.WithdrawQueryRequest;
import com.gop.api.cloud.response.WithdrawCoinDetailDto;
import com.gop.api.cloud.service.CloudApiService;
import com.gop.asset.service.ConfigAssetService;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.WithdrawalsCodeConst;
import com.gop.coin.transfer.dto.WithdrawCallback;
import com.gop.coin.transfer.dto.WithdrawDetailDto;
import com.gop.coin.transfer.service.WithdrawCoinQueryService;
import com.gop.coin.transfer.service.WithdrawCoinService;
import com.gop.common.Confirm;
import com.gop.domain.User;
import com.gop.domain.WithdrawCoinOrderUser;
import com.gop.domain.enums.WithdrawCoinOrderStatus;
import com.gop.domain.enums.WithdrawCoinOrderType;
import com.gop.exception.AppException;
import com.gop.mode.vo.PageModel;
import com.gop.user.service.UserService;
import com.gop.util.SequenceUtil;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

import javax.servlet.annotation.HttpConstraint;

import lombok.extern.slf4j.Slf4j;

@RestController("managerWithdrawCoinController")
@RequestMapping("/withdraw/coin")
@Slf4j
public class WithdrawCoinController {

	@Autowired
	ConfigAssetService configAssetService;

	@Autowired
	private WithdrawCoinQueryService withdrawCoinQueryService;

	@Autowired
	private UserService userService;

	@Autowired
	private CloudApiService cloudApiService;

	@Autowired
	private WithdrawCoinService withdrawCoinService;

	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
		@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkLoginPasswordStrategy'}})") })
	@RequestMapping(value = "/uncerf", method = RequestMethod.GET)
	public void withdrawCoin(@AuthForHeader AuthContext context, @RequestParam("uid") Integer uid,
			@RequestParam("assetCode") String assetCode, @RequestParam("amount") BigDecimal amount) {

//		String txid = SequenceUtil.getNextId();
//
//		withdrawCoinService.withdrawCoinOrderUnCerf(uid, txid, assetCode, amount);

	}

	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
		@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	@RequestMapping(value = "/processed-query", method = RequestMethod.GET)
	public PageModel<WithdrawCoinDetailDto> transferAlreadyProcessed(@AuthForHeader AuthContext context,
			@RequestParam(value = "brokerId", required = false) Integer brokerId,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "account", required = false) String account,
			@RequestParam(value = "uId", required = false) Integer uId,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "txid", required = false) String txid,
			@RequestParam(value = "assetCode", required = false) String assetCode,
			@RequestParam(value = "beginTime", required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
			@RequestParam(value = "endTime", required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
			@RequestParam(value = "email", required = false)  String email,
			@RequestParam(value = "status", required = false) WithdrawCoinOrderStatus status,
			@RequestParam(value = "sortProp", required = false) String sortProp,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		WithdrawQueryRequest request = new WithdrawQueryRequest();
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);
		request.setStatus(status);
		request.setAssetCode(assetCode);
		request.setAddress(address);
		request.setEndDate(endTime==null?null:format.format(endTime));
		request.setStartDate(beginTime==null?null:format.format(beginTime));
		request.setUid(uId == null?null:(long)uId);
		request.setEmail(email);
		PageInfo<WithdrawCoinDetailDto> pageInfo = cloudApiService.withdrawCoinDetail(request);

		PageModel<WithdrawCoinDetailDto> pageModel = new PageModel<>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(pageSize);
		pageModel.setPageNum(pageInfo.getPages());
		pageModel.setTotal(pageInfo.getTotal());
		pageModel.setList(pageInfo.getList());

		return pageModel;
	}
	
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
		@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	@RequestMapping(value = "/untreated", method = RequestMethod.GET)
	public PageModel<WithdrawCoinDetailDto> transferUntreated(@AuthForHeader AuthContext context,
			@RequestParam(value = "brokerId", required = false) Integer brokerId,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "account", required = false) String account,
			@RequestParam(value = "uId", required = false) Integer uId,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "txid", required = false) String txid,
			@RequestParam(value = "assetCode", required = false) String assetCode,
			@RequestParam(value = "beginTime", required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
			@RequestParam(value = "endTime", required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
			@RequestParam(value = "email", required = false)  String email,
			@RequestParam(value = "status", required = false) WithdrawCoinOrderStatus status,
			@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {

		WithdrawQueryRequest request = new WithdrawQueryRequest();
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);
		request.setStatus(status);
		request.setAssetCode(assetCode);
		PageInfo<WithdrawCoinDetailDto> pageInfo = cloudApiService.withdrawCoinDetail(request);

		PageModel<WithdrawCoinDetailDto> pageModel = new PageModel<>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(pageSize);
		pageModel.setPageNum(pageInfo.getPages());
		pageModel.setTotal(pageInfo.getTotal());
		pageModel.setList(pageInfo.getList());

		return pageModel;
	}
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
		@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkLoginPasswordStrategy'}})") })
	@ResponseBody
	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	public void confirm(@AuthForHeader AuthContext context,
			@RequestParam(value = "refuseMs", required = false) String refuseMs,
			@RequestParam("clientOrderNo") String clientOrderNo,
			@RequestParam("confirm") Confirm confirm) {

		WithdrawQueryRequest queryRequest = new WithdrawQueryRequest();
		queryRequest.setClientOrderNo(clientOrderNo);
		PageInfo<WithdrawCoinDetailDto> pageInfo = cloudApiService.withdrawCoinDetail(queryRequest);
		WithdrawCoinDetailDto detail = pageInfo.getList().stream().filter(order -> order.getClientOrderNo().equals(clientOrderNo)).findAny().orElse(null);
		if (detail == null){
			throw new AppException(WithdrawalsCodeConst.WITHDRAWAL_COIN_RECORD_NOT_EXIST);
		}
		WithdrawConfirmRequest confirmRequest = new WithdrawConfirmRequest();
		confirmRequest.setConfirm(confirm);
		confirmRequest.setClientOrderNo(clientOrderNo);
		confirmRequest.setRefuseMs(refuseMs);
		cloudApiService.withdrawConfirm(confirmRequest);

		if (confirm.equals(Confirm.REFUSE)){
			detail.setStatus(WithdrawCoinOrderStatus.REFUSE.name());
			withdrawCoinService.withdrawRefuse(detail);
		}
	}
}
