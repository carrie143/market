package com.gop.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.domain.JubiUserInformationDetail;
import com.gop.mapper.JubiUserInformationDetailMapper;
import com.gop.user.service.JubiUserInfoDetailService;


@Service
public class JubiUserInfoDetailServiceImpl implements JubiUserInfoDetailService{

	@Autowired
	private JubiUserInformationDetailMapper jubiUserInformationDetailMapper;
	
	@Override
	public void addJubiUserInformationDetail(JubiUserInformationDetail jubiUserInformationDetail) {
		jubiUserInformationDetailMapper.addJubiUserInformationDetail(jubiUserInformationDetail);
	}

}
