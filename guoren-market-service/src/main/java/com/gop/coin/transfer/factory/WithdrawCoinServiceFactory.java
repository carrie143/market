package com.gop.coin.transfer.factory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gop.coin.transfer.service.WithdrawCoinService;

@Component
public class WithdrawCoinServiceFactory {
	
	@Autowired
	private BeanFactory beanFactory;
	public WithdrawCoinService getService(String assetCode){
		WithdrawCoinService service = null;
		switch (assetCode) {
		case "GOP":
			service = (WithdrawCoinService) beanFactory.getBean("withdrawGopService");
			break;
		
		case "ACT":
			service = (WithdrawCoinService) beanFactory.getBean("withdrawActService");
			break;
		default:
			service = (WithdrawCoinService) beanFactory.getBean("withdrawSelfService");
			break;
		}
		return service;
	}

}
