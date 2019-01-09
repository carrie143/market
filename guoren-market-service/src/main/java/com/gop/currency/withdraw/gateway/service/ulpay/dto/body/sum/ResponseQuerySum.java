package com.gop.currency.withdraw.gateway.service.ulpay.dto.body.sum;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)  
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlType(name = "TRANS_SUM", propOrder={"querySn", "queryRemark"})

public class ResponseQuerySum {
	@XmlElement(name="QUERY_SN")
	private String querySn;
	@XmlElement(name="QUERY_REMARK")
	private String queryRemark;
	public String getQuerySn() {
		return querySn;
	}
	public void setQuerySn(String querySn) {
		this.querySn = querySn;
	}
	public String getQueryRemark() {
		return queryRemark;
	}
	public void setQueryRemark(String queryRemark) {
		this.queryRemark = queryRemark;
	}
	@Override
	public String toString() {
		return "ResponseQuerySum [querySn=" + querySn + ", queryRemark="
				+ queryRemark + "]";
	}
}

