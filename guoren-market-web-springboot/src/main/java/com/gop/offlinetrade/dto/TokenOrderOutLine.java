package com.gop.offlinetrade.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class TokenOrderOutLine {
	private BigDecimal num;
	private String assetName;
	private int uid;
    private Date createTime;
	private String userName;
}
