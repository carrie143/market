package com.gop.user.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ConfigSymbolProfileInitDto extends ConfigSymbolDto{
	@NotNull
	private Integer highlightNo; // ---高亮位数 5
}
