package com.gop.common.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gop.common.MqDeadsMessageService;
import com.gop.domain.MqDeadsMessage;
import com.gop.mapper.MqDeadsMessageMapper;

@Service
public class MqDeadsMessageServiceImpl implements MqDeadsMessageService {

	@Autowired
	MqDeadsMessageMapper deadsMessageMapper;

	@Transactional
	public void saveDeadsMessage(MqDeadsMessage deadsMessage) {
		deadsMessageMapper.insertSelective(deadsMessage);
	}

}
