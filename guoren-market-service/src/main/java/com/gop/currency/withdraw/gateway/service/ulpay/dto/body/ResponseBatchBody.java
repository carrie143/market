package com.gop.currency.withdraw.gateway.service.ulpay.dto.body;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import com.gop.currency.withdraw.gateway.service.ulpay.dto.body.detail.ResponseBatchDetail;


/**
 * 批量代付交易响应对象
 * @author wujh
 *
 */
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)  
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlType(name = "BODY", propOrder={"retDetail"})
public class ResponseBatchBody{
	// 返回明细列表信息
	@XmlElementWrapper(name = "RET_DETAILS")
	@XmlElement(name="RET_DETAIL")
	private List<ResponseBatchDetail> retDetail;
	
	public List<ResponseBatchDetail> getRetDetail() {
		return retDetail;
	}
	public void setRetDetail(List<ResponseBatchDetail> retDetail) {
		this.retDetail = retDetail;
	}

	@Override
	public String toString() {
		return "ResponseBatchBody [retDetail=" + retDetail + "]";
	}
}
