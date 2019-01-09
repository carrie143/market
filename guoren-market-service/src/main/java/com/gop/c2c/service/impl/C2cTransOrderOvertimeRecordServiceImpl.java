package com.gop.c2c.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.c2c.service.C2cTransOrderOvertimeRecordService;
import com.gop.domain.C2cTransOrderOvertimeRecord;
import com.gop.mapper.C2cTransOrderOvertimeRecordMapper;

@Service("C2cTransOrderOvertimeRecordService")
public class C2cTransOrderOvertimeRecordServiceImpl implements C2cTransOrderOvertimeRecordService {
	@Autowired
	private C2cTransOrderOvertimeRecordMapper c2cTransOrderOvertimeRecordMapper;

	@Override
	public int saveRemind(C2cTransOrderOvertimeRecord record) {
		return c2cTransOrderOvertimeRecordMapper.insertSelective(record);
	}

}
