package com.gop.authentication.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.gop.domain.UserIdentification;
import com.gop.mode.vo.BaseDto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserAuthLevel0Dto extends BaseDto {
	@NotNull

	private UserBasicInfoDto userBasicInfoDto;
	@NotNull

	private IdentificationDto identificationDto;
	
	private List<UserIdentification> userIdentificationList = new ArrayList<>();

}
