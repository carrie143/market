package com.gop.authentication.dto;

import org.hibernate.validator.constraints.NotBlank;

import com.gop.mode.vo.BaseDto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BasicInfoAuthenticationDto extends BaseDto{
	
	@NotBlank
	private String firstName;
	
//	@NotBlank
	private String middleName;
	
	@NotBlank
	private String lastName;
	
	@NotBlank
	private String gender;
	
	@NotBlank
	private String birthday;
	
	@NotBlank
	private String countryId;
	
	@NotBlank
	private String country;

}
