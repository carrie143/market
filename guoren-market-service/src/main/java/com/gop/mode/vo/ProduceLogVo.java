package com.gop.mode.vo;

import com.alibaba.fastjson.JSONObject;
import com.gop.domain.enums.SendMessageStatus;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ProduceLogVo {

	private int tablenNum;
	private Long id;
	
	@NonNull
	private String exchangeName;
	@NonNull
	private String key;
	@NonNull
	private Object message;
	@NonNull
	private SendMessageStatus status = SendMessageStatus.WAIT;

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}
