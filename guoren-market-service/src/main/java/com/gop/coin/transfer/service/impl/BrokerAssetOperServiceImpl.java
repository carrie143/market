package com.gop.coin.transfer.service.impl;

import java.math.BigDecimal;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.gop.asset.dto.AssetOperationDto;
import com.gop.code.consts.CommonCodeConst;
import com.gop.coin.transfer.service.BrokerAssetOperDetailService;
import com.gop.coin.transfer.service.BrokerAssetOperService;
import com.gop.domain.BrokerAssetOperDetail;
import com.gop.exception.AppException;
import com.gop.financecheck.enums.AccountClass;
import com.gop.financecheck.enums.AccountSubject;
import com.gop.financecheck.enums.BusinessSubject;
import com.gop.util.OrderUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BrokerAssetOperServiceImpl implements BrokerAssetOperService{
	
	@Autowired
	private BrokerAssetOperDetailService brokerAssetOperDetailService;

	@Override
	@Transactional
	public void brokerAssetOperDeposit(Integer uid, String assetCode, BigDecimal amount, Integer operUid) {
		try {
			String requestNo = OrderUtil.generateCode(OrderUtil.ORDER_SERVICE, OrderUtil.TRANSFER_IN_CURRENCY);
			BrokerAssetOperDetail brokerAssetOperDetail = new BrokerAssetOperDetail();
			brokerAssetOperDetail.setUid(uid);
			brokerAssetOperDetail.setAssetCode(assetCode);
			brokerAssetOperDetail.setBusinessSubject(BusinessSubject.DEPOSIT.name());
			brokerAssetOperDetail.setRequestNo(requestNo);
			brokerAssetOperDetail.setAmount(amount);
			brokerAssetOperDetail.setOperUid(operUid);
			//券商用户充值添加流水记录
			brokerAssetOperDetailService.addBrokerAssetOperDetail(brokerAssetOperDetail);
			
			AssetOperationDto depositDto = new AssetOperationDto();
			depositDto.setUid(uid);
			depositDto.setAccountClass(AccountClass.LIABILITY);
			depositDto.setAccountSubject(AccountSubject.DEPOSIT_COMMON);
			depositDto.setAssetCode(assetCode);
			depositDto.setBusinessSubject(BusinessSubject.DEPOSIT);
			depositDto.setAmount(amount);
			depositDto.setLoanAmount(BigDecimal.ZERO);
			depositDto.setLockAmount(BigDecimal.ZERO);
			depositDto.setMemo("券商充值");
			depositDto.setRequestNo(requestNo);
			depositDto.setIndex(0);
			List<AssetOperationDto> ops = Lists.newArrayList(depositDto);
		} catch (Exception e) {
			log.error("为券商用户充值异常userId={},eMessage=" + e.getMessage(),uid, e);
			Throwables.propagateIfInstanceOf(e, AppException.class);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
	}

	@Override
	@Transactional
	public void brokerAssetOperWithdraw(Integer uid, String assetCode, BigDecimal amount, Integer operUid) {
		try {
			String requestNo = OrderUtil.generateCode(OrderUtil.ORDER_SERVICE, OrderUtil.TRANSFER_IN_CURRENCY);
			BrokerAssetOperDetail brokerAssetOperDetail = new BrokerAssetOperDetail();
			brokerAssetOperDetail.setUid(uid);
			brokerAssetOperDetail.setAssetCode(assetCode);
			brokerAssetOperDetail.setBusinessSubject(BusinessSubject.WITHDRAW.name());
			brokerAssetOperDetail.setRequestNo(requestNo);
			brokerAssetOperDetail.setAmount(amount.negate());
			brokerAssetOperDetail.setOperUid(operUid);
			//券商用户提取添加流水记录
			brokerAssetOperDetailService.addBrokerAssetOperDetail(brokerAssetOperDetail);
			
			AssetOperationDto depositDto = new AssetOperationDto();
			depositDto.setUid(uid);
			depositDto.setAccountClass(AccountClass.LIABILITY);
			depositDto.setAccountSubject(AccountSubject.WITHDRAW_COMMON);
			depositDto.setAssetCode(assetCode);
			depositDto.setBusinessSubject(BusinessSubject.WITHDRAW);
			depositDto.setAmount(amount.negate());
			depositDto.setLoanAmount(BigDecimal.ZERO);
			depositDto.setLockAmount(BigDecimal.ZERO);
			depositDto.setMemo("券商提现");
			depositDto.setRequestNo(requestNo);
			depositDto.setIndex(0);
			List<AssetOperationDto> ops = Lists.newArrayList(depositDto);
		} catch (Exception e) {
			log.error("为券商用户提现异常userId={},eMessage=" + e.getMessage(),uid, e);
			Throwables.propagateIfInstanceOf(e, AppException.class);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
	}
}
