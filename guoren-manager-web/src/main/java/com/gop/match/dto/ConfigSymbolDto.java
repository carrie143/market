package com.gop.match.dto;

import com.gop.domain.enums.ConfigSymbolType;

import lombok.Data;

@Data
public class ConfigSymbolDto {
	private Integer id;
	private String symbol;
	//key使用枚举
	private ConfigSymbolType profileKey;
	private String profileValue;
	

}
