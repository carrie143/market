package com.gop.currency.transfer.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.gop.domain.enums.UserAccountChannelType;
import com.gop.mode.vo.BaseDto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DepositDto extends BaseDto{
	
	@NotNull
	private Integer accountId;
	
	@NotNull
	private UserAccountChannelType channelType;
	
	@NotNull
	private BigDecimal amount;
	
	@NotNull
	private String assetCode;
	
	@NotNull
	private String memo;
}
