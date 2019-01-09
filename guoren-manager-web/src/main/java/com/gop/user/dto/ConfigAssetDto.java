package com.gop.user.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.gop.domain.enums.AssetStatus;
import com.gop.domain.enums.CurrencyType;

import lombok.Data;

@Data
public class ConfigAssetDto {
	@NotBlank
	private String assetCode;
	@NotNull
	private CurrencyType currencyType;
	@NotNull
	private AssetStatus status;
	@NotBlank
	private String name;
	@NotNull
	private BigDecimal supplyAmount;
	@NotNull
	private BigDecimal totalAmount;
	@NotNull
	private Integer minPrecision;
//	@NotBlank
	private String description;
//	@NotBlank
	private String webUrl;
}
