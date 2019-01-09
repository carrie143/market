package com.gop.currency.withdraw.gateway.service.ulpay.dto.body;


import java.util.List;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import com.gop.currency.withdraw.gateway.service.ulpay.dto.body.detail.ResponseQueryDetail;
import com.gop.currency.withdraw.gateway.service.ulpay.dto.body.sum.ResponseQuerySum;

/**
 * 代付接口查询
 * @author wujh
 *
 */
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)  
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlType(name = "BODY", propOrder={"transSum", "transDetails"})
public class ResponseQueryBody {
	// 请求明细汇总信息
	@XmlElement(name="QUERY_TRANS")
	private ResponseQuerySum transSum;
	// 明细列表信息
	@XmlElementWrapper(name = "RET_DETAILS")
	@XmlElement(name="RET_DETAIL")
	private List<ResponseQueryDetail> transDetails;
	public ResponseQuerySum getTransSum() {
		return transSum;
	}
	public void setTransSum(ResponseQuerySum transSum) {
		this.transSum = transSum;
	}
	public List<ResponseQueryDetail> getTransDetails() {
		return transDetails;
	}
	public void setTransDetails(List<ResponseQueryDetail> transDetails) {
		this.transDetails = transDetails;
	}
	@Override
	public String toString() {
		return "ResponseQueryBody [transSum=" + transSum + ", transDetails="
				+ transDetails + "]";
	}
}
