package com.gop.c2c.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.c2c.service.C2cOrderRecordService;
import com.gop.domain.C2cOrderRecord;
import com.gop.mapper.C2cOrderRecordMapper;

@Service("C2cOrderRecordService")
public class C2cOrderRecordServiceImpl implements C2cOrderRecordService{
	@Autowired
	private C2cOrderRecordMapper c2cOrderRecordMapper;
	@Override
	public C2cOrderRecord selectOrderByAdvertId(String advertId) {
		C2cOrderRecord record = c2cOrderRecordMapper.selectByAdvertId(advertId);
		return record;
	}
	
	@Override
	public void addC2cOrderRecordByAdvertId(C2cOrderRecord c2cOrderRecord) {
		c2cOrderRecordMapper.addC2cOrderRecordByAdvertId(c2cOrderRecord);
	}
	
}
