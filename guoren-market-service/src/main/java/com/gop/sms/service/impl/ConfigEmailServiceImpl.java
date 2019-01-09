package com.gop.sms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.domain.ConfigEmail;
import com.gop.domain.enums.ConfigEmailStatus;
import com.gop.mapper.ConfigEmailMapper;
import com.gop.sms.service.ConfigEmailService;
@Service
public class ConfigEmailServiceImpl implements ConfigEmailService{
	@Autowired
	private ConfigEmailMapper configEmailMapper; 
	@Override
	public ConfigEmail getEmailByMinSendCountAndStatus(ConfigEmailStatus status) {
		return configEmailMapper.getEmailByMinSendCountAndStatus(status);
	}

	@Override
	public int updateConfigEmail(ConfigEmail configEmail) {
		return configEmailMapper.updateByPrimaryKey(configEmail);
	}

	@Override
	public int updateEmailCount(Integer id) {
		return configEmailMapper.updateMailCount(id);
	}
	

}
