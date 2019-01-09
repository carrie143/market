package com.gop.c2c.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.gop.c2c.service.C2cOrderPaymentDetailService;
import com.gop.domain.C2cOrderPaymentDetail;
import com.gop.domain.enums.C2cPayType;
import com.gop.mapper.C2cOrderPaymentDetailMapper;

@Service("C2cOrderPaymentDetailService")
public class C2cOrderPaymentDetailServiceImpl implements C2cOrderPaymentDetailService{
	@Autowired
	private C2cOrderPaymentDetailMapper c2cOrderPaymentDetailMapper; 
	@Override
	public List<C2cOrderPaymentDetail> selectDetailByAdvertId(String advertId) {
		List<C2cOrderPaymentDetail> list = c2cOrderPaymentDetailMapper.selectDetailByAdvertId(advertId);
		return list;
	}
	@Override
	public C2cOrderPaymentDetail selectDetailByAdvertIdAndPaytype(String advertId, C2cPayType payType) {
		return c2cOrderPaymentDetailMapper.selectDetailByAdvertIdAndPaytype(advertId,payType);
	}
	
	@Override
	public void addC2cOrderPaymentDetail(C2cOrderPaymentDetail c2cOrderPaymentDetail) {
		c2cOrderPaymentDetailMapper.addC2cOrderPaymentDetail(c2cOrderPaymentDetail);
	}
	
	@Override
	 public List<C2cPayType> selectPayTypeByAdvertId(String advertId){
		 
		List<C2cOrderPaymentDetail> list = c2cOrderPaymentDetailMapper.selectDetailByAdvertId(advertId);
		List<C2cPayType> c2cPayTypes = Lists.newArrayList();
		for (C2cOrderPaymentDetail c2cOrderPaymentDetail : list) {
			
			c2cPayTypes.add(c2cOrderPaymentDetail.getPayType());
		}
		return  c2cPayTypes;
	 }
	
}
