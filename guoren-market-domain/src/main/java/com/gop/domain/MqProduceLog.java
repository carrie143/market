package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.SendMessageStatus;

import lombok.Data;
import lombok.NonNull;

@Data
public class MqProduceLog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1556747089723521756L;
	@NonNull
	private Long id;
	@NonNull
	private String exchangeName;
	@NonNull
	private String routerKey;
	@NonNull
	private String message;
	@NonNull
	private SendMessageStatus status;

	@NonNull
	private Date createTime;

	public MqProduceLog() {
		super();
	}

	public MqProduceLog(Long id, String exchangeName, String routerKey, String message, SendMessageStatus status) {
		super();
		this.id = id;
		this.exchangeName = exchangeName;
		this.routerKey = routerKey;
		this.message = message;
		this.status = status;
	}
}