package com.gop.web.base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gop.conetxt.ApplicationConfig;
import com.gop.conetxt.EnvironmentContxt;
import com.gop.conetxt.MsgFactory;
import com.gop.conetxt.WebApiResponseFactory;

@Configuration
@EnableConfigurationProperties(ApplicationConfig.class)
public class ApiResponseConfig {

	@Autowired
	private ApplicationConfig applicationConfig;

	@ConditionalOnMissingBean(WebApiResponseFactory.class)
	@Bean("webApiResponseFactory")
	public WebApiResponseFactory createWebApiResponseFactory() {

		WebApiResponseFactory webApiResponseFactory = new WebApiResponseFactory();
		MsgFactory msgFactory = new MsgFactory();
		msgFactory.setBaseNames(new String[] { "SysCode" });
		webApiResponseFactory.setMsgFactory(msgFactory);
		return webApiResponseFactory;
	}

	@ConditionalOnMissingBean(EnvironmentContxt.class)
	@Bean("environmentContxt")
	public EnvironmentContxt createEnvironmentContxt() {
		MsgFactory msgFactory = new MsgFactory();
		msgFactory.setBaseNames(new String[] { "message" });
		EnvironmentContxt environmentContxt = new EnvironmentContxt();
		environmentContxt.setApplicationConfig(applicationConfig);
		environmentContxt.setMsgFactory(msgFactory);
		return environmentContxt;
	}
}
