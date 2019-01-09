package com.gop.user.dto;

import java.util.Date;

import com.gop.domain.enums.UserMessageCategory;
import com.gop.mode.vo.BaseDto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UnMessageDto extends BaseDto {
	private UserMessageCategory UserMessageCategory;

	private String status;

	private String content;

	private Date createDate;

	private Integer uid;
}
