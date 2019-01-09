package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.ConfigSymbolType;

import lombok.Data;

@Data
public class ConfigSymbolProfile {
	private Integer id;

	private String symbol;
	private ConfigSymbolType profileKey;

	private String profileValue;

	private Date createDate;

	private Date updateDate;

}