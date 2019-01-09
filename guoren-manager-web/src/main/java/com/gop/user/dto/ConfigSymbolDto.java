package com.gop.user.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.gop.domain.enums.SymbolStatus;

import lombok.Data;

@Data
public class ConfigSymbolDto {
	@NotBlank
	private String symbol;
//	@NotBlank
	private String priceAsset;
//	@NotBlank
	private String tradeAsset;
	@NotNull
	private SymbolStatus status;
	@NotBlank
	private String title;
	@NotBlank
	private String name;
//	@NotBlank
	private String description;
	@NotNull
	private Integer minPrecision1;
	@NotNull
	private Integer minPrecision2;
	@NotNull
	private BigDecimal minAmount1;
	@NotNull
	private BigDecimal minAmount2;
	@NotNull
	private BigDecimal maxAmount1;
	@NotNull
	private BigDecimal maxAmount2;

}
