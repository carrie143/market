package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.ConfigAssetType;

import lombok.Data;

@Data
public class ConfigAssetProfile {
	private Integer id;

	private String assetCode;

	// 改枚举类型与dto通用
	private ConfigAssetType profileKey;

	private String profileValue;

	private Date createDate;

	private Date updateDate;

}