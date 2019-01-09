package com.gop.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "mail")
@Data
public class MailConfig {

  private String toUser;
  private String reportSubject;
  private String reportText;
  private String reportRoot;
  private String reportDownload;

}
