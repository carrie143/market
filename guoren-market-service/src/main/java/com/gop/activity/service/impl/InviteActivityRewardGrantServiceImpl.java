package com.gop.activity.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Joiner;
import com.google.common.base.Throwables;
import com.gop.activity.service.InviteActivityConfigService;
import com.gop.activity.service.InviteActivityRewardConfigService;
import com.gop.activity.service.InviteActivityRewardGrantService;
import com.gop.activity.service.InviteCompleteInfoService;
import com.gop.activity.service.InviteUserRewardRecordService;
import com.gop.asset.dto.AssetOperationDto;
import com.gop.code.consts.CommonCodeConst;
import com.gop.domain.InviteActivityConfig;
import com.gop.domain.InviteActivityRewardConfig;
import com.gop.domain.InviteCompleteInfo;
import com.gop.domain.InviteUserRewardRecord;
import com.gop.domain.User;
import com.gop.domain.enums.AuthLevel;
import com.gop.domain.enums.InviteActivityConfigStatus;
import com.gop.domain.enums.InviteActivityRewardConfigInviteType;
import com.gop.domain.enums.InviteActivityRewardConfigStatus;
import com.gop.exception.AppException;
import com.gop.financecheck.enums.AccountClass;
import com.gop.financecheck.enums.AccountSubject;
import com.gop.financecheck.enums.BusinessSubject;
import com.gop.user.facade.UserFacade;
import com.gop.util.SequenceUtil;

import lombok.extern.slf4j.Slf4j;

@Service("InviteActivityRewardGrantService")
@Slf4j
public class InviteActivityRewardGrantServiceImpl implements InviteActivityRewardGrantService {
	@Autowired
	private InviteActivityConfigService inviteActivityConfigService;
	@Autowired
	private InviteUserRewardRecordService inviteUserRewardRecordService;
	@Autowired
	private UserFacade userFacade;
	@Autowired
	private InviteActivityRewardConfigService inviteActivityRewardConfigService;
	@Autowired
	private InviteCompleteInfoService inviteCompleteInfoService;

	@Override
	@Transactional
	public void handleInviteActivityRewardByInvitedUser(Integer uid, String activityName) {
		try {

			// 用户校验
			User user = userFacade.getUser(uid);
			if (null == user || 0 == user.getInviteUid() || AuthLevel.LEVEL0.equals(user.getAuthLevel())) {
				return;
			}
			Integer inviter = user.getInviteUid();
			// 校验邀请活动状态
			InviteActivityConfig activityConfig = inviteActivityConfigService
					.getInviteActivityConfigByActivityName(activityName);
			if (null == activityConfig || activityConfig.getStatus().equals(InviteActivityConfigStatus.OFF)) {
				return;
			}
			// 校验是否有完成记录
			InviteCompleteInfo info = inviteCompleteInfoService.getInfoByUidAndActivityId(uid, null);
			if (null != info) {
				return;
			}
			// 处理邀请人与被邀请人奖励
			List<InviteActivityRewardConfig> rewardList = inviteActivityRewardConfigService
					.getInviteActivityRewardConfigListByActivityId(activityConfig.getId(), null,
							InviteActivityRewardConfigStatus.ON);
			List<AssetOperationDto> assetOperationDtos = new ArrayList<AssetOperationDto>();// 资产处理list
			// 用户邀请完成认证信息记录
			InviteCompleteInfo completeInfo = new InviteCompleteInfo();
			completeInfo.setUid(uid);
			completeInfo.setInviteUid(inviter);
			completeInfo.setActivityId(activityConfig.getId());
			completeInfo.setInviteEmail(getEncodeEmail(user.getEmail()));
			inviteCompleteInfoService.createInfo(completeInfo);
			for (int i = 0; i < rewardList.size(); i++) {
				InviteActivityRewardConfig config = rewardList.get(i);
				String requestNo = SequenceUtil.getNextId();
				InviteUserRewardRecord record = new InviteUserRewardRecord();
				// 邀请活动dto处理
				AssetOperationDto assetOperationRewardDto = new AssetOperationDto();
				if (InviteActivityRewardConfigInviteType.INVITER.equals(config.getInviteType())) {
					assetOperationRewardDto.setUid(inviter);
					assetOperationRewardDto.setBusinessSubject(BusinessSubject.INVITER_REWARD);
					record.setUid(inviter);
					record.setInviteType(InviteActivityRewardConfigInviteType.INVITER);
				} else {
					assetOperationRewardDto.setUid(uid);
					assetOperationRewardDto.setBusinessSubject(BusinessSubject.INVITED_REWARD);
					record.setUid(uid);
					record.setInviteType(InviteActivityRewardConfigInviteType.INVITED);
				}
				assetOperationRewardDto.setRequestNo(requestNo);
				assetOperationRewardDto.setAssetCode(config.getRewardAssetCode());
				assetOperationRewardDto.setAmount(config.getAmount());
				assetOperationRewardDto.setLockAmount(BigDecimal.ZERO);
				assetOperationRewardDto.setLoanAmount(BigDecimal.ZERO);
				assetOperationRewardDto.setAccountClass(AccountClass.ASSET);
				assetOperationRewardDto.setAccountSubject(AccountSubject.SYSTEM_TRANSFER_ASSET_PLUS);
				assetOperationRewardDto.setIndex(i);
				assetOperationDtos.add(assetOperationRewardDto);
				// 记录奖励领取
				record.setActivityId(config.getActivityId());
				record.setRewardAmount(config.getAmount());
				record.setRewardAssetCode(config.getRewardAssetCode());
				record.setRequestNo(requestNo);
				
				inviteUserRewardRecordService.createRewardRecord(record);
			}

		} catch (Exception e) {
			log.error("用户认证通过进行邀请人与被邀请人奖赏失败 userId={},eMessage=" + e.getMessage(), uid, e);
			Throwables.propagateIfInstanceOf(e, AppException.class);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
	}

	/**
	 * 用户邮件信息脱敏存储
	 */
	private String getEncodeEmail(String email) {
		String[] split = email.split("@");
		String emailName = split[0];
		int length = emailName.length();
		String result;
		if (length == 1) {
			result = Joiner.on("@").join(emailName + "**" + emailName, split[1]);
		} else {
			String start = emailName.substring(0, 1);
			String end = emailName.substring(length - 1, length);
			result = Joiner.on("@").join(start + "**" + end, split[1]);
		}
		return result;
	}

}
