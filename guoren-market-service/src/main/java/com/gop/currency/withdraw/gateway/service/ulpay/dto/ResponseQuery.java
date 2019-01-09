package com.gop.currency.withdraw.gateway.service.ulpay.dto;


import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.gop.currency.withdraw.gateway.service.ulpay.dto.body.ResponseQueryBody;
import com.gop.currency.withdraw.gateway.service.ulpay.dto.info.ResponseQueryInfo;

/**
 * 批量代付响应接口
 *
 */
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)  
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlType(name = "AIPG", propOrder={"info", "body"})
@XmlRootElement(name="AIPG")
public class ResponseQuery {
	// 响应报文头
	@XmlElement(name="INFO")
	private ResponseQueryInfo info;
	// 响应报文体信息
	@XmlElement(name="BODY")
	private ResponseQueryBody body;
	public ResponseQueryInfo getInfo() {
		return info;
	}
	public void setInfo(ResponseQueryInfo info) {
		this.info = info;
	}
	public ResponseQueryBody getBody() {
		return body;
	}
	public void setBody(ResponseQueryBody body) {
		this.body = body;
	}
	@Override
	public String toString() {
		return "ResponseQuery [info=" + info + ", body=" + body + "]";
	}
}
