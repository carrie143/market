package com.gop.sms.facade.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.gop.domain.EmailLog;
import com.gop.domain.enums.ServiceProvider;
import com.gop.sms.dto.EmailDto;
import com.gop.sms.facade.SendEmailServiceFacade;
import com.gop.sms.service.EmailLogService;
import com.gop.sms.service.IEmailService;
import com.gop.vo.ReceivedSmsMessage;

@Service("sendEmailServiceFacade")
public class SendEmailServiceFacadeImpl implements SendEmailServiceFacade {

	@Autowired
	private EmailLogService emailLogService;
	@Autowired
	@Qualifier("IEmailService")
	private IEmailService emailService;
	@Value("${mail.subject}")
	String subject;

	@Override
	public void processMessage(ReceivedSmsMessage message) {

		String email = message.getAccount(); // market传过来的参数最好改个名 mobile->code
		String msgContent = message.getMsgContent();

		// 保存到emailLog数据库中
		EmailLog emailLog = new EmailLog();
		emailLog.setMsgId("");// msgId 短信服务商返回的信息；
		emailLog.setSysCode(message.getSysCode());
		emailLog.setServiceCode(message.getServiceCode());
		emailLog.setServiceProvider(ServiceProvider.TENCENT);
		emailLog.setMsgContent(message.getMsgContent());
		emailLog.setEmail(message.getAccount()); // 需要传送 邮箱发送地址
		emailLog.setCreateDate(new Date());
		emailLogService.addEmailLog(emailLog);

		sendMailDirect(email, msgContent);
	}

	public void sendMailDirect(String toMail, String msgContent) {
		EmailDto emailDto = new EmailDto();
		emailDto.setSubject(subject);
		emailDto.setText(msgContent);

		emailDto.setToUser(Lists.newArrayList(toMail));
		emailService.sendSimpleMail(emailDto);

	}

	@Override
	public void processMessage(ReceivedSmsMessage message, Boolean useTemplate, String msgContent) {
		if (!useTemplate) {
			processMessage(message);
		}
		String email = message.getAccount(); // market传过来的参数最好改个名 mobile->code

		// 保存到emailLog数据库中
		EmailLog emailLog = new EmailLog();
		emailLog.setMsgId("");// msgId 短信服务商返回的信息；
		emailLog.setSysCode(message.getSysCode());
		emailLog.setServiceCode(message.getServiceCode());
		emailLog.setServiceProvider(ServiceProvider.TENCENT);
		emailLog.setMsgContent(msgContent);
		emailLog.setEmail(message.getAccount()); // 需要传送 邮箱发送地址
		emailLog.setCreateDate(new Date());
		emailLogService.addEmailLog(emailLog);

		sendMailDirect(email, message.getMsgContent());
	}

}
