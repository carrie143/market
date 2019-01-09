package com.gop.sms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.domain.SmsLog;
import com.gop.mapper.SmsLogMapper;
import com.gop.sms.service.SmsLogService;

@Service("SmsLogService")
public class SmsLogServiceImpl implements SmsLogService {
	private static final Logger log = LoggerFactory.getLogger(SmsLogServiceImpl.class);
	
	@Autowired
	private SmsLogMapper smsLogMapper;
	
	@Override
	public void addSmsLog(SmsLog smsLog) {
		int num = smsLogMapper.insertSelective(smsLog);
		if(num <= 0){
			log.error("短信日志保存失败，smsLog{}", smsLog);
		}
	}

	@Override
	public void SendSmsLog(SmsLog smsLog) {
		
	}

}
