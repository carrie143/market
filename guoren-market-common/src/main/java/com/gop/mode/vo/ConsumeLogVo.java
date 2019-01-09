package com.gop.mode.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ConsumeLogVo {
	private String id;

	private String queueName;

	private Object message;

}
