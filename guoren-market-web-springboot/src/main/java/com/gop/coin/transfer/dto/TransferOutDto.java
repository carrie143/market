package com.gop.coin.transfer.dto;

import org.hibernate.validator.constraints.NotBlank;

import com.gop.mode.vo.BaseDto;

import lombok.Data;

@Data
public class TransferOutDto extends BaseDto{

	@NotBlank
	private String assetCode;

	@NotBlank
	private String address;
	
	@NotBlank
	private String memo;

}
