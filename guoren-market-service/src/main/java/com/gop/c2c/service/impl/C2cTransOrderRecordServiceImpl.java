package com.gop.c2c.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.c2c.service.C2cTransOrderRecordService;
import com.gop.domain.C2cTransOrderRecord;
import com.gop.mapper.C2cTransOrderRecordMapper;

@Service("C2cTransOrderRecordService")
public class C2cTransOrderRecordServiceImpl implements C2cTransOrderRecordService{
	@Autowired
	private C2cTransOrderRecordMapper c2cTransOrderRecordMapper;

	@Override
	public int saveRecord(C2cTransOrderRecord orderRecord) {
		return c2cTransOrderRecordMapper.insertSelective(orderRecord);
	}

}
