package com.gop.user.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.gop.mode.vo.BaseDto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDto extends BaseDto{
	
	private Integer userId;
	
	private Integer invitId;
	
	private String nickname;
	
	private String accountNo;
	
	private String lang;

	@NotBlank
	private String loginPassword;
	
	private String payPassword;
	//券商id
	private String inviteCode;
	
}
