package com.gop.c2c.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.c2c.service.C2cUserCancelTransOrderCountRecordService;
import com.gop.domain.C2cUserCancelTransOrderCountRecord;
import com.gop.mapper.C2cUserCancelTransOrderCountRecordMapper;

@Service
public class C2cUserCancelTransOrderCountRecordServiceImpl implements C2cUserCancelTransOrderCountRecordService {
	@Autowired
	private C2cUserCancelTransOrderCountRecordMapper c2cUserCancelTransOrderCountRecordMapper;

	@Override
	public int getCountOfCancelToday(Integer uid, Date begigDate, Date endDate) {
		return c2cUserCancelTransOrderCountRecordMapper.getCountOfCancelToday(uid, begigDate, endDate);
	}

	@Override
	public int recordCancel(C2cUserCancelTransOrderCountRecord cancelRecord) {
		return c2cUserCancelTransOrderCountRecordMapper.insertSelective(cancelRecord);
	}

}
