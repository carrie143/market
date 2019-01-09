package com.gop.currency.transfer.factory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gop.code.consts.CommonCodeConst;
import com.gop.currency.transfer.service.ChannelCurrencyDepositOrderService;
import com.gop.domain.enums.UserAccountChannelType;
import com.gop.exception.AppException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DepositOrderServiceFactory {
	
	@Autowired
	private BeanFactory beanFactory;
	
	public ChannelCurrencyDepositOrderService getService(UserAccountChannelType channelType){
		ChannelCurrencyDepositOrderService service = null;
		switch (channelType) {
		case BANK:
			service = (ChannelCurrencyDepositOrderService) beanFactory.getBean("depositBankOrderServiceImpl");
			break;
		case ALIPAY:
			service = (ChannelCurrencyDepositOrderService) beanFactory.getBean("depositAlipayOrderServiceImpl");
			break;
		case OKPAY:
			service = (ChannelCurrencyDepositOrderService) beanFactory.getBean("depositOkpayOrderServiceImpl");
			break;
		default:
			log.error("无效用户充值渠道");
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
		return service;
	}

}
