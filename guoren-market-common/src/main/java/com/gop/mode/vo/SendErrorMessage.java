package com.gop.mode.vo;

import com.alibaba.fastjson.JSONObject;

public class SendErrorMessage extends BaseMessage {
	
	private JSONObject data;

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	public SendErrorMessage() {
		this.setStatus("400");
		this.setMsg("error");
	}
	
	public SendErrorMessage(JSONObject jsonObject){
		super();
		this.setStatus("400");
		this.setMsg("error");
		this.data = jsonObject;
	}

}
