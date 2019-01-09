package com.gop.currency.withdraw.gateway.service.ulpay.dto.body.detail;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 代付结果查询
 *
 */
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)  
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlType(name = "TRANS_DETAIL", propOrder={"querySn", "queryDate", "queryRemark"})
public class RequestQueryDetail {
	@XmlElement(name="QUERY_SN")
	private String querySn;
	@XmlElement(name="QUERY_DATE")
	private String queryDate;
	@XmlElement(name="QUERY_REMARK")
	private String queryRemark;
	public String getQuerySn() {
		return querySn;
	}
	public void setQuerySn(String querySn) {
		this.querySn = querySn;
	}
	public String getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}
	public String getQueryRemark() {
		return queryRemark;
	}
	public void setQueryRemark(String queryRemark) {
		this.queryRemark = queryRemark;
	}
	@Override
	public String toString() {
		return "RequestQueryDetail [querySn=" + querySn + ", queryDate="
				+ queryDate + ", queryRemark=" + queryRemark + "]";
	}
}

