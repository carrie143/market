package com.gop.user.dto;


import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class AdministractorLoginPasswordDto {
	
	@NotBlank
	String loginPassword;

}
