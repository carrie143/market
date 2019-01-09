package com.gop.c2c.dto;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class C2cBankInfoDto extends C2cBasePayChannelDto{
	//银行
	 @NotBlank
	private String bank;
	//支行
	private String subBank;
	//姓名
	 @NotBlank
	private String name;
	//银行卡号
	 @NotBlank
	private String acnumber;

}
