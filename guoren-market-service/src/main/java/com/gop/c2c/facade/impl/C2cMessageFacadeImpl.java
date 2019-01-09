package com.gop.c2c.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.StringUtil;
import com.gop.c2c.facade.C2cMessageFacade;
import com.gop.common.SmsMessageService;
import com.gop.domain.User;
import com.gop.domain.enums.UserMessageCategory;
import com.gop.user.facade.UserFacade;
import com.gop.user.service.UserMessageService;

@Service
public class C2cMessageFacadeImpl implements C2cMessageFacade{
	
	@Autowired
	private UserFacade userFacade;
	
	@Autowired
	private UserMessageService userMessageService;
	
	@Autowired
	private SmsMessageService smsMessageService;

	@Override
	public void sendMessageByUid(Integer uid, String tranferMessage) {
		User user = userFacade.getUser(uid);
		String email = user.getEmail();
		String phone = user.getMobile();
		//发送站内信
		userMessageService.insertMessage(uid, tranferMessage,UserMessageCategory.C2C);
		//发送邮件
//		smsMessageService.sendEmailMessage(email, tranferMessage);
		//发送短信
		if(StringUtil.isNotEmpty(phone)) {
			smsMessageService.sendPhoneMessage(phone, tranferMessage);
		}
	}
}
