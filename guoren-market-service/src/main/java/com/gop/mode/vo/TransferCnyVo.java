package com.gop.mode.vo;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class TransferCnyVo {
	private Integer id;
	private String serialNumber;
	private Date arriveTime;
	private BigDecimal money;
	private String name;
	private String bankName;
	private String bankNo;
	private String postscript;
    private Integer uid;
}
