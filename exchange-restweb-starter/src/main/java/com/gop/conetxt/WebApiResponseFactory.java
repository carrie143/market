package com.gop.conetxt;

import java.util.Locale;

import com.gop.web.base.model.WebApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebApiResponseFactory {

	private MsgFactory msgFactory;

	public MsgFactory getMsgFactory() {
		return msgFactory;
	}

	public void setMsgFactory(MsgFactory msgFactory) {
		this.msgFactory = msgFactory;
	}

	public WebApiResponse get(String code, String[] args, Object data, Locale locale) {

		WebApiResponse webApiResponse = new WebApiResponse();
		webApiResponse.setCode(code);
		String msg = null;
		try {
			msg = msgFactory.get(code, args, locale);
		} catch (Exception e) {
			log.error("根据code码发现返回值异常code:{},local:{},e:{}", code, locale.toString(), e.getMessage());
		}
		webApiResponse.setMsg(msg);
		webApiResponse.setData(data);
		return webApiResponse;
	}

	public WebApiResponse get(String code, Object data, String message) {

		WebApiResponse webApiResponse = new WebApiResponse();
		webApiResponse.setCode(code);
		webApiResponse.setMsg(message);
		webApiResponse.setData(data);
		return webApiResponse;
	}

}
