package com.gop.authentication.dto;

import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import com.gop.domain.UserBasicInfo;
import com.gop.domain.UserResidence;

import lombok.Data;

@Data
@ToString
public class UserResidenceDto {
	private UserBasicInfo userBasicInfo;
	private UserResidence userResidence;
	private List<UserResidence> userResidenceList = new ArrayList<>();
}
