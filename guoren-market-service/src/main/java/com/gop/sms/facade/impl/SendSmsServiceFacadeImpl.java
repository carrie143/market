package com.gop.sms.facade.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.cache.RedisCache;
import com.gop.domain.SmsLog;
import com.gop.domain.enums.ServiceProvider;
import com.gop.sms.context.ServiceStrategyContext;
import com.gop.sms.facade.SendSmsServiceFacade;
import com.gop.sms.service.SmsLogService;
import com.gop.vo.ReceivedSmsMessage;
import com.gop.vo.enums.SendStrategy;

@Service("sendSmsServiceFacade")
public class SendSmsServiceFacadeImpl implements SendSmsServiceFacade {
	private static final Logger log = LoggerFactory.getLogger(SendSmsServiceFacadeImpl.class);

	@Autowired
	private SmsLogService smsLogService;

	@Autowired
	private RedisCache redisCache;

	@Override
	public void processMessage(ReceivedSmsMessage message) {

		ServiceProvider serviceProvider = determinServiceProvider(message.getSendStrategy(), message.getAccount());
		/**
		 * 固定为微网一家,如果再增加别的SP,注释掉 serviceProvider = ServiceProvider.WEIWANG;
		 */
		serviceProvider = ServiceProvider.WEIWANG;
		ServiceStrategyContext serviceStrategyContext = new ServiceStrategyContext();
		serviceStrategyContext.determineServiceStrategy(serviceProvider, message.getSysCode(),
				message.getServiceCode());
		String msgId = serviceStrategyContext.sendSms(message.getAccount(), message.getMsgContent());
		if (StringUtils.isEmpty(msgId)) {
			log.error("msgid is empty:serviceProvider={}", serviceProvider);
			return;
		}
		SmsLog smsLog = new SmsLog();
		smsLog.setMsgId(msgId);
		smsLog.setSysCode(message.getSysCode());
		smsLog.setServiceCode(message.getServiceCode());
		smsLog.setServiceProvider(serviceProvider);
		smsLog.setMsgContent(message.getMsgContent());
		smsLog.setMobile(message.getAccount());
		smsLog.setCreateDate(new Date());
		smsLogService.addSmsLog(smsLog);
	}

	private ServiceProvider determinServiceProvider(SendStrategy sendStrategy, String phone) {
		ServiceProvider serviceProvider = null;
		switch (sendStrategy) {
		case FIXED:
			serviceProvider = getFixedServiceProvider(phone);
			break;
		case INTURN:
			serviceProvider = getDynamicServiceProvider(phone);
			break;
		default:
			serviceProvider = getFixedServiceProvider(phone);
			break;
		}
		return serviceProvider;
	}

	private ServiceProvider getFixedServiceProvider(String phone) {
		redisCache.saveValue(phone, "1");
		return ServiceProvider.WEIWANG;
	}

	private ServiceProvider getDynamicServiceProvider(String phone) {
		ServiceProvider serviceProvider = null;
		String value = redisCache.getValue(phone);
		if (value.equals("0")) {
			value = "1";
			serviceProvider = ServiceProvider.WEIWANG;
		} else {
			value = "0";
			serviceProvider = ServiceProvider.LINGKAI;
		}
		redisCache.saveValue(phone, value);
		return serviceProvider;
	}
}
