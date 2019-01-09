package com.gop.user.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.code.consts.UserCodeConst;
import com.gop.domain.User;
import com.gop.exception.AppException;
import com.gop.mapper.UserMapper;
import com.gop.user.facade.BrokerFacade;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BrokerFacadeImpl implements BrokerFacade {

	@Autowired
	private UserMapper userMapper;

	@Override
	public Integer getBrokerIdByUid(Integer uid) {
		User user = userMapper.selectByPrimaryKey(uid);

		if (user == null) {
			log.info("用户信息不存在,uid:{}", uid);
			throw new AppException(UserCodeConst.NO_REGISTER);
		}
		
		return user.getBrokerId();
	}

}
