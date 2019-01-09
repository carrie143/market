package com.gop.c2c.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.gop.asset.dto.AssetOperationDto;
import com.gop.c2c.facade.C2cMessageFacade;
import com.gop.c2c.service.C2cAlipayInfoService;
import com.gop.c2c.service.C2cBankInfoService;
import com.gop.c2c.service.C2cOrderCancelRecordService;
import com.gop.c2c.service.C2cOrderPaymentDetailService;
import com.gop.c2c.service.C2cOrderRecordService;
import com.gop.c2c.service.C2cSellAdvertisementService;
import com.gop.code.consts.C2cCodeConst;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.MessageConst;
import com.gop.domain.C2cAlipayInfo;
import com.gop.domain.C2cBankInfo;
import com.gop.domain.C2cOrderCancelRecord;
import com.gop.domain.C2cOrderPaymentDetail;
import com.gop.domain.C2cOrderRecord;
import com.gop.domain.C2cSellAdvertisement;
import com.gop.domain.enums.C2cPayType;
import com.gop.domain.enums.C2cSellAdvertStatus;
import com.gop.exception.AppException;
import com.gop.financecheck.enums.AccountClass;
import com.gop.financecheck.enums.AccountSubject;
import com.gop.financecheck.enums.BusinessSubject;
import com.gop.mapper.C2cSellAdvertisementMapper;
import com.gop.util.OrderUtil;
import com.gop.util.SequenceUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class C2cSellAdvertisementServiceImpl implements C2cSellAdvertisementService {

	@Autowired
	private C2cSellAdvertisementMapper c2cSellAdvertisementMapper;

	@Autowired
	private C2cAlipayInfoService c2cAlipayInfoService;

	@Autowired
	private C2cBankInfoService c2cBankInfoService;

	@Autowired
	private C2cOrderPaymentDetailService c2cOrderPaymentDetailService;

	@Autowired
	private C2cOrderRecordService c2cOrderRecordService;

	@Autowired
	private C2cMessageFacade c2cMessageFacade;

	@Autowired
	private C2cOrderCancelRecordService c2cOrderCancelRecordService;


	@Override
	public int updateC2cSellAdvertStatusByAdvertId(String advertId, C2cSellAdvertStatus status,C2cSellAdvertStatus beginStatus) {
		return c2cSellAdvertisementMapper.updateC2cSellAdvertStatusByAdvertId(advertId, status,beginStatus);
	}

	@Override
	public List<C2cSellAdvertisement> selectAllByAssetCode(String assetCode,Integer pageNo,Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return c2cSellAdvertisementMapper.selectAllByAssetCode(assetCode);
	}

	@Override
	public C2cSellAdvertisement selectByAdvertId(String advertId) {
		return c2cSellAdvertisementMapper.selectByAdvertId(advertId);
	}

	@Override
	public List<C2cSellAdvertisement> selectC2cSellAdvertByUid(Integer uid,Integer pageNo,Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize,true);
		PageHelper.orderBy("id desc");
		List<C2cSellAdvertisement> c2cSellAdverts = c2cSellAdvertisementMapper.selectC2cSellAdvertByUid(uid);
		return c2cSellAdverts;
	}

	@Override
	public void addC2cSellAdvertisement(C2cSellAdvertisement c2cSellAdvertisement) {
		c2cSellAdvertisementMapper.addC2cSellAdvertisement(c2cSellAdvertisement);
	}

	@Override
	public List<C2cSellAdvertisement> selectC2cSellAdvertByUidAndStatusAndAssetCode(Integer uid,C2cSellAdvertStatus status,String assetCode,Integer pageNo,Integer pageSize){

		PageHelper.startPage(pageNo, pageSize);
		return c2cSellAdvertisementMapper.selectC2cSellAdvertByUidAndStatusAndAssetCode(uid, status,assetCode);
	}

	@Override
	@Transactional
	public void deployC2cSellAdvertisementByUid(C2cSellAdvertisement c2cSellAdvertisement, Set<C2cPayType> paytype) {
		Integer uid = c2cSellAdvertisement.getUid();
		try {
			String advertId = OrderUtil.generateC2cCode();
			String requestNo = SequenceUtil.getNextId();
			c2cSellAdvertisement.setAdvertId(advertId);
			c2cSellAdvertisement.setRequestNo(requestNo);
			String assetCode = c2cSellAdvertisement.getAssetCode();
			BigDecimal maxSellAmount = c2cSellAdvertisement.getMaxSellAmount();
			BigDecimal tradePrice = c2cSellAdvertisement.getTradePrice();
			//1.新增一条售出广告记录
			addC2cSellAdvertisement(c2cSellAdvertisement);
			//2.支付方式详情表插入多条记录
			for(C2cPayType c2cPayType : paytype) {
				C2cOrderPaymentDetail c2cOrderPaymentDetail = new C2cOrderPaymentDetail();
				c2cOrderPaymentDetail.setUid(uid);
				c2cOrderPaymentDetail.setAdvertId(advertId);
				c2cOrderPaymentDetail.setPayType(c2cPayType);
				//将对应的支付通道表ID存入
				switch(c2cPayType.name()) {
					case "ALIPAY":
						C2cAlipayInfo c2cAlipayInfo = c2cAlipayInfoService.selectByUid(uid);
						c2cOrderPaymentDetail.setPayChannelId(c2cAlipayInfo.getId());
						break;
					case "BANK":
						C2cBankInfo c2cBankInfo = c2cBankInfoService.selectByUid(uid);
						c2cOrderPaymentDetail.setPayChannelId(c2cBankInfo.getId());
						break;
				}
				//调用支付方式详情表接口插入数据
				c2cOrderPaymentDetailService.addC2cOrderPaymentDetail(c2cOrderPaymentDetail);
			}
			//3.订单记录表新增一条记录 用于对账查账
			C2cOrderRecord c2cOrderRecord = new C2cOrderRecord();
			c2cOrderRecord.setUid(uid);
			c2cOrderRecord.setOrderId(SequenceUtil.getNextId());
			c2cOrderRecord.setAdvertId(advertId);
			c2cOrderRecord.setRequestNo(requestNo);
			c2cOrderRecord.setAssetCode(assetCode);
			c2cOrderRecord.setLockNum(maxSellAmount);
			c2cOrderRecord.setPrice(tradePrice);
			c2cOrderRecord.setMoney(maxSellAmount.multiply(tradePrice));
			c2cOrderRecordService.addC2cOrderRecordByAdvertId(c2cOrderRecord);

			//4.按照广告最大售出个数对用户资产进行锁定
			AssetOperationDto assetOperationDto = new AssetOperationDto();
			assetOperationDto.setUid(uid);
			assetOperationDto.setRequestNo(requestNo);
			assetOperationDto.setBusinessSubject(BusinessSubject.LOCK);
			assetOperationDto.setAssetCode(assetCode);
			assetOperationDto.setAmount(maxSellAmount.negate());
			assetOperationDto.setLockAmount(maxSellAmount);
			assetOperationDto.setLoanAmount(BigDecimal.ZERO);
			assetOperationDto.setAccountClass(AccountClass.ASSET);
			assetOperationDto.setAccountSubject(AccountSubject.SYSTEM_TRANSFER_ASSET_LESS);
			assetOperationDto.setIndex(0);
			List<AssetOperationDto> assetOperationDtos = Lists.newArrayList(assetOperationDto);

			//5.发送邮件及短信及站内信 通知用户广告创建成功
			String message = String.format(MessageConst.C2C_SELL_ADVERT_DEPLOY_SUCCESS_MESSAGE,
				assetCode);
			c2cMessageFacade.sendMessageByUid(uid, message);
		} catch (Exception e) {
			log.error("用户发布售出广告异常 userId={},eMessage=" + e.getMessage(),uid, e);
			Throwables.propagateIfInstanceOf(e, AppException.class);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
	}

	@Override
	@Transactional
	public void deleteC2cSellAdvertisementByAdvertId(String advertId,Integer uid) {

		C2cSellAdvertisement c2cSellAdvertisement = selectByAdvertId(advertId);
		//校验广告ID是否存在
		if (c2cSellAdvertisement == null) {
			throw new AppException(C2cCodeConst.ADVERT_NOT_EXIST);
		}
		//校验该广告是否属于本人
		if(!Objects.equals(uid, c2cSellAdvertisement.getUid())) {
			throw new AppException(C2cCodeConst.ADVERT_NOT_BELONG_TO_USER);
		}
		//校验广告是否被购买
		if(C2cSellAdvertStatus.PURCHASED.equals(c2cSellAdvertisement.getStatus())) {
			throw new AppException(C2cCodeConst.ADVERT_HAS_PURCHASED);
		}
		//校验广告是否被删除
		if(C2cSellAdvertStatus.DELETED.equals(c2cSellAdvertisement.getStatus())) {
			throw new AppException(C2cCodeConst.ADVERT_HAS_DELETED);
		}
		try {
			//1.更新广告状态为已删除
			if (updateC2cSellAdvertStatusByAdvertId(advertId, C2cSellAdvertStatus.DELETED,C2cSellAdvertStatus.SHOW) != 1) {
				throw new AppException(C2cCodeConst.ADVERT_HAS_DELETED);
			}
			//2.取消订单表新增一条记录 用于对账查账
			C2cOrderRecord c2cOrderRecord = c2cOrderRecordService.selectOrderByAdvertId(advertId);
			String orderId = c2cOrderRecord.getOrderId();
			String cancelOrderId = SequenceUtil.getNextId();
			String assetCode = c2cSellAdvertisement.getAssetCode();
			String requestNo = SequenceUtil.getNextId();
			BigDecimal returnNum = c2cSellAdvertisement.getLockNum();
			BigDecimal tradePrice = c2cSellAdvertisement.getTradePrice();

			C2cOrderCancelRecord c2cOrderCancelRecord = new C2cOrderCancelRecord();
			c2cOrderCancelRecord.setUid(c2cSellAdvertisement.getUid());
			c2cOrderCancelRecord.setOrderId(orderId);
			c2cOrderCancelRecord.setCancelOrderId(cancelOrderId);
			c2cOrderCancelRecord.setRequestNo(requestNo);
			c2cOrderCancelRecord.setAssetCode(c2cSellAdvertisement.getAssetCode());
			c2cOrderCancelRecord.setReturnNum(c2cSellAdvertisement.getLockNum());
			c2cOrderCancelRecord.setPrice(c2cSellAdvertisement.getTradePrice());
			c2cOrderCancelRecord.setMoney(returnNum.multiply(tradePrice));
			c2cOrderCancelRecordService.addC2cOrderCancelRecordByOrderId(c2cOrderCancelRecord);

			//3.解冻用户资产金额
			AssetOperationDto assetOperationDto = new AssetOperationDto();
			assetOperationDto.setUid(uid);
			assetOperationDto.setRequestNo(requestNo);
			assetOperationDto.setBusinessSubject(BusinessSubject.UNLOCK);
			assetOperationDto.setAssetCode(assetCode);
			assetOperationDto.setAmount(returnNum);
			assetOperationDto.setLockAmount(returnNum.negate());
			assetOperationDto.setLoanAmount(BigDecimal.ZERO);
			assetOperationDto.setAccountClass(AccountClass.ASSET);
			assetOperationDto.setAccountSubject(AccountSubject.SYSTEM_TRANSFER_ASSET_PLUS);
			assetOperationDto.setIndex(0);
			List<AssetOperationDto> assetOperationDtos = Lists.newArrayList(assetOperationDto);

		} catch (Exception e) {
			log.error("用户发布删除广告异常 userId={},eMessage=" + e.getMessage(),uid, e);
			Throwables.propagateIfInstanceOf(e, AppException.class);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
	}

	@Override
	@Transactional
	public void ensureEditC2cSellAdvertisementByUid(C2cSellAdvertisement c2cSellAdvertisement,
			Set<C2cPayType> paytype) {
		Integer uid = c2cSellAdvertisement.getUid();
		try{
			//先删除该广告
			deleteC2cSellAdvertisementByAdvertId(c2cSellAdvertisement.getAdvertId(),uid);
			//再重新发布一条新广告
			deployC2cSellAdvertisementByUid(c2cSellAdvertisement,paytype);
		} catch (Exception e) {
			log.error("用户编辑广告异常 userId={},eMessage=" + e.getMessage(),uid, e);
			Throwables.propagateIfInstanceOf(e, AppException.class);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
	}
}
