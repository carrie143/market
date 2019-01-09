package com.gop.mapper.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.TokenOrderState;

import lombok.Data;

@Data
public class OrderRankOfLastBoundDto {
	private Integer id;
	private Integer uid;
	private String coinName;
	private BigDecimal num;
	private TokenOrderState state;
	private Date boundTime;
}
