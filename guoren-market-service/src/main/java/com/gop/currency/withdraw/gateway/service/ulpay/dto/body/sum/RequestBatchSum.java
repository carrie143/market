package com.gop.currency.withdraw.gateway.service.ulpay.dto.body.sum;


import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)  
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlType(name = "TRANS_SUM", propOrder={"businessCode", "merchantId", "sendTime", "sendDt", "totalItem", "totalSum", "retUrl", "notifyUrl"})
public class RequestBatchSum {
	// 业务代码
	@XmlElement(name="BUSINESS_CODE")
	private String businessCode;
	// 商户编号
	@XmlElement(name="MERCHANT_ID")
	private String merchantId;
	// 商户请求时间
	@XmlElement(name="SEND_TIME")
	private String sendTime;
	// 商户请求日期
	@XmlElement(name="SEND_DT")
	private String sendDt;
	// 总笔数
	@XmlElement(name="TOTAL_ITEM")
	private String totalItem;
	// 交易总金额
	@XmlElement(name="TOTAL_SUM")
	private String totalSum;
	// 同步通知路劲
	@XmlElement(name="RET_URL")
	private String retUrl;
	// 异步通知路径
	@XmlElement(name="NOTIFY_URL")
	private String notifyUrl;
	public String getBusinessCode() {
		return businessCode;
	}
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getSendDt() {
		return sendDt;
	}
	public void setSendDt(String sendDt) {
		this.sendDt = sendDt;
	}
	public String getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(String totalItem) {
		this.totalItem = totalItem;
	}
	public String getTotalSum() {
		return totalSum;
	}
	public void setTotalSum(String totalSum) {
		this.totalSum = totalSum;
	}
	public String getRetUrl() {
		return retUrl;
	}
	public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	@Override
	public String toString() {
		return "RequestBatchSum [businessCode=" + businessCode
				+ ", merchantId=" + merchantId + ", sendTime=" + sendTime
				+ ", sendDt=" + sendDt + ", totalItem=" + totalItem
				+ ", totalSum=" + totalSum + ", retUrl=" + retUrl
				+ ", notifyUrl=" + notifyUrl + "]";
	}
}
