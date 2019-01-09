package com.gop.activity.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.gop.activity.service.UserActivityRecordService;
import com.gop.activity.service.UserActivityService;
import com.gop.asset.dto.AssetOperationDto;
import com.gop.code.consts.ActivityCodeConst;
import com.gop.code.consts.CommonCodeConst;
import com.gop.domain.UserActivityRecord;
import com.gop.exception.AppException;
import com.gop.financecheck.enums.AccountClass;
import com.gop.financecheck.enums.AccountSubject;
import com.gop.financecheck.enums.BusinessSubject;

import com.gop.util.SequenceUtil;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class UserActivityServiceImpl implements UserActivityService{
	
	@Autowired
	private UserActivityRecordService userActivityRecordService;
	
	@Override
	@Transactional
	public void assetOperationActivityUser(Integer uid,String cardType,String cardNo) {
		try {
			String requestNo = SequenceUtil.getNextId();
			UserActivityRecord  userActivityRecord = new UserActivityRecord();
			userActivityRecord.setUid(uid);
			userActivityRecord.setActivityType("ACT1229");
			userActivityRecord.setCardType(cardType);
			userActivityRecord.setCardNo(cardNo);
			userActivityRecord.setRequestNo(requestNo);
			int count = userActivityRecordService.saveUserActivityRecord(userActivityRecord);
			if (count != 1) {
				throw new AppException(CommonCodeConst.SERVICE_ERROR);
			}
			List<AssetOperationDto> assetOperationDtos = Lists.newArrayList();
			AssetOperationDto assetOperationDto = new AssetOperationDto();
			assetOperationDto.setUid(uid);
			assetOperationDto.setRequestNo(requestNo);
			assetOperationDto.setBusinessSubject(BusinessSubject.ACTIVITY_REWARD);
			assetOperationDto.setAssetCode("ACT");
			assetOperationDto.setAmount(new BigDecimal("5"));
			assetOperationDto.setLockAmount(BigDecimal.ZERO);
			assetOperationDto.setLoanAmount(BigDecimal.ZERO);
			assetOperationDto.setAccountClass(AccountClass.ASSET);
			assetOperationDto.setAccountSubject(AccountSubject.DEPOSIT_COIN);
			assetOperationDto.setIndex(0);
			assetOperationDtos.add(assetOperationDto);
			//给用户加活动发放的数字资产
		}
		catch (DuplicateKeyException e) {
			throw new AppException(ActivityCodeConst.USER_HAS_RECEIVE_REWARD);
		}
		catch (Exception e) {
			log.error("为用户账户添加活动发放的ACT资产异常userId={},eMessage=" + e.getMessage(),uid, e);
			Throwables.propagateIfInstanceOf(e, AppException.class);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
	}
}
