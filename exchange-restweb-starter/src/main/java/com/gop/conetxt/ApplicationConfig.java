package com.gop.conetxt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix="gade")
public class ApplicationConfig {
	private String environment;
}
