package com.gop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@SpringBootApplication
@MapperScan("com.gop.mapper")
@PropertySources({ @PropertySource("classpath:sms.properties")})
public class GuorenManagerWebSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuorenManagerWebSpringbootApplication.class, args);
	}
}
