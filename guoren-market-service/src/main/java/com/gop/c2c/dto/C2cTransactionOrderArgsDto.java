package com.gop.c2c.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.gop.domain.enums.C2cPayType;

import lombok.Data;

@Data
public class C2cTransactionOrderArgsDto {
	private String advertId;

	private C2cPayType payType;
	private BigDecimal number;
	private BigDecimal money;
	private String remark;
}
