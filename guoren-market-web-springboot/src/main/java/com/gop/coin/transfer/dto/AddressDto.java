package com.gop.coin.transfer.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.gop.domain.ChannelCoinAddressWithdraw;
import com.gop.mode.vo.BaseDto;

import lombok.Data;

@Data
public class AddressDto extends BaseDto{
	
	@NotNull
	private Integer addressid;

	@NotBlank
	private String assetCode;

	@NotBlank
	private String address;

	@NotBlank
	private String memo;
	
	@NotNull
	private Date updateDate;

	public AddressDto(ChannelCoinAddressWithdraw address) {

		this.addressid = address.getId();
		this.address = address.getCoinAddress();
		this.memo = address.getName();
		this.assetCode = address.getAssetCode();
		this.updateDate = address.getCreateDate();
	}

}
