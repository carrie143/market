package com.gop.c2c.facade.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.gop.asset.dto.AssetOperationDto;
import com.gop.c2c.dto.C2cAlipayInfoDto;
import com.gop.c2c.dto.C2cAlipayInfoDto.C2cAlipayInfoDtoBuilder;
import com.gop.c2c.dto.C2cBankInfoDto;
import com.gop.c2c.dto.C2cBankInfoDto.C2cBankInfoDtoBuilder;
import com.gop.c2c.dto.C2cBasePayChannelDto;
import com.gop.c2c.dto.C2cTransactionOrderArgsDto;
import com.gop.c2c.facade.C2cPayFacade;
import com.gop.c2c.service.C2cAlipayInfoService;
import com.gop.c2c.service.C2cBankInfoService;
import com.gop.c2c.service.C2cOrderCancelRecordService;
import com.gop.c2c.service.C2cOrderPaymentDetailService;
import com.gop.c2c.service.C2cOrderRecordService;
import com.gop.c2c.service.C2cSellAdvertisementService;
import com.gop.c2c.service.C2cTransOrderOperRecordService;
import com.gop.c2c.service.C2cTransOrderService;
import com.gop.c2c.service.C2cUserCancelTransOrderCountRecordService;
import com.gop.code.consts.C2cCodeConst;
import com.gop.code.consts.CommonCodeConst;
import com.gop.domain.C2cAlipayInfo;
import com.gop.domain.C2cBankInfo;
import com.gop.domain.C2cOrderCancelRecord;
import com.gop.domain.C2cOrderPaymentDetail;
import com.gop.domain.C2cOrderRecord;
import com.gop.domain.C2cSellAdvertisement;
import com.gop.domain.C2cTransOrder;
import com.gop.domain.TokenOrderCompletionCount;
import com.gop.domain.User;
import com.gop.domain.enums.C2cPayType;
import com.gop.domain.enums.C2cSellAdvertStatus;
import com.gop.domain.enums.C2cTransOrderStatus;
import com.gop.exception.AppException;
import com.gop.financecheck.enums.AccountClass;
import com.gop.financecheck.enums.AccountSubject;
import com.gop.financecheck.enums.BusinessSubject;
import com.gop.mapper.C2cTransOrderMapper;
import com.gop.offlineorder.service.TokenOrderCompletionCountService;
import com.gop.offlineorder.service.TokenOrderService;
import com.gop.user.facade.UserFacade;
import com.gop.util.OrderUtil;
import com.gop.util.SequenceUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author zhushengtao
 *
 */
@Service("C2cPayFacade")
@Slf4j
public class C2cPayFacadeImpl implements C2cPayFacade {
	@Autowired
	private C2cOrderPaymentDetailService c2cOrderPaymentDetailService;
	@Autowired
	private C2cAlipayInfoService c2cAlipayInfoService;
	@Autowired
	private C2cBankInfoService c2cBankInfoService;
	@Autowired
	private C2cSellAdvertisementService c2cSellAdvertisementService;
	@Autowired
	private C2cUserCancelTransOrderCountRecordService c2cUserCancelTransOrderCountRecordService;
	@Autowired
	private C2cTransOrderOperRecordService c2cTransOrderOperRecordService;
	@Autowired
	private TokenOrderService tokenOrderService;
	@Autowired
	private TokenOrderCompletionCountService tokenOrderCompletionCountService;
	@Autowired
	private UserFacade userFacade;
	@Autowired
	private C2cTransOrderService c2cTransOrderService;
	@Autowired
	private C2cOrderRecordService c2cOrderRecordService;
	@Autowired
	private C2cOrderCancelRecordService c2cOrderCancelRecordService;

	@Override
	public C2cBasePayChannelDto getbasicPayChannelDtoByPaymentDetail(C2cOrderPaymentDetail orderPaymentDetail) {
		if (null == orderPaymentDetail || null == orderPaymentDetail.getPayType()) {
			throw new AppException(CommonCodeConst.FIELD_ERROR, "缺少支付类型");
		}
		String advertId = orderPaymentDetail.getAdvertId();
		C2cPayType payType = orderPaymentDetail.getPayType();
		Integer payChannelId = orderPaymentDetail.getPayChannelId();
		C2cBasePayChannelDto dto = new C2cBasePayChannelDto();
		switch (payType) {
		case BANK:
			C2cBankInfo bankInfo = c2cBankInfoService.selectById(payChannelId);
			C2cBankInfoDtoBuilder bankBuilder = C2cBankInfoDto.builder();
			C2cBankInfoDto bankDto = bankBuilder.bank(bankInfo.getBank()).subBank(bankInfo.getSubbank())
					.name(bankInfo.getName()).acnumber(bankInfo.getAcnumber()).build();
			dto = bankDto;
			break;

		default:
			C2cAlipayInfo ali = c2cAlipayInfoService.selectById(payChannelId);
			C2cAlipayInfoDtoBuilder aliBuilder = C2cAlipayInfoDto.builder();
			C2cAlipayInfoDto aliDto = aliBuilder.alipayNo(ali.getAlipayNo()).name(ali.getName()).uid(ali.getUid())
					.build();
			dto = aliDto;
			break;
		}
		return dto;
	}

}
