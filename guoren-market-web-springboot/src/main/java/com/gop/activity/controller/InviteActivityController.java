package com.gop.activity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gop.activity.dto.InviteActivityInfoQueryDto;
import com.gop.activity.service.InviteActivityConfigService;
import com.gop.activity.service.InviteActivityRewardConfigService;
import com.gop.activity.service.InviteCompleteInfoService;
import com.gop.activity.service.InviteUserInfoService;
import com.gop.activity.service.InviteUserRewardRecordService;
import com.gop.code.consts.ActivityCodeConst;
import com.gop.domain.InviteActivityConfig;
import com.gop.domain.InviteActivityRewardConfig;
import com.gop.domain.InviteCompleteInfo;
import com.gop.domain.InviteUserInfo;
import com.gop.domain.InviteUserRewardRecord;
import com.gop.domain.enums.InviteActivityRewardConfigInviteType;
import com.gop.domain.enums.InviteActivityRewardConfigStatus;
import com.gop.exception.AppException;
import com.gop.mode.vo.PageModel;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户邀请活动
 */
@RestController("InviteActivityController")	
@RequestMapping("/activity-invite")
@Slf4j
public class InviteActivityController {
	
	
	@Autowired
	private InviteActivityConfigService inviteActivityConfigService;
	
	@Autowired 
	private InviteUserInfoService inviteUserInfoService;
	
	@Autowired
	private InviteUserRewardRecordService inviteUserRewardRecordService;
	
	@Autowired
	private InviteActivityRewardConfigService inviteActivityRewardConfigService;
	
	@Autowired
	private InviteCompleteInfoService inviteCompleteInfoService;
	
	
	//用户邀请活动信息查询
	@Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))	
	@RequestMapping(value = "/info-query", method = RequestMethod.GET)
	public InviteActivityInfoQueryDto infoQuery(@AuthForHeader AuthContext context,@RequestParam("activityName") String activityName) {	
		Integer uid = context.getLoginSession().getUserId();
		InviteActivityConfig inviteActivityConfig = inviteActivityConfigService.getInviteActivityConfigByActivityName(activityName);
		if (null == inviteActivityConfig) {
			throw new AppException(ActivityCodeConst.INVITE_ACTIVITY_NOT_EXIST);
		}
		Integer activityId = inviteActivityConfig.getId();
		InviteUserInfo inviteUserInfo = inviteUserInfoService.getInviteUserInfoByUid(uid);
		//获取用户邀请码
		String inviteCode = inviteUserInfo.getInviteCode();
		//获取已获得奖励数量
		List<InviteUserRewardRecord> inviteUserRewardRecords = inviteUserRewardRecordService.getInviteUserRewardRecordListByUidAndActivityIdAndType(uid,activityId,
				InviteActivityRewardConfigInviteType.INVITER);
		//获取数字资产礼包内容
		List<InviteActivityRewardConfig> inviteActivityRewardConfigs = inviteActivityRewardConfigService.getInviteActivityRewardConfigListByActivityId(activityId,
				InviteActivityRewardConfigInviteType.INVITED,InviteActivityRewardConfigStatus.ON);
		//获取已成功邀请实名注册的人数
		Integer inviteCount = inviteCompleteInfoService.countInviteCompleteInfoByInviteUidAndActivityId(uid, activityId);
		Integer rewardCount = inviteUserRewardRecordService.countInviteUserRewardRecordByUidAndActivityIdAndType(uid, activityId, InviteActivityRewardConfigInviteType.INVITED);
		InviteActivityInfoQueryDto inviteActivityInfoQueryDto = InviteActivityInfoQueryDto.builder()
				.inviteCode(inviteCode)
				.inviteUserRewardRecords(inviteUserRewardRecords)
				.inviteActivityRewardConfigs(inviteActivityRewardConfigs)
				.inviteCount(inviteCount)
				.isReceiveReward(rewardCount >0)
				.build();
		return inviteActivityInfoQueryDto;
	}
	
	//用户邀请活动已完成的推荐查询
	@Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))	
	@RequestMapping(value = "/recommend-query", method = RequestMethod.GET)
	public PageModel<InviteCompleteInfo> recommendQuery(@AuthForHeader AuthContext context,
			@RequestParam("activityName") String activityName,
			@RequestParam("pageNo") Integer pageNo,
			@RequestParam("pageSize") Integer pageSize) {	
		Integer uid = context.getLoginSession().getUserId();
		InviteActivityConfig inviteActivityConfig = inviteActivityConfigService.getInviteActivityConfigByActivityName(activityName);
		if (null == inviteActivityConfig) {
			throw new AppException(ActivityCodeConst.INVITE_ACTIVITY_NOT_EXIST);
		}
		Integer activityId = inviteActivityConfig.getId();
		List<InviteCompleteInfo> inviteCompleteInfos = inviteCompleteInfoService.selectInviteCompleteInfoByInviteUidAndActivityId(uid, activityId, pageNo, pageSize);
		if (null == inviteCompleteInfos || inviteCompleteInfos.isEmpty()) {
			 return new PageModel<>();
		}
		PageInfo<InviteCompleteInfo> pageInfo = new PageInfo<>(inviteCompleteInfos);
		PageModel<InviteCompleteInfo> pageModel = new PageModel<InviteCompleteInfo>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageNum(pageInfo.getPages());
		pageModel.setPageSize(pageSize);
		pageModel.setTotal(pageInfo.getTotal());
		pageModel.setList(inviteCompleteInfos);
		return pageModel;
	}
		
	//用户邀请活动已获得的奖励查询
	@Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))	
	@RequestMapping(value = "/reward-query", method = RequestMethod.GET)
	public PageModel<InviteUserRewardRecord> rewardQuery(@AuthForHeader AuthContext context,
			@RequestParam("activityName") String activityName,
			@RequestParam("pageNo") Integer pageNo,
			@RequestParam("pageSize") Integer pageSize) {	
		Integer uid = context.getLoginSession().getUserId();
		InviteActivityConfig inviteActivityConfig = inviteActivityConfigService.getInviteActivityConfigByActivityName(activityName);
		if (null == inviteActivityConfig) {
			throw new AppException(ActivityCodeConst.INVITE_ACTIVITY_NOT_EXIST);
		}
		Integer activityId = inviteActivityConfig.getId();
		List<InviteUserRewardRecord> inviteUserRewardRecords = inviteUserRewardRecordService.selectInviteUserRewardRecord(uid, activityId, pageNo, pageSize);
		if (null == inviteUserRewardRecords || inviteUserRewardRecords.isEmpty()) {
			 return new PageModel<>();
		}
		PageInfo<InviteUserRewardRecord> pageInfo = new PageInfo<>(inviteUserRewardRecords);
		PageModel<InviteUserRewardRecord> pageModel = new PageModel<InviteUserRewardRecord>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageNum(pageInfo.getPages());
		pageModel.setPageSize(pageSize);
		pageModel.setTotal(pageInfo.getTotal());
		pageModel.setList(inviteUserRewardRecords);
		return pageModel;
	}
}
