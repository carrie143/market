package com.gop.coin.transfer.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import com.gop.domain.ChannelCoinAddressDeposit;
import com.gop.mode.vo.BaseDto;

import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferInAddressDto extends BaseDto{

	@NotBlank
	private String assetCode;
	
	@NotBlank
	private String address;


}
