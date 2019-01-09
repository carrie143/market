package com.gop.currency.transfer.factory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gop.currency.transfer.service.ChannelUserAccountBaseService;
import com.gop.domain.enums.UserAccountChannelType;

@Component
public class ChannelUserAccountServiceFactory {

	@Autowired
	private BeanFactory beanFactory;

	public ChannelUserAccountBaseService getUserAccountService(UserAccountChannelType channelType) {

		ChannelUserAccountBaseService accountService = null;
		switch (channelType) {
		case BANK:
			accountService = beanFactory.getBean("userBankServiceImpl", ChannelUserAccountBaseService.class);
			break;
		case ALIPAY:
			accountService = beanFactory.getBean("userAlipayServiceImpl", ChannelUserAccountBaseService.class);
			break;
		case OKPAY:
			accountService = beanFactory.getBean("userOkpayServiceImpl", ChannelUserAccountBaseService.class);
			break;
		default:
			throw new IllegalArgumentException();
		}
		return accountService;
	}

}
