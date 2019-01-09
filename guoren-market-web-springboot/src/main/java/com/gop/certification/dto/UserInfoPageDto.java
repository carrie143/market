package com.gop.certification.dto;

import com.gop.domain.UserBasicInfo;
import com.gop.domain.UserIdentification;
import com.gop.domain.UserResidence;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserInfoPageDto {
	private String email;
	private UserBasicInfo userBasicInfo;
	private UserIdentification userIdentification;
	private UserResidence userResidence;
}
	
