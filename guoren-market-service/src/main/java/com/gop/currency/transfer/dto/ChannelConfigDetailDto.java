package com.gop.currency.transfer.dto;

import java.math.BigDecimal;

import com.gop.domain.ChannelAlipayGlobalConfig;
import com.gop.domain.ChannelOkpayGlobalConfig;

import lombok.Data;

@Data
public class ChannelConfigDetailDto {

	// @ApiModelProperty("配置的id,新增配置的时候为不传值")
	Integer id;

	// @ApiModelProperty("最小充值金额")
	BigDecimal minAmount;

	// @ApiModelProperty("最大充值金额")
	BigDecimal maxAmount;

	// @ApiModelProperty("配置状态")
	String status;

	public ChannelConfigDetailDto(ChannelAlipayGlobalConfig config) {

		this.id = config.getId();
		this.minAmount = config.getMinAmount();
		this.maxAmount = config.getMaxAmount();
		this.status = config.getStatus().toString();
	}

	public ChannelConfigDetailDto(ChannelOkpayGlobalConfig config) {
		this.id = config.getId();
		this.minAmount = config.getMinAmount();
		this.maxAmount = config.getMaxAmount();
		this.status = config.getStatus().toString();

	}

}
