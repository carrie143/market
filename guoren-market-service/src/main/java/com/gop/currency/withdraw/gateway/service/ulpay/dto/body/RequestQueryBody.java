package com.gop.currency.withdraw.gateway.service.ulpay.dto.body;


import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.gop.currency.withdraw.gateway.service.ulpay.dto.body.detail.RequestQueryDetail;


/**
 * 代付结果查询
 *
 */
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)  
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlType(name = "BODY", propOrder={"retDetail"})
public class RequestQueryBody {
	// 查询明细
	@XmlElement(name="QUERY_TRANS")
	private RequestQueryDetail retDetail;

	public RequestQueryDetail getRetDetail() {
		return retDetail;
	}

	public void setRetDetail(RequestQueryDetail retDetail) {
		this.retDetail = retDetail;
	}

	@Override
	public String toString() {
		return "RequestQueryDetail [retDetail=" + retDetail + "]";
	}
}
