package com.gop.user.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gop.code.consts.UserCodeConst;
import com.gop.domain.JubiUserInformation;
import com.gop.exception.AppException;
import com.gop.mapper.JubiUserInformationMapper;
import com.gop.user.service.JubiUserInfoService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JubiUserInfoServiceImpl implements JubiUserInfoService {
	
	@Autowired
	private JubiUserInformationMapper jubiUserInformationMapper;
		

	@Override
	public List<JubiUserInformation> getJubiUserInfosByPhone(String phone) {
		List<JubiUserInformation> jubiInfos = jubiUserInformationMapper.selectByPhone(phone);
		if(null == jubiInfos || jubiInfos.isEmpty()) {
			throw new AppException(UserCodeConst.JUBI_USER_NOT_EXIST);
		}
		return jubiInfos;
	}

	@Override
	public List<JubiUserInformation> getJubiUserInfosByEmail(String email) {
		List<JubiUserInformation> jubiInfos = jubiUserInformationMapper.selectByEmail(email);
		if(null == jubiInfos || jubiInfos.isEmpty()) {
			throw new AppException(UserCodeConst.JUBI_USER_NOT_EXIST);
		}
		return jubiInfos;
	}

	
}
