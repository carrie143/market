package com.gop.mode.vo;


import lombok.Data;

/**
 * Created by Administrator on 2016/10/24.
 */
@Data
public class SuccessDateMessage extends BaseMessage {
	public SuccessDateMessage() {
		setMsg("success");
		setStatus("200");
	}

	private RealtimeClearResult data;
}
