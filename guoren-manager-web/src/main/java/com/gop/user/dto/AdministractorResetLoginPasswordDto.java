package com.gop.user.dto;


import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class AdministractorResetLoginPasswordDto {
	
	@NotNull
	private Integer adminId;
	
	@NotBlank
	private String loginPassword;

}
