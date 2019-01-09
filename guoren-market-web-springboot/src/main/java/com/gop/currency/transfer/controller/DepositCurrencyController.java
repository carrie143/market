package com.gop.currency.transfer.controller;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gop.currency.transfer.dto.DepositDto;
import com.gop.currency.transfer.dto.DepositOrderReturnDto;
import com.gop.currency.transfer.dto.RechargeHistory;
import com.gop.currency.transfer.factory.DepositOrderServiceFactory;
import com.gop.currency.transfer.service.DepositCurrencyQueryOrderService;
import com.gop.domain.DepositCurrencyOrderUser;
import com.gop.domain.enums.UserAccountChannelType;
import com.gop.mode.vo.PageModel;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

@RestController
@RequestMapping("/currency")
public class DepositCurrencyController {
	// 转入记录查询

	// 生成转入订单信息表
	@Autowired
	private DepositOrderServiceFactory factory;

	@Autowired
	private DepositCurrencyQueryOrderService depositQueryOrderService;

	@Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))
	@RequestMapping(value = "/recharge-history", method = RequestMethod.GET)
	public PageModel<RechargeHistory> currencyRechargeHistory(@AuthForHeader AuthContext context,
			@RequestParam("assetCode") String assetCode, @RequestParam("pageSize") Integer pageSize,
			@RequestParam("pageNo") Integer pageNo) {

		Integer uid = context.getLoginSession().getUserId();
		PageInfo<DepositCurrencyOrderUser> pageInfos = depositQueryOrderService.queryOrder(uid, assetCode, pageNo,
				pageSize);
		PageModel<RechargeHistory> pages = new PageModel<>();
		pages.setPageNo(pageNo);
		pages.setPageSize(pageSize);
		pages.setPageNum(pageInfos.getPages());
		pages.setList(
				pageInfos.getList().stream().map(order -> new RechargeHistory(order)).collect(Collectors.toList()));

		return pages;
	}

	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkCertification'}})") })
	@RequestMapping(value = "/deposit", method = RequestMethod.POST)
	public DepositOrderReturnDto deposito(@AuthForHeader AuthContext context, @RequestBody DepositDto depositDto) {

		Integer uid = context.getLoginSession().getUserId();
		Integer channelWithdrawId = depositDto.getAccountId();
		UserAccountChannelType channelType = depositDto.getChannelType();
		String assetCode = depositDto.getAssetCode();
		BigDecimal money = depositDto.getAmount();
		String memo = depositDto.getMemo();
		return factory.getService(channelType).depositOrder(channelWithdrawId, assetCode, uid, money, memo);
	}
}
