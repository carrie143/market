package com.gop.activity.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.gop.activity.service.UserRedeemActivityConfigService;
import com.gop.activity.service.UserRedeemActivityDetailService;
import com.gop.activity.service.UserRedeemActivityService;
import com.gop.asset.dto.AssetOperationDto;
import com.gop.code.consts.ActivityCodeConst;
import com.gop.code.consts.CommonCodeConst;
import com.gop.domain.UserRedeemActivity;
import com.gop.domain.UserRedeemActivityConfig;
import com.gop.domain.UserRedeemActivityDetail;
import com.gop.domain.enums.UserRedeemActivityConfigStatus;
import com.gop.domain.enums.UserRedeemActivityStatus;
import com.gop.exception.AppException;
import com.gop.financecheck.enums.AccountClass;
import com.gop.financecheck.enums.AccountSubject;
import com.gop.financecheck.enums.BusinessSubject;
import com.gop.mapper.UserRedeemActivityMapper;
import com.gop.util.SequenceUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserRedeemActivityServiceImpl implements UserRedeemActivityService {

	@Autowired
	private UserRedeemActivityMapper userRedeemActivityMapper;
	
	@Autowired
	private UserRedeemActivityConfigService userRedeemActivityConfigService;
	@Autowired
	private UserRedeemActivityDetailService userRedeemActivityDetailService;
	
	
	@Override
	@Transactional
	public void receiveRedeemActivity(Integer uid,String assetCode, String redeemCode) {
		try {
			UserRedeemActivity userRedeemActivity = getRedeemActivityByAssetCodeAndRedeemCode(assetCode,redeemCode);
			if(null == userRedeemActivity) {
				throw new AppException(ActivityCodeConst.REDEEM_CODE_NOT_EXIST);
			}
			UserRedeemActivityConfig userRedeemActivityConfig = userRedeemActivityConfigService.selectUserRedeemActivityConfigById(userRedeemActivity.getActivityId());
			if(null == userRedeemActivityConfig) {
				throw new AppException(ActivityCodeConst.REDEEM_ACTIVITY_NOT_EXIST);
			}
			//判断活动是否开启
			if (UserRedeemActivityConfigStatus.OFF.equals(userRedeemActivityConfig.getStatus())) {
				throw new AppException(ActivityCodeConst.REDEEM_ACTIVITY_HAS_CLOSED);
			}
			//判断兑换码是否已被使用
			if (UserRedeemActivityStatus.USED.equals(userRedeemActivity.getStatus())) {
				throw new AppException(ActivityCodeConst.REDEEM_CODE_HAS_USED);
			}
			//更新兑换码状态为已使用
			if(updateRedeemActivityStatusAndUid(userRedeemActivity.getId(),uid,UserRedeemActivityStatus.UNUSE,UserRedeemActivityStatus.USED) != 1) {
				throw new AppException(ActivityCodeConst.REDEEM_CODE_HAS_USED);
			}
			String requestNo = SequenceUtil.getNextId();
			//兑换码流水记录表新增一条记录
			UserRedeemActivityDetail userRedeemActivityDetail = new UserRedeemActivityDetail();
			userRedeemActivityDetail.setUid(uid);
			userRedeemActivityDetail.setRedeemId(userRedeemActivity.getId());
			userRedeemActivityDetail.setAssetCode(assetCode);
			userRedeemActivityDetail.setAmount(userRedeemActivity.getAmount());
			userRedeemActivityDetail.setRequestNo(requestNo);
			userRedeemActivityDetailService.addUserRedeemActivityDetail(userRedeemActivityDetail);
			//给用户加资产
			AssetOperationDto assetOperationDto = new AssetOperationDto();
			assetOperationDto.setUid(uid);
			assetOperationDto.setRequestNo(requestNo);
			assetOperationDto.setBusinessSubject(BusinessSubject.DEPOSIT);
			assetOperationDto.setAssetCode(assetCode);
			assetOperationDto.setAmount(userRedeemActivity.getAmount());
			assetOperationDto.setLockAmount(BigDecimal.ZERO);
			assetOperationDto.setLoanAmount(BigDecimal.ZERO);
			assetOperationDto.setAccountClass(AccountClass.ASSET);
			assetOperationDto.setAccountSubject(AccountSubject.DEPOSIT_COIN);
			assetOperationDto.setIndex(0);
			List<AssetOperationDto> assetOperationDtos = Lists.newArrayList(assetOperationDto);
		} catch (Exception e) {
			log.error("用户兑换码领取异常 userId={},eMessage=" + e.getMessage(),uid, e);
			Throwables.propagateIfInstanceOf(e, AppException.class);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
	}

	@Override
	public UserRedeemActivity getRedeemActivityByAssetCodeAndRedeemCode(String assetCode,String redeemCode) {
		return userRedeemActivityMapper.getRedeemActivityByAssetCodeAndRedeemCode(assetCode,redeemCode);
	}
	
	@Override
	public int updateRedeemActivityStatusAndUid(Integer id,Integer uid,UserRedeemActivityStatus beginStatus,UserRedeemActivityStatus endStatus) {
		return userRedeemActivityMapper.updateRedeemActivityStatusAndUid(id,uid, beginStatus, endStatus);
	}

}
