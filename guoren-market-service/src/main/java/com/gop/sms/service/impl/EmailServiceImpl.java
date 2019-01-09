package com.gop.sms.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.base.Optional;
import com.gop.domain.ConfigEmail;
import com.gop.domain.enums.ConfigEmailStatus;
import com.gop.sms.dto.EmailDto;
import com.gop.sms.service.ConfigEmailService;
import com.gop.sms.service.IEmailService;


import lombok.extern.slf4j.Slf4j;

/**
 * @author yujianjian
 * @since 2017-09-11 下午2:56
 */
@Service("IEmailService")
@Slf4j
public class EmailServiceImpl implements IEmailService {

	@Value("${spring.mail.username}")
	private String user;
	@Value("${spring.mail.password}")
	private String passWord;
	@Value("${spring.mail.host}")
	private String host;
	@Value("${spring.mail.port}")
	private String port;
	@Value("${spring.mail.properties.mail.smtp.auth}")
	private boolean auth;
	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private boolean starttlsEnable;
	@Value("${spring.mail.properties.mail.smtp.starttls.required}")
	private boolean starttlsRequired;
	@Value("${spring.mail.properties.mail.smtp.timeout}")
	private Integer timeOut;
	@Value("${spring.mail.properties.mail.smtp.socketFactory.class}")
	private String sslFactory;
	@Value("${spring.mail.properties.mail.smtp.socketFactory.fallback}")
	private boolean fallback;
	@Value("${mail.personal}")
	private String personal;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private ConfigEmailService configEmailService;


	@Override
	public boolean sendSimpleMail(EmailDto emailDto) {
		Properties javaMailProperties = new Properties();  
        javaMailProperties.put("mail.smtp.auth", Optional.fromNullable(auth).or(true));   
        javaMailProperties.put("mail.smtp.starttls.enable", Optional.fromNullable(starttlsEnable).or(true));   
        javaMailProperties.put("mail.smtp.starttls.required", Optional.fromNullable(starttlsRequired).or(true));   
        javaMailProperties.put("mail.smtp.timeout", Optional.fromNullable(timeOut).or(15000));
        javaMailProperties.put("mail.smtp.port", Optional.fromNullable(port).or("465"));
        javaMailProperties.put("mail.smtp.socketFactory.class", Optional.fromNullable(sslFactory).or("javax.net.ssl.SSLSocketFactory")); 
        javaMailProperties.put("mail.smtp.socketFactory.fallback", Optional.fromNullable(fallback).or(false)); 
		String fromUser = Optional.fromNullable(emailDto.getFromUser()).or(user);
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		ConfigEmail emailConfig = configEmailService.getEmailByMinSendCountAndStatus(ConfigEmailStatus.LISTED);
		mailSender.setJavaMailProperties(javaMailProperties);
		mailSender.setHost(Optional.fromNullable(emailConfig.getMailHost()).or(host));
		mailSender.setUsername(Optional.fromNullable(emailConfig.getMailUsername()).or(user));  
		mailSender.setPassword(Optional.fromNullable(emailConfig.getMailPassword()).or(passWord));
		mailSender.setDefaultEncoding("UTF-8");
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		configEmailService.updateEmailCount(emailConfig.getId());
		try {
			helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(Optional.fromNullable(emailConfig.getMailUsername()).or(fromUser),Optional.fromNullable(personal).or("OurDax"));
			helper.setTo(emailDto.getToUser().toArray(new String[emailDto.getToUser().size()]));
			if (emailDto.getCcUser() != null && emailDto.getCcUser().length > 0) {
				helper.setCc(emailDto.getCcUser());
			}
			helper.setSubject(emailDto.getSubject());
			helper.setText(emailDto.getText(),true);
			mailSender.send(mimeMessage);
			return true;
		} catch (Exception e) {
			log.error("邮箱地址:{}发送给{}的邮件异常|",emailDto.getFromUser(), emailDto.getToUser(),e);
		}
		return false;
	}

	@Override
	public boolean sendAttachmentsMail(EmailDto emailDto) {
		Assert.notNull(emailDto, "emailDto must not be null");

		Assert.isTrue(StringUtils.isNotEmpty(emailDto.getSubject()), "缺少主题内容");
		Assert.isTrue(emailDto.getFileList().size() > 0, "至少一个附件");
		MimeMessage mailMessage = mailSender.createMimeMessage();
		// 要发送附件一定要加true
		String fromUser = Optional.fromNullable(emailDto.getFromUser()).or(user);
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true);
			messageHelper.setFrom(fromUser);
			messageHelper.setTo(emailDto.getToUser().toArray(new String[emailDto.getToUser().size()]));
			if (emailDto.getCcUser() != null && emailDto.getCcUser().length > 0) {
				messageHelper.setCc(emailDto.getCcUser());
			}
			messageHelper.setSubject(emailDto.getSubject());
			messageHelper.setText(emailDto.getText());
			for (File file : emailDto.getFileList()) {
				messageHelper.addAttachment(file.getName(), file);
			}
			mailSender.send(mailMessage);
			log.info("邮件发送成功|emailDto={}|", emailDto);
			return true;
		} catch (MessagingException e) {
			log.error("发送邮件异常|", e);
		}
		return false;
	}
}
