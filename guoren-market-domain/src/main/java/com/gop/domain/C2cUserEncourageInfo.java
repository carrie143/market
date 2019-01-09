package com.gop.domain;

import java.util.Date;

import lombok.Data;

@Data
public class C2cUserEncourageInfo {
	private Integer id;

	private Integer uid;

	private Integer encouragedCount;

	private Date createDate;

	private Date updateDate;

}