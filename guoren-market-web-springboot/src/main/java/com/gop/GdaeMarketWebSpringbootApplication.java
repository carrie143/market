package com.gop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@MapperScan("com.gop.mapper")
//@PropertySources({ @PropertySource("classpath:apiresource.properties"), @PropertySource("classpath:common.properties"),
//		@PropertySource("classpath:rabbitmqConfig.properties")})
//@ImportResource(locations = { "classpath:applicationContext-job.xml" })
@PropertySources({ @PropertySource("classpath:sms.properties")})
@EnableCaching
public class GdaeMarketWebSpringbootApplication {

	public static void main(String[] args) {

		SpringApplication.run(GdaeMarketWebSpringbootApplication.class, args);
	}
}
