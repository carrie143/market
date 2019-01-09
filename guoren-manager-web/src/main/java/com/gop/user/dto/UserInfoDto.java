package com.gop.user.dto;

import java.util.Date;

import com.gop.domain.User;

import lombok.Data;

@Data
public class UserInfoDto {
	
	Integer uid;
	
	String phone;
	
	String email;

	String name;
	
	Date createDate;
	
	String ip;
	
	String cardType;
	
	String cardNo;
	
	
	public UserInfoDto(User user){
		this.setUid(user.getUid());
		this.setPhone(user.getMobile());
		this.setEmail(user.getEmail());
		this.setName(user.getFullname());
		this.setCreateDate(user.getCreateDate());
		this.setIp(user.getCreateip());
	}
}
