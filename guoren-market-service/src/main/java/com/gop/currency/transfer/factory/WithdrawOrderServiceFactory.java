package com.gop.currency.transfer.factory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gop.code.consts.CommonCodeConst;
import com.gop.currency.transfer.service.ChannelCurrencyWithdrawOrderService;
import com.gop.exception.AppException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WithdrawOrderServiceFactory {
	
	@Autowired
	private BeanFactory beanFactory;
	
	public ChannelCurrencyWithdrawOrderService getService(String channelType){
		ChannelCurrencyWithdrawOrderService service = null;
		switch (channelType) {
		case "OKPAY":
			service = (ChannelCurrencyWithdrawOrderService) beanFactory.getBean("withdrawOrderOkpayServiceImpl");
			break;
		case "BANK":
			service = (ChannelCurrencyWithdrawOrderService) beanFactory.getBean("withdrawOrderBankServiceImpl");
			break;
		default:
			log.error("无效用户提现渠道");
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
		return service;
		
	}
	

}
