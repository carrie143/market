package com.gop.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gop.domain.UserLoginLog;
import com.gop.mode.vo.PageModel;
import com.gop.user.service.UserLoginLogService;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户登陆ip记录
 */
@RestController("UserIpRecordController")	
@RequestMapping("/login-ipaddress")
@Slf4j
public class UserIpRecordController {
	
	@Autowired
	private UserLoginLogService userLoginLogService;
	//用户登陆ip记录查询
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})")})
	@RequestMapping(value = "/ip-list", method = RequestMethod.GET)
	public PageModel<UserLoginLog> queryUserTransactionFeeWhiteList(@AuthForHeader AuthContext context,
			@RequestParam(value = "ipAddress", required = false) String ipAddress,@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
		UserLoginLog userLoginLog=new UserLoginLog();
		userLoginLog.setIpAddress(ipAddress);
		return userLoginLogService.getUserLoginLog(userLoginLog, pageNo, pageSize);
	}
	
}