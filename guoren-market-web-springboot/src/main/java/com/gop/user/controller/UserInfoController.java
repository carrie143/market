package com.gop.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gop.domain.UserLoginLog;
import com.gop.mode.vo.PageModel;
import com.gop.user.service.UserService;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;
import com.gop.config.CommonConstants;
@RestController("UserInfoController")	
@RequestMapping("/user")
public class UserInfoController {
	//获取用户基本信息
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	//用户登陆ip记录查询
		@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})")})
		@RequestMapping(value = "/ip-list", method = RequestMethod.GET)
		public PageModel<UserLoginLog> queryUserTransactionFeeWhiteList(@AuthForHeader AuthContext context,
			@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
			
			pageNo = CommonConstants.getPageNo(pageNo);
			pageSize = CommonConstants.getPageSize(pageSize);
			return userService.getUserLoginLogByUid(context.getLoginSession().getUserId(), pageNo, pageSize);
		}

}
