package com.gop.c2c.service.impl;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gop.c2c.service.C2cUserEncourageInfoRecordService;
import com.gop.c2c.service.C2cUserEncourageInfoService;
import com.gop.domain.C2cUserEncourageInfo;
import com.gop.domain.C2cUserEncourageInfoRecord;
import com.gop.mapper.C2cUserEncourageInfoRecordMapper;
@Service
public class C2cUserEncourageInfoRecordServiceImpl implements C2cUserEncourageInfoRecordService {

	@Autowired
	private C2cUserEncourageInfoService c2cUserEncourageInfoService;
	
	
	@Autowired
	private C2cUserEncourageInfoRecordMapper c2cUserEncourageInfoRecordMapper;
	
	@Override
	public void addC2cUserEncourageInfoRecord(C2cUserEncourageInfoRecord record) {
		
		c2cUserEncourageInfoRecordMapper.insertSelective(record);
		
	}

	@Override
	public int getTodayEncourageCountByEncourageId(Integer uid,Integer encourageId,Date beginDate,Date endDate) {

		int encourageCount = c2cUserEncourageInfoRecordMapper.selectByEncourageIdAndDate(uid, encourageId,beginDate,endDate);
		return encourageCount;
	}

	
	@Override
	@Transactional
	public void addEncourage(Integer uid, Integer encourageId) {

		C2cUserEncourageInfoRecord record = new C2cUserEncourageInfoRecord();
		record.setUid(uid);
		record.setEncourageId(encourageId);
		c2cUserEncourageInfoRecordMapper.insertSelective(record);
		C2cUserEncourageInfo c2cUserEncourageInfo = c2cUserEncourageInfoService.selectByUid(uid);
		if(c2cUserEncourageInfo == null) {
			
			C2cUserEncourageInfo c2cUserEncourage = new C2cUserEncourageInfo();
			c2cUserEncourage.setEncouragedCount(1);
			c2cUserEncourage.setUid(uid);
			c2cUserEncourageInfoService.addUserEncourageIfo(c2cUserEncourage);
		}else {
			
			c2cUserEncourageInfoService.updateUserEncourageIfoCountByUid(uid);	
		}
	}
}
