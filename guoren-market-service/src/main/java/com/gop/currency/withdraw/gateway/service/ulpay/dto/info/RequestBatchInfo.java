package com.gop.currency.withdraw.gateway.service.ulpay.dto.info;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 请求报文头
 * @author wujianhua
 *
 */
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)  
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlType(name = "INFO", propOrder={"trxCode", "version", "dataType", "level", "userName", "userPass", "reqSn", "signedMsg","mercId"})
public class RequestBatchInfo {
	// 交易码
	@XmlElement(name="TRX_CODE")
	private String trxCode;
	// 报文版本号
	@XmlElement(name="VERSION")
	private String version;
	// 报文格式
	@XmlElement(name="DATA_TYPE")
	private String dataType;
	// 报文处理级别
	@XmlElement(name="LEVEL")
	private String level;
	// 请求用户名
	@XmlElement(name="USER_NAME")
	private String userName;
	// 请求用户密码
	@XmlElement(name="USER_PASS")
	private String userPass;
	// 交易批次号
	@XmlElement(name="REQ_SN")
	private String reqSn;
	// 签名信息
	@XmlElement(name="SIGNED_MSG")
	private String signedMsg;
	
	@XmlElement(name="MERCHANT_ID")
	private String mercId;
	public String getMercId() {
		return mercId;
	}
	public void setMercId(String mercId) {
		this.mercId = mercId;
	}
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
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPass() {
		return userPass;
	}
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	public String getReqSn() {
		return reqSn;
	}
	public void setReqSn(String reqSn) {
		this.reqSn = reqSn;
	}
	public String getSignedMsg() {
		return signedMsg;
	}
	public void setSignedMsg(String signedMsg) {
		this.signedMsg = signedMsg;
	}
	@Override
	public String toString() {
		return "RequestBatchInfo [trxCode=" + trxCode + ", version=" + version
				+ ", dataType=" + dataType + ", level=" + level + ", userName="
				+ userName + ", userPass=" + userPass + ", reqSn=" + reqSn
				+ ", signedMsg=" + signedMsg + "]";
	}
}