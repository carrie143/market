package com.gop.c2c.service;

import java.util.Date;

import com.gop.domain.C2cUserCancelTransOrderCountRecord;

public interface C2cUserCancelTransOrderCountRecordService {

	int getCountOfCancelToday(Integer uid, Date beginDate, Date endDate);

	int recordCancel(C2cUserCancelTransOrderCountRecord cancelRecord);

}
