package com.gop.user.dto;

import org.hibernate.validator.constraints.NotBlank;

import com.gop.mode.vo.BaseDto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JubiUserDto extends BaseDto{
	
	@NotBlank
	private String randomkey;
	
	@NotBlank
	private String jubiAccount;
	
	@NotBlank
	private String loginPassword;
	
	@NotBlank
	private String payPassword;
}
