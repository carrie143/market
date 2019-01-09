package com.gop.sms.service.impl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.common.Environment.EnvironmentEnum;
import com.gop.sms.dto.VerifyCodeDto;
import com.gop.sms.service.MessageGenerator;

@Service("verifyCodeMessageGenerator")
public class VerifyCodeMessageGenerator implements MessageGenerator<VerifyCodeDto> {

	@Autowired
	FreeMarkTemplete freeMarkTemplete;

	@Override
	public String generatorMessage(EnvironmentEnum environmentEnum, VerifyCodeDto varable) {

		Map<String, Object> map = new HashMap<>();
		map.put("emailText", varable.getEmailText());

		if (environmentEnum.equals(EnvironmentEnum.US)) {
			return freeMarkTemplete.getText(varable.getTemplateName(), map, Locale.US);
		} else {
			return freeMarkTemplete.getText(varable.getTemplateName(), map, Locale.CHINA);
		}

	}

}
