package com.gop.certification.controller.dto;

import java.util.Date;

import com.gop.domain.User;
import com.gop.domain.enums.AuthLevel;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
public class UserInfoDto {
	private Integer uid;

	private String email;

	private Date createDate;

	private String createip;
	
	private String ipCity;

	private AuthLevel authLevel;

	public UserInfoDto(User user) {
		this.uid = user.getUid();
		this.email = user.getEmail();
		this.createDate = user.getCreateDate();
		this.createip = user.getCreateip();
		this.authLevel = user.getAuthLevel();
	}

}
