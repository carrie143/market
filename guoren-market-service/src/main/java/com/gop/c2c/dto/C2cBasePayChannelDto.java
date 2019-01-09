package com.gop.c2c.dto;

import com.gop.domain.enums.C2cPayType;

import lombok.Builder;
import lombok.Data;

@Data
public class C2cBasePayChannelDto {
	//支付方式
	private C2cPayType c2cPayType;
}
