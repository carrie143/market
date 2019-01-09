package com.gop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gop.util.TokenHelper;

@Configuration
public class CommonConfig {
	static public Integer DEFAULT_PAGE_SIZE = 10;
	static public Integer DEFAULT_PAGE_NO = 1;

	@Bean()
	public TokenHelper tokenHelper() {
		return new TokenHelper();
	}

}
