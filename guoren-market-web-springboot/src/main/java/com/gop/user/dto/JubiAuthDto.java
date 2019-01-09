package com.gop.user.dto;


import org.hibernate.validator.constraints.NotBlank;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JubiAuthDto {

	@NotBlank
	private String assetCode;
	
	@NotBlank
	private String assetCodeAmount;
}
