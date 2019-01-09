package com.gop.user.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.gop.domain.enums.AdministratorsRole;
import com.gop.mode.vo.BaseDto;

import lombok.Data;

@Data
public class CreateAdministratorDto extends BaseDto {
	
	@NotBlank
	private String account;

	@NotBlank
	private String password;

	@NotBlank
	private String userName;
	@NotNull
	private Integer roleId;
}
