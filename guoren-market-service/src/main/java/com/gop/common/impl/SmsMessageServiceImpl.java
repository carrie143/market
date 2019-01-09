package com.gop.common.impl;

import java.util.Date;
import java.util.concurrent.Executors;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.gop.common.Environment;
import com.gop.common.SmsMessageService;
import com.gop.domain.EmailLog;
import com.gop.domain.enums.ServiceCode;
import com.gop.domain.enums.ServiceProvider;
import com.gop.domain.enums.SysCode;
import com.gop.sms.dto.EmailDto;
import com.gop.sms.dto.VerifyCodeDto;
import com.gop.sms.facade.SendEmailServiceFacade;
import com.gop.sms.facade.SendSmsServiceFacade;
import com.gop.sms.service.EmailLogService;
import com.gop.sms.service.IEmailService;
import com.gop.sms.service.MessageGenerator;
import com.gop.vo.ReceivedSmsMessage;
import com.gop.vo.enums.SendStrategy;

@Service
public class SmsMessageServiceImpl implements SmsMessageService {
	private static final Logger loger = LoggerFactory.getLogger(SmsMessageServiceImpl.class);

	private static final ListeningExecutorService guavaExecutor = MoreExecutors
			.listeningDecorator(Executors.newFixedThreadPool(10));

	@Autowired
	private SendSmsServiceFacade sendSmsServiceFacade;

	@Autowired
	private SendEmailServiceFacade sendEmailServiceFacade;

	@Autowired
	private Environment environmentContxt;
	
	@Autowired
	private EmailLogService emailLogService;
	
	@Autowired
	@Qualifier("IEmailService")
	private IEmailService emailService;
	
	@Value("${mail.subject}")
	String subject;
	
	@Autowired
	@Qualifier("verifyCodeMessageGenerator")
	private MessageGenerator<VerifyCodeDto> verifyCodeMessageGenerator;

	@Value("${myExchange}")
	private String exchange;

	@Value("${sendSms.key}")
	private String sendSmsRouterKey;

	@PreDestroy
	public void destory() {
		if (!guavaExecutor.isShutdown()) {
			guavaExecutor.shutdown();
		}

	}

	@Override
	public void sendPhoneMessage(String phone, String message) {
		// TODO Auto-generated method stub
		ReceivedSmsMessage Message = new ReceivedSmsMessage();
		Message.setSysCode(SysCode.GP_MARKET);
		Message.setServiceCode(ServiceCode.VERIFY_CODE);
		Message.setSendStrategy(SendStrategy.FIXED);
		Message.setAccount(phone);
		Message.setMsgContent(message);
		guavaExecutor.submit(new Runnable() {

			@Override
			public void run() {
				try {
					sendSmsServiceFacade.processMessage(Message);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					loger.error("发送错误:message={}", Message, e);
				}
			}
		});

	}

	@Override
	public void sendEmailMessage(String email, String message) {
		// mail：
		ReceivedSmsMessage Message = new ReceivedSmsMessage();
		Message.setSysCode(SysCode.GP_MARKET);
		Message.setServiceCode(ServiceCode.VERIFY_CODE);
		Message.setSendStrategy(SendStrategy.FIXED);
		Message.setMsgContent(message);
		Message.setAccount(email);
		guavaExecutor.submit(new Runnable() {

			@Override
			public void run() {
				try {
					sendEmailServiceFacade.processMessage(Message);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					loger.error("发送错误:message={}", Message, e);
				}

			}
		});

	}

	@Override
	public void sendEmailMessage(String email, String message, boolean useTemplate,String templateName) {
		if (!useTemplate) {
			sendEmailMessage(email, message);
		}
		VerifyCodeDto verifyCodeDto = new VerifyCodeDto();
		verifyCodeDto.setEmailText(message);
		verifyCodeDto.setTemplateName(templateName);
		String sendContent = verifyCodeMessageGenerator.generatorMessage(environmentContxt.getSystemEnvironMent(),
				verifyCodeDto);
		ReceivedSmsMessage Message = new ReceivedSmsMessage();
		Message.setSysCode(SysCode.GP_MARKET);
		Message.setServiceCode(ServiceCode.VERIFY_CODE);
		Message.setSendStrategy(SendStrategy.FIXED);
		Message.setMsgContent(sendContent);
		Message.setAccount(email);
		guavaExecutor.submit(new Runnable() {

			@Override
			public void run() {
				try {
					sendEmailServiceFacade.processMessage(Message,true,message);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					loger.error("发送错误:message={}", Message, e);
				}

			}
		});
	}

}
