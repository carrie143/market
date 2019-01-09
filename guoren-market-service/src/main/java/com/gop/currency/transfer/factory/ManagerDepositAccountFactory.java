package com.gop.currency.transfer.factory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gop.code.consts.CommonCodeConst;
import com.gop.currency.transfer.service.ChannelCurrencyDepositManagerService;
import com.gop.exception.AppException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ManagerDepositAccountFactory {

	@Autowired
	private BeanFactory beanFactory;

	public ChannelCurrencyDepositManagerService getService(String channelType) {
		ChannelCurrencyDepositManagerService service = null;
		switch (channelType) {
		case "OKPAY":
			service = (ChannelCurrencyDepositManagerService) beanFactory.getBean("channelDepositAlipayManagerService");
			break;
		case "ALIPAY":
			service = (ChannelCurrencyDepositManagerService) beanFactory.getBean("channelDepositAlipayManagerService");
			break;
		default:
			log.error("无效充值管理渠道:{}", channelType);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
		return service;
	}

}
