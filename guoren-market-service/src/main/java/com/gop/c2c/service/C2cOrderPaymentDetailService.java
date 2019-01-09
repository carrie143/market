package com.gop.c2c.service;

import java.util.List;

import com.gop.domain.C2cOrderPaymentDetail;
import com.gop.domain.enums.C2cPayType;

public interface C2cOrderPaymentDetailService {

	 public List<C2cOrderPaymentDetail> selectDetailByAdvertId(String advertId);

	 public C2cOrderPaymentDetail selectDetailByAdvertIdAndPaytype(String advertId, C2cPayType payType);
	
	 public void addC2cOrderPaymentDetail(C2cOrderPaymentDetail c2cOrderPaymentDetail);
	
	 public List<C2cPayType> selectPayTypeByAdvertId(String advertId);

}
