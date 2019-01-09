package com.gop.api.cloud.config;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhangjinyang on 2018/6/27.
 */
@Data
@ConfigurationProperties(prefix = "cloud.api")
public class ApiConfig {
  private String url;
  private String accesskey;
  private String secretkey;
}
