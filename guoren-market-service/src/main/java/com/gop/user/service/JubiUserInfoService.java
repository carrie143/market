package com.gop.user.service;

import java.math.BigDecimal;
import java.util.List;

import com.gop.domain.JubiUserInformation;
import com.gop.domain.enums.JubiRegisterFlag;

public interface JubiUserInfoService {
	
	public List<JubiUserInformation> getJubiUserInfosByPhone(String phone); 
	
	public List<JubiUserInformation> getJubiUserInfosByEmail(String email);
	
}
