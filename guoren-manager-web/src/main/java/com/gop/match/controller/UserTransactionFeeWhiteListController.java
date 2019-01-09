package com.gop.match.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gop.api.cloud.request.ModifyWhitelistReq;
import com.gop.api.cloud.service.CloudApiService;
import com.gop.domain.UserTransactionFeeWhiteList;
import com.gop.match.service.UserTransactionFeeWhiteListService;
import com.gop.mode.vo.PageModel;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

import lombok.extern.slf4j.Slf4j;

/**
 * 提币手续费白名单配置
 */
@RestController("UserTransactionFeeWhiteListController")	
@RequestMapping("/whiteListconfig")
@Slf4j
public class UserTransactionFeeWhiteListController {
	@Autowired
	private UserTransactionFeeWhiteListService userTransactionFeeWhiteListService;

	@Autowired
	private CloudApiService cloudApiService;
	//新增白名单用户
	@Strategys(strategys = {
			@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkLoginPasswordStrategy'}})") })
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public void addUserTransactionFeeWhiteList(@AuthForHeader AuthContext context,
			@RequestParam("uid") Integer uid) {
		UserTransactionFeeWhiteList addwhiteUser=new UserTransactionFeeWhiteList();
		addwhiteUser.setUid(uid);
		addwhiteUser.setAdminId(context.getLoginSession().getUserId());
		userTransactionFeeWhiteListService.addUserTransactionFeeWhiteList(addwhiteUser);

		ModifyWhitelistReq request = new ModifyWhitelistReq();
		request.setBrokerUid((long)uid);
		request.setNanoTime(System.nanoTime());
		request.setWhiteList(true);
		cloudApiService.updateWhiteList(request);
	}

	//删除白名单用户
	@Strategys(strategys = { 
			@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkLoginPasswordStrategy'}})") })
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public void updateUserTransactionFeeWhiteList(@AuthForHeader AuthContext context,
			@RequestParam("uid") Integer uid) {
		ModifyWhitelistReq request = new ModifyWhitelistReq();
		request.setBrokerUid((long)uid);
		request.setNanoTime(System.nanoTime());
		request.setWhiteList(false);
		cloudApiService.updateWhiteList(request);

		userTransactionFeeWhiteListService.updateByUidInValid(uid);
	}

	//白名单用户查询
	@Strategys(strategys = {
			@Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})")})
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public PageModel<UserTransactionFeeWhiteList> queryUserTransactionFeeWhiteList(@AuthForHeader AuthContext context,
			@RequestParam(value = "uid", required = false) Integer uid,@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
		UserTransactionFeeWhiteList whiteUser=new UserTransactionFeeWhiteList();
		whiteUser.setUid(uid);
		return userTransactionFeeWhiteListService.getUserTransactionFeeWhiteList(whiteUser,pageNo,pageSize);
	}
	
}
