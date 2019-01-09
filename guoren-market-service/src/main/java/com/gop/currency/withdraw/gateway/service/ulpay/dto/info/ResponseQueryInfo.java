package com.gop.currency.withdraw.gateway.service.ulpay.dto.info;


import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 响应报文头
 */
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)  
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlType(name = "INFO", propOrder={"trxCode", "version", "dataType", "reqSn", "retCode", "errMsg", "signedMsg", "hzDt"})
public class ResponseQueryInfo {
	// 交易码
	@XmlElement(name="TRX_CODE")
	private String trxCode;
	// 报文版本号
	@XmlElement(name="VERSION")
	private String version;
	// 报文类型
	@XmlElement(name="DATA_TYPE")
	private String dataType;
	// 交易批次号
	@XmlElement(name="REQ_SN")
	private String reqSn;
	// 返回交易码
	@XmlElement(name="RET_CODE")
	private String retCode;
	// 返回错误信息
	@XmlElement(name="ERR_MSG")
	private String errMsg;
	// 签名信息
	@XmlElement(name="SIGNED_MSG")
	private String signedMsg;
	// 合纵支付平台日期
	@XmlElement(name="HZ_DT")
	private String hzDt;
	public String getTrxCode() {
		return trxCode;
	}
	public void setTrxCode(String trxCode) {
		this.trxCode = trxCode;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getReqSn() {
		return reqSn;
	}
	public void setReqSn(String reqSn) {
		this.reqSn = reqSn;
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
	public String getSignedMsg() {
		return signedMsg;
	}
	public void setSignedMsg(String signedMsg) {
		this.signedMsg = signedMsg;
	}
	public String getHzDt() {
		return hzDt;
	}
	public void setHzDt(String hzDt) {
		this.hzDt = hzDt;
	}
	@Override
	public String toString() {
		return "ResponseQueryInfo [trxCode=" + trxCode + ", version=" + version
				+ ", dataType=" + dataType + ", reqSn=" + reqSn + ", retCode="
				+ retCode + ", errMsg=" + errMsg + ", signedMsg=" + signedMsg
				+ ", hzDt=" + hzDt + "]";
	}
}
