package com.gop.match.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.TradeMatchResult;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchRecordDto {

	private String price;

	private String num;

	private Date createTime;

}
