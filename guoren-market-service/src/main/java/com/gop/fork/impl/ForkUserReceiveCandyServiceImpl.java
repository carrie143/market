package com.gop.fork.impl;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.gop.asset.dto.AssetOperationDto;
import com.gop.code.consts.ActivityCodeConst;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.ForkCodeConst;
import com.gop.domain.ForkUserReceiveCandy;
import com.gop.domain.ForkUserReceiveCandyDetail;
import com.gop.domain.enums.ForkUserReceiveCandyStatus;
import com.gop.exception.AppException;
import com.gop.financecheck.enums.AccountClass;
import com.gop.financecheck.enums.AccountSubject;
import com.gop.financecheck.enums.BusinessSubject;
import com.gop.fork.ForkUserReceiveCandyDetailService;
import com.gop.fork.ForkUserReceiveCandyService;
import com.gop.mapper.ForkUserReceiveCandyMapper;
import com.gop.util.SequenceUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ForkUserReceiveCandyServiceImpl implements ForkUserReceiveCandyService{
	
	@Autowired
	private ForkUserReceiveCandyMapper forkUserReceiveCandyMapper;
	
	@Autowired
	private ForkUserReceiveCandyDetailService  forkUserReceiveCandyDetailService;
	
	@Override
	public ForkUserReceiveCandy getForkUserReceiveCandyByUidAndAssetCode(Integer uid, String forkAssetCode,
			String targetAssetCode) {
		return forkUserReceiveCandyMapper.getForkUserReceiveCandyByUidAndAssetCode(uid,forkAssetCode,targetAssetCode);
	}

	@Override
	@Transactional
	public void receiveForkUserReceiveCandy(Integer uid, String forkAssetCode, String targetAssetCode) {
		ForkUserReceiveCandy forkUserReceiveCandy = getForkUserReceiveCandyByUidAndAssetCode(uid,forkAssetCode,targetAssetCode);
		if (forkUserReceiveCandy == null ) {
			throw new AppException(ForkCodeConst.FORK_QUALIFICATION_ERROR);
		}
		if (ForkUserReceiveCandyStatus.RECEIVED.equals(forkUserReceiveCandy.getStatus())){
			throw new AppException(ForkCodeConst.FORK_TARGET_ASSET_RECEIVED);
		}
		try {
			String requestNo = SequenceUtil.getNextId();
			Integer id = forkUserReceiveCandy.getId();
			//更新状态为已领取
			if(updateForkUserReceiveCandyStatusById(id,ForkUserReceiveCandyStatus.UNRECEIVE,ForkUserReceiveCandyStatus.RECEIVED) != 1) {
				throw new AppException(ForkCodeConst.FORK_TARGET_ASSET_RECEIVED);
			}
			//用户领取糖果流水表新增一条记录
			ForkUserReceiveCandyDetail forkUserReceiveCandyDetail = new ForkUserReceiveCandyDetail();
			//将糖果记录表中id存储至流水表中
			forkUserReceiveCandyDetail.setForkCandyId(id);
			forkUserReceiveCandyDetail.setUid(uid);
			forkUserReceiveCandyDetail.setForkAssetCode(forkAssetCode);
			forkUserReceiveCandyDetail.setTargetAssetCode(targetAssetCode);
			forkUserReceiveCandyDetail.setForkAmount(forkUserReceiveCandy.getForkAmount());
			forkUserReceiveCandyDetail.setTargetAmount(forkUserReceiveCandy.getTargetAmount());
			forkUserReceiveCandyDetail.setExchangeRate(forkUserReceiveCandy.getExchangeRate());
			forkUserReceiveCandyDetail.setRequestNo(requestNo);
			forkUserReceiveCandyDetailService.addForkUserReceiveCandyDetail(forkUserReceiveCandyDetail);
			
			//开始为用户增加糖果资产
			AssetOperationDto assetOperationDto = new AssetOperationDto();
			assetOperationDto.setUid(uid);
			assetOperationDto.setRequestNo(requestNo);
			assetOperationDto.setBusinessSubject(BusinessSubject.FORK_CANDY_IN);
			assetOperationDto.setAssetCode(targetAssetCode);
			assetOperationDto.setAmount(forkUserReceiveCandy.getTargetAmount());
			assetOperationDto.setLockAmount(BigDecimal.ZERO);
			assetOperationDto.setLoanAmount(BigDecimal.ZERO);
			assetOperationDto.setAccountClass(AccountClass.ASSET);
			assetOperationDto.setAccountSubject(AccountSubject.DEPOSIT_COIN);
			assetOperationDto.setIndex(0);
			List<AssetOperationDto> assetOperationDtos = Lists.newArrayList(assetOperationDto);

		} 
		catch (DuplicateKeyException e) {
			throw new AppException(ForkCodeConst.FORK_TARGET_ASSET_RECEIVED);
		}
		catch (Exception e) {
			log.error("用户领取糖果异常 userId={},eMessage=" + e.getMessage(),uid, e);
			Throwables.propagateIfInstanceOf(e, AppException.class);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
	}

	@Override
	public int updateForkUserReceiveCandyStatusById(Integer id, ForkUserReceiveCandyStatus beginStatus,
			ForkUserReceiveCandyStatus endStatus) {
		return forkUserReceiveCandyMapper.updateForkUserReceiveCandyStatusById(id, beginStatus, endStatus);
	}

}
