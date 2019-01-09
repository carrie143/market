package com.gop.domain;

import com.gop.domain.enums.ConfigEmailStatus;

import lombok.Data;

@Data
public class ConfigEmail {
	private Integer id;

	private String mailUsername;

	private String mailPassword;

	private String mailHost;

	private String mailSubject;

	private Integer sendCount;

	private ConfigEmailStatus status;

	private String createDate;

	private String updateDate;

}