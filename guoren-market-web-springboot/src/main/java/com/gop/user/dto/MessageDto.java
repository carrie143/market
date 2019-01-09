package com.gop.user.dto;

import java.util.Date;

import com.gop.domain.UserMessage;
import com.gop.domain.enums.UserMessageCategory;
import com.gop.domain.enums.UserMessageStatus;
import com.gop.mode.vo.BaseDto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MessageDto extends BaseDto {

	private UserMessageCategory UserMessageCategory;

	private UserMessageStatus status;

	private String content;

	private Date createDate;

	private Integer uid;
	
	public MessageDto(UserMessage message){
		this.status=message.getStatus();
		this.content=message.getContent();
		this.uid=message.getUid();
		this.createDate=message.getCreateDate();
		this.UserMessageCategory=message.getCategory();
		
	}
}
