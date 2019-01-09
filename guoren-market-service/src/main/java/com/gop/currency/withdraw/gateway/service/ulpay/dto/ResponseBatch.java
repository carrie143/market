package com.gop.currency.withdraw.gateway.service.ulpay.dto;


import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.gop.currency.withdraw.gateway.service.ulpay.dto.body.ResponseBatchBody;
import com.gop.currency.withdraw.gateway.service.ulpay.dto.info.ResponseBatchInfo;

/**
 * 批量代付交易响应对象
 * @author wujh
 *
 */
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)  
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlType(name = "AIPG", propOrder={"info", "body"})
@XmlRootElement(name="AIPG")
public class ResponseBatch{
	// 响应报文头
	@XmlElement(name="INFO")
	private ResponseBatchInfo info;
	// 响应报文体信息
	@XmlElement(name="BODY")
	private ResponseBatchBody body;
	public ResponseBatchInfo getInfo() {
		return info;
	}
	public void setInfo(ResponseBatchInfo info) {
		this.info = info;
	}
	public ResponseBatchBody getBody() {
		return body;
	}
	public void setBody(ResponseBatchBody body) {
		this.body = body;
	}
	@Override
	public String toString() {
		return "ResponseBatch [info=" + info + ", body=" + body + "]";
	}
}
