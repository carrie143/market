package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.C2cOrderPaymentDetail;
import com.gop.domain.enums.C2cPayType;

public interface C2cOrderPaymentDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(C2cOrderPaymentDetail record);

    int insertSelective(C2cOrderPaymentDetail record);

    C2cOrderPaymentDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(C2cOrderPaymentDetail record);

    int updateByPrimaryKey(C2cOrderPaymentDetail record);

	List<C2cOrderPaymentDetail> selectDetailByAdvertId(String advertId);

	C2cOrderPaymentDetail selectDetailByAdvertIdAndPaytype(@Param("advertId")String advertId,@Param("payType") C2cPayType payType);
	
	int addC2cOrderPaymentDetail(C2cOrderPaymentDetail c2cOrderPaymentDetail);
}