package com.gop.currency.withdraw.gateway.service.ulpay.dto.body;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import com.gop.currency.withdraw.gateway.service.ulpay.dto.body.detail.RequestBatchDetail;
import com.gop.currency.withdraw.gateway.service.ulpay.dto.body.sum.RequestBatchSum;


/**
 * 批量代付请求对象
 * @author wujh
 *
 */
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)  
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlType(name = "BODY", propOrder={"transSum", "transDetails"})
public class RequestBatchBody{
	
	// 请求明细汇总信息
	@XmlElement(name="TRANS_SUM")
	private RequestBatchSum transSum;
	// 明细列表信息
	@XmlElementWrapper(name = "TRANS_DETAILS")
	@XmlElement(name="TRANS_DETAIL")
	private List<RequestBatchDetail> transDetails;
	public RequestBatchSum getTransSum() {
		return transSum;
	}
	public void setTransSum(RequestBatchSum transSum) {
		this.transSum = transSum;
	}
	public List<RequestBatchDetail> getTransDetails() {
		return transDetails;
	}
	public void setTransDetails(List<RequestBatchDetail> transDetails) {
		this.transDetails = transDetails;
	}
	@Override
	public String toString() {
		return "RequestBatchBody [transSum=" + transSum + ", transDetails="
				+ transDetails + "]";
	}
}