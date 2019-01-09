package com.gop.user.dto;

import com.gop.domain.enums.AuthLevel;
import com.gop.mode.vo.BaseDto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserSimpleInfoDto extends BaseDto{

	private Integer uid;

	private String userAccount;

	private String fullName;

	private String nickName;
	
	private Integer brokerId;
	private String token;
	
	//1.5.1版本添加
	private AuthLevel authLevel;
	

}
