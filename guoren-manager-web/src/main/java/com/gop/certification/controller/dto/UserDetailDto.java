package com.gop.certification.controller.dto;

import java.util.List;

import com.gop.domain.UserBasicInfo;
import com.gop.domain.UserIdentification;
import com.gop.domain.UserResidence;

import lombok.Data;

@Data
public class UserDetailDto {
	
	private UserInfoDto userInfoDto;
	private UserBasicInfo userBasicInfo;
	private UserIdentification userIdentification;
	private UserResidence userResidence;
	private List<UserIdentification> userIdentifications;
	private List<UserResidence> userResidences;

}
