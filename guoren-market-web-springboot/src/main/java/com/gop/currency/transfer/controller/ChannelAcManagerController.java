package com.gop.currency.transfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gop.code.consts.CommonCodeConst;
import com.gop.currency.transfer.dto.AcAccountDto;
import com.gop.currency.transfer.dto.AddAccountDto;
import com.gop.currency.transfer.dto.ChannelIsOpenDto;
import com.gop.currency.transfer.factory.ChannelUserAccountServiceFactory;
import com.gop.currency.transfer.factory.ManagerDepositAccountFactory;
import com.gop.currency.transfer.service.ChannelCurrencyDepositManagerService;
import com.gop.currency.transfer.service.ChannelUserAccountBaseService;
import com.gop.domain.enums.UserAccountChannelType;
import com.gop.exception.AppException;
import com.gop.mode.vo.PageModel;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/transfer/account")
@Slf4j
public class ChannelAcManagerController {

	// 添加卡账户

	@Autowired
	private ChannelUserAccountServiceFactory factory;

	@Autowired
	private ManagerDepositAccountFactory managerDepositAccountFactory;

	// @Autowired
	// private UserFacade userFacade;

	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkPayPasswordStregy'}})"),
			@Strategy(authStrategy = "exe({{'checkServiceCodeStrategy'}})") })
	@RequestMapping(value = "/channel-account", method = RequestMethod.POST)
	public void addAcAccount(@AuthForHeader AuthContext context, @RequestBody AddAccountDto addAccountDto) {
		Integer uid = context.getLoginSession().getUserId();

		// UserSimpleInfoDto user = userFacade.getUserInfoByUid(uid);
		UserAccountChannelType channelType = null;
		try {
			channelType = UserAccountChannelType.valueOf(addAccountDto.getChannelType());
		} catch (Exception e) {
			log.info("传入添加账号渠道错误");
			throw new AppException(CommonCodeConst.FIELD_ERROR);

		}
		String channelAccountName = addAccountDto.getChannelAccountName();

		String channelAccountNo = addAccountDto.getChannelAccountNo();

		ChannelUserAccountBaseService accountService = factory.getUserAccountService(channelType);

		accountService.addAccount(channelAccountNo, channelAccountName, uid);
	}

	@Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))
	@RequestMapping(value = "/channel-account/delete", method = RequestMethod.GET)
	public void deleteAcAccount(@AuthForHeader AuthContext context, @RequestParam("accountNo") String accountNo,
			@RequestParam("channelType") UserAccountChannelType channelType) {

		Integer uid = context.getLoginSession().getUserId();
		ChannelUserAccountBaseService accountService = factory.getUserAccountService(channelType);

		accountService.deleteAccount(accountNo, uid);

	}

	@Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))
	@RequestMapping(value = "/channel-account-list", method = RequestMethod.GET)
	public PageModel<AcAccountDto> getAcAccountList(@AuthForHeader AuthContext context,
			@RequestParam("channelType") UserAccountChannelType channelType, @RequestParam("pageSize") Integer pageSize,
			@RequestParam("pageNo") Integer pageNo) {
		Integer uid = context.getLoginSession().getUserId();
		ChannelUserAccountBaseService accountService = factory.getUserAccountService(channelType);
		return accountService.getList(uid, pageNo, pageSize);
	}

	@Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))
	@RequestMapping(value = "/channel/isopen", method = RequestMethod.GET)
	public ChannelIsOpenDto channelIsOpen(@AuthForHeader AuthContext context,
			@RequestParam("channelType") UserAccountChannelType channelType) {
		Integer uid = context.getLoginSession().getUserId();
		ChannelCurrencyDepositManagerService channelCurrencyDepositManagerService = managerDepositAccountFactory
				.getService(channelType.toString());
		return channelCurrencyDepositManagerService.isGlobalConfigOpen(uid);
	}

}
