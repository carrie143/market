package com.gop.mode.vo;

public class UnauthorizedMessage extends BaseMessage {
	public UnauthorizedMessage() {
		setStatus("401");
		setMsg("无权限");
	}
}
