package com.gop.currency.withdraw.gateway.service.ulpay.dto;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.gop.currency.withdraw.gateway.service.ulpay.dto.body.RequestBatchBody;
import com.gop.currency.withdraw.gateway.service.ulpay.dto.info.RequestBatchInfo;

/**
 * 批量代付请求对象
 *
 */
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)  
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlType(name = "AIPG", propOrder={"info", "body"})
@XmlRootElement(name="AIPG")
public class RequestBatch {
	// 报文头
	@XmlElement(name="INFO")
	private RequestBatchInfo info;
	// 请求报文体
	@XmlElement(name="BODY")
	private RequestBatchBody body;
	public RequestBatchInfo getInfo() {
		return info;
	}
	public void setInfo(RequestBatchInfo info) {
		this.info = info;
	}
	public RequestBatchBody getBody() {
		return body;
	}
	public void setBody(RequestBatchBody body) {
		this.body = body;
	}
	@Override
	public String toString() {
		return "RequestBatch [info=" + info + ", body=" + body + "]";
	}
}
