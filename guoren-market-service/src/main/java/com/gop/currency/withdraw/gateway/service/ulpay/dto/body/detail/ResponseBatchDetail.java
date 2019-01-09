package com.gop.currency.withdraw.gateway.service.ulpay.dto.body.detail;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 响应明细信息
 * @author wujh
 *
 */
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)  
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlType(name = "TET_DETAIL", propOrder={"sn", "retCode", "errMsg"})
public class ResponseBatchDetail {
	// 交易流水号
	@XmlElement(name="SN")
	private String sn;
	// 返回码
	@XmlElement(name="RET_CODE")
	private String retCode;
	// 返回信息
	@XmlElement(name="ERR_MSG")
	private String errMsg;
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	@Override
	public String toString() {
		return "ResponseBatchDetail [sn=" + sn + ", retCode=" + retCode
				+ ", errMsg=" + errMsg + "]";
	}
}

