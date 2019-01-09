package com.gop.c2c.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.c2c.service.C2cOrderCancelRecordService;
import com.gop.domain.C2cOrderCancelRecord;
import com.gop.mapper.C2cOrderCancelRecordMapper;


@Service
public class C2cOrderCancelRecordServiceImpl  implements  C2cOrderCancelRecordService{
	
	@Autowired
	private C2cOrderCancelRecordMapper c2cOrderCancelRecordMapper;
	
	@Override
	public void addC2cOrderCancelRecordByOrderId(C2cOrderCancelRecord c2cOrderCancelRecord) {
		c2cOrderCancelRecordMapper.addC2cOrderCancelRecordByOrderId(c2cOrderCancelRecord);
	}

}
