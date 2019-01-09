package com.gop.fork.dto;

import java.math.BigDecimal;

import com.gop.domain.ForkUserReceiveCandy;
import com.gop.domain.enums.ForkUserReceiveCandyStatus;
import com.gop.fork.enums.ForkUserReceiveCandyDtoStatus;

import lombok.Data;

@Data
public class ForkUserReceiveCandyDto {
	private ForkUserReceiveCandyDtoStatus candyDtoStatus;
}
