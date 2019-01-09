package com.gop.c2c.dto;

import com.gop.domain.enums.AuthLevel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class C2cTransactionInfoDto {

	//uid
	private Integer uid;
	//昵称
	private String nickName;
	//手机号
	private String phone;
	//认证等级
	private AuthLevel authLevel;
	//广告次数
	private Integer totalCount;
	//鼓励次数
	private Integer encourageCount;
	
}
