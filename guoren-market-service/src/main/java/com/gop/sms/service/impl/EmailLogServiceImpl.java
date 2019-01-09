package com.gop.sms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.domain.EmailLog;
import com.gop.mapper.EmailLogMapper;
import com.gop.sms.service.EmailLogService;

@Service("EmailLogService")
public class EmailLogServiceImpl implements EmailLogService {
	private static final Logger log = LoggerFactory.getLogger(EmailLogServiceImpl.class);
	
	@Autowired
	private EmailLogMapper emailLogMapper;
	
	@Override
	public void addEmailLog(EmailLog emailLog) {
		int num = emailLogMapper.insertSelective(emailLog);
		if(num <= 0){
			log.error("邮箱日志保存失败，smsLog{}", emailLog);
		}
	}

	@Override
	public void SendEmailLog(EmailLog emailLog) {
		
	}

}
