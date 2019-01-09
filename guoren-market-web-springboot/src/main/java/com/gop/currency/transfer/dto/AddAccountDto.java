package com.gop.currency.transfer.dto;

import javax.validation.constraints.NotNull;

import com.gop.mode.vo.BaseDto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AddAccountDto extends BaseDto{
	@NotNull
	private String channelType;
	
	@NotNull
	private String channelAccountNo;
	
	@NotNull
	private String channelAccountName;
	
}
