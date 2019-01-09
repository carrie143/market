package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.QuantFundConfigType;

import lombok.Data;

@Data
public class QuantFundConfig {
	private Integer id;

	private String fundAssetCode;

	private QuantFundConfigType profileKey;

	private String profileValue;

	private Date createDate;

	private Date updateDate;
}