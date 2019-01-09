package com.gop.activity.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gop.activity.service.UserActivityRecordService;
import com.gop.activity.service.UserActivityService;
import com.gop.authentication.service.UserIdentificationService;
import com.gop.code.consts.ActivityCodeConst;
import com.gop.domain.User;
import com.gop.domain.UserIdentification;
import com.gop.domain.enums.AuditDealStatus;
import com.gop.domain.enums.AuditStatus;
import com.gop.exception.AppException;
import com.gop.user.facade.UserFacade;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

import lombok.extern.slf4j.Slf4j;


/**
 * 用户活动
 */
@RestController("UserActivityController")	
@RequestMapping("/activity")
@Slf4j
public class UserActivityController {
	
	@Autowired
	private UserFacade userFacade;
	
	@Autowired
	private UserIdentificationService userIdentificationService;
	
	@Autowired
	private UserActivityService userActivityService;
	
	@Autowired
	private UserActivityRecordService userActivityRecordService;

	//2017-12-29 12:00:00
	private final long preTime = 1514520000000L;
	//2018-1-02 12:00:00
	private final long postTime = 1514865600000L;
	
	private final String activityType = "ACT1229";
	
	
	//用户领取ACT 171229 活动
	@Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))	
	@RequestMapping(value = "/receive", method = RequestMethod.GET)
	public void activityAct(@AuthForHeader AuthContext context) {	
		Integer uid = context.getLoginSession().getUserId();
		User user = userFacade.getUser(uid);
		//判断用户注册时间是否在preTime之后,在postTime之前
		if (user.getCreateDate().getTime() < preTime || user.getCreateDate().getTime() > postTime) {
			throw new AppException(ActivityCodeConst.USER_NOT_REQUIRED_ACTIVITY);		
		}
		UserIdentification userIdentification = userIdentificationService.getUserByUidAndStatusAndAuditStatus(uid,AuditDealStatus.FINISH, AuditStatus.OK);
		//判断用户是否完成KYC认证
		if (userIdentification == null) {
			throw new AppException(ActivityCodeConst.USER_NOT_REQUIRED_ACTIVITY);		
		}
		//判断用户KYC认证完成是否在postTime之前
		if (userIdentification.getUpdateDate().getTime() > postTime) {
			throw new AppException(ActivityCodeConst.USER_NOT_REQUIRED_ACTIVITY);		
		}
		//校验用户是否领取过
		if (userActivityRecordService.getCountByUidAndActivityType(uid, activityType) ==1) {
			throw new AppException(ActivityCodeConst.USER_HAS_RECEIVE_REWARD);
		}
		//校验用户是否使用过相同身份证或护照号进行领取奖励
		if (userActivityRecordService.getCountByActivityTypeAndCardTypeAndCardNo(activityType,userIdentification.getCardType(), userIdentification.getCardNo()) > 0) {
			throw new AppException(ActivityCodeConst.USER_NOT_REQUIRED_ACTIVITY);		
		}		
		
		//满足条件开始为用户加资产
		userActivityService.assetOperationActivityUser(uid,userIdentification.getCardType(), userIdentification.getCardNo());
	}
	
	//校验用户是否有资格领取奖励
	@Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))	
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public void activityCheck(@AuthForHeader AuthContext context) {	
		Integer uid = context.getLoginSession().getUserId();
		User user = userFacade.getUser(uid);
		//判断用户注册时间是否在preTime之后,在postTime之前
		if (user.getCreateDate().getTime() < preTime || user.getCreateDate().getTime() > postTime) {
			throw new AppException(ActivityCodeConst.USER_NOT_REQUIRED_ACTIVITY);		
		}
		UserIdentification userIdentification = userIdentificationService.getUserByUidAndStatusAndAuditStatus(uid,AuditDealStatus.FINISH, AuditStatus.OK);
		//判断用户是否完成KYC认证
		if (userIdentification == null) {
			throw new AppException(ActivityCodeConst.USER_NOT_REQUIRED_ACTIVITY);		
		}
		//判断用户KYC认证完成是否在postTime之前
		if (userIdentification.getUpdateDate().getTime() > postTime) {
			throw new AppException(ActivityCodeConst.USER_NOT_REQUIRED_ACTIVITY);		
		}
		//校验用户是否领取过
		if (userActivityRecordService.getCountByUidAndActivityType(uid, activityType) ==1) {
			throw new AppException(ActivityCodeConst.USER_HAS_RECEIVE_REWARD);
		}
		//校验用户是否使用过相同身份证或护照号进行领取奖励
		if (userActivityRecordService.getCountByActivityTypeAndCardTypeAndCardNo(activityType,userIdentification.getCardType(), userIdentification.getCardNo()) > 0) {
			throw new AppException(ActivityCodeConst.USER_NOT_REQUIRED_ACTIVITY);		
		}		
	}
}
