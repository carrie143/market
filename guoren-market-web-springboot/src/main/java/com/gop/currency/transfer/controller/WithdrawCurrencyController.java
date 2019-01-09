package com.gop.currency.transfer.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gop.currency.transfer.dto.WithdrawDto;
import com.gop.currency.transfer.dto.WithdrawHistory;
import com.gop.currency.transfer.factory.WithdrawOrderServiceFactory;
import com.gop.currency.transfer.service.ChannelCurrencyWithdrawOrderService;
import com.gop.currency.transfer.service.WithdrawCurrencyService;
import com.gop.domain.WithdrawCurrencyOrderUser;
import com.gop.mode.vo.PageModel;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

@RestController
@RequestMapping("/currency")
public class WithdrawCurrencyController {

	/**
	 *
	 * 提现记录查询
	 *
	 * @param context
	 * @param assetCode
	 * @param pageSize
	 * @param pageNo
	 * @return
	 * @author zhangli
	 */
	@Autowired
	WithdrawOrderServiceFactory withdrawOrderServiceFactory;
	
	

//	@Autowired
//	WithdrawCurrencyService WithdrawService;

	@Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))
	@RequestMapping(value = "/withdraw-history", method = RequestMethod.GET)
	public PageModel<WithdrawHistory> currencyWithdrawHistory(@AuthForHeader AuthContext context,
			@RequestParam("assetCode") String assetCode, @RequestParam("pageSize") Integer pageSize,
			@RequestParam("pageNo") Integer pageNo) {

		Integer uid = context.getLoginSession().getUserId();

		// ChannelCurrencyWithdrawOrderService withdrawOrderService =
		// withdrawOrderServiceFactory.getService(channelType);

//		PageInfo<WithdrawCurrencyOrderUser> page = WithdrawService.queryOrder(uid, assetCode, pageNo, pageSize);

		PageModel<WithdrawHistory> pages = new PageModel<>();
//		pages.setPageNo(pageNo);
//		pages.setPageSize(pageSize);
//		pages.setPageNum(page.getPages());
//		pages.setList(
//				page.getList().stream().map(withdraw -> new WithdrawHistory(withdraw)).collect(Collectors.toList()));
		return pages;
	}

	/**
	 * 提现
	 * 
	 * @param context
	 * 提现请求body
	 * 
	 * @author zhangli
	 */

	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkPayPasswordStregy'}})"),
			@Strategy(authStrategy = "exe({{'checkServiceCodeStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkCertificationLevel2'}})") })
	@RequestMapping(value = "/withdraw", method = RequestMethod.POST)
	public void withdraw(@AuthForHeader AuthContext context, @RequestBody WithdrawDto withdrawDto) {

		Integer uid = context.getLoginSession().getUserId();
		ChannelCurrencyWithdrawOrderService withdrawOrderService = withdrawOrderServiceFactory
				.getService(withdrawDto.getChannelType());
		
		
		withdrawOrderService.withdrawOrder(withdrawDto.getUserAccountId(), withdrawDto.getExternalOrderNo(),
				withdrawDto.getAssetCode(), uid, withdrawDto.getAmount(), withdrawDto.getMemo());
	}
}
