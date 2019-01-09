package com.gop.authentication.dto;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.gop.mode.vo.BaseDto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class IdentityAuthenticationDto extends BaseDto {

	@NotBlank
	private String countryId;

	@NotBlank
	private String country;

	@NotBlank
	private String cardType;

	// 1.5.1为必须
	@NotBlank
	private String cardNo;

	// 1.5.1为必须
	@NotBlank
	private Date expiredDate;

	@NotBlank
	private String cardPhoto;

	@NotBlank
	private String cardHandhold;
	
	// 非必须
	private String cardTranslate;

	// 1.5.1为必须
	@NotBlank
	private String cardBack;
}
