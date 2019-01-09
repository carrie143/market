package com.gop.currency.withdraw.gateway.factory;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.gop.currency.withdraw.gateway.service.GetWayPayService;
import com.gop.domain.enums.WithdrawCurrencyPayMode;

import lombok.extern.slf4j.Slf4j;

/**
 * 单例工厂用以生成对用的渠道service
 * 
 * @author Administrator
 *
 */
@Slf4j
@Component
public class ChannelFactory implements ApplicationContextAware {

	static private ApplicationContext applicationContext;

	static private ChannelFactory factory;

	private static Object lock = new Object();

	private ChannelFactory() {
		super();
	}

	public static ChannelFactory getInstance() {
		synchronized (lock) {
			if (factory == null) {
				factory = new ChannelFactory();
			}
			return factory;
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ChannelFactory.applicationContext = applicationContext;

	}

	public GetWayPayService getPayChannel(WithdrawCurrencyPayMode payMode) {

		Object instance = null;
		switch (payMode) {
		case OKPAY:
			instance = applicationContext.getBean("okPayService");
			break;
		case SUPERPAY:
			instance = applicationContext.getBean("cibPayService");
			break;
		case ULPAY:
			instance = applicationContext.getBean("ulPayService");
			break;

		case OFFLINE:
			instance = applicationContext.getBean("offineLineService");
			break;
		default:
			log.info("没有对应的渠道信息: " + payMode);
			break;
		}
		return (GetWayPayService) instance;
	}

}
