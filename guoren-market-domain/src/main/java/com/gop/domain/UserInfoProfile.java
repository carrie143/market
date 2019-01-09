package com.gop.domain;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class UserInfoProfile {
	private Integer id;

	private Integer uid;

	private String profileGroup;

	private String profileKey;

	private String profileIndex;

	private String dataType;

	private String profileValue;

	private Date createDate;

	private Date updateDate;

}