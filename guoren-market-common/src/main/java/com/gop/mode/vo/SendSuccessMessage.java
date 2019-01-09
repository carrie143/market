/*
 * 文件名：SendCodeMessage.java 版权：Copyright by www.guoren.com 描述： 修改人：汪洋 修改时间：2015年12月8日 跟踪单号： 修改单号：
 * 正确相应返回的mnssage
 */

package com.gop.mode.vo;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class SendSuccessMessage extends BaseMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5826301203992365246L;
	private JSONObject data;

	public SendSuccessMessage() {
		super();
	}

	public SendSuccessMessage(JSONObject data) {
		super();
		this.data = data;
	}

	public SendSuccessMessage(Object data) {

		Object obj = JSONObject.toJSON(data);

		if (obj instanceof Map) {
			this.data = (JSONObject) obj;
		} else {
			throw new IllegalArgumentException("消息非json格式!");
		}

	}

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

}
