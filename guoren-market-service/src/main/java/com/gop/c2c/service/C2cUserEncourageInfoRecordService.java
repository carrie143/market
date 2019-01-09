package com.gop.c2c.service;

import java.util.Date;

import com.gop.domain.C2cUserEncourageInfoRecord;

public interface C2cUserEncourageInfoRecordService {
	
	public void addC2cUserEncourageInfoRecord(C2cUserEncourageInfoRecord record);
	
	public int getTodayEncourageCountByEncourageId(Integer uid,Integer encourageId,Date beginDate,Date endDate);
	
	public void addEncourage(Integer uid,Integer encourageId);

}
