package com.gop.asset.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserBankDto {
	
	String channelName;
	
	String userName;
	
	String channelAccountNo;
	
	Date createDate;
	
	String ip;

}
