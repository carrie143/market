package com.gop.currency.withdraw.gateway.service.ulpay.dto;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.gop.currency.withdraw.gateway.service.ulpay.dto.body.RequestQueryBody;
import com.gop.currency.withdraw.gateway.service.ulpay.dto.info.RequestQueryInfo;


/**
 * 代付结果查询请求
 * @author wujh
 *
 */
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)  
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlType(name = "AIPG", propOrder={"info", "body"})
@XmlRootElement(name="AIPG")
public class RequestQuery {
	// 报文头
	@XmlElement(name="INFO")
	private RequestQueryInfo info;
	// 请求报文体
	@XmlElement(name="BODY")
	private RequestQueryBody body;
	public RequestQueryInfo getInfo() {
		return info;
	}
	public void setInfo(RequestQueryInfo info) {
		this.info = info;
	}
	public RequestQueryBody getBody() {
		return body;
	}
	public void setBody(RequestQueryBody body) {
		this.body = body;
	}
	@Override
	public String toString() {
		return "Request200000 [info=" + info + ", body=" + body + "]";
	}
}
