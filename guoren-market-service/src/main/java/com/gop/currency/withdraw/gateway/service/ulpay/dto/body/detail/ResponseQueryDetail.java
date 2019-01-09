package com.gop.currency.withdraw.gateway.service.ulpay.dto.body.detail;


import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)  
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlType(name = "TRANS_DETAIL", propOrder={"sn", "account", "accountName", "amount", "custUserid", "remark", 
		"retCode", "errMsg"})
public class ResponseQueryDetail {
	// 交易流水号
	@XmlElement(name="SN")
	private String sn;
	// 账号
	@XmlElement(name="ACCOUNT")
	private String account;
	// 账号名
	@XmlElement(name="ACCOUNT_NAME")
	private String accountName;
	// 金额
	@XmlElement(name="AMOUNT")
	private String amount;
	// 自定义用户号
	@XmlElement(name="CUST_USERID")
	private String custUserid;
	// 备注
	@XmlElement(name="REMARK")
	private String remark;
	// 结果码
	@XmlElement(name="RET_CODE")
	private String retCode;
	// 结果提示
	@XmlElement(name="ERR_MSG")
	private String errMsg;
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCustUserid() {
		return custUserid;
	}
	public void setCustUserid(String custUserid) {
		this.custUserid = custUserid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
		return "ResponseQueryDetail [sn=" + sn + ", account=" + account
				+ ", accountName=" + accountName + ", amount=" + amount
				+ ", custUserid=" + custUserid + ", remark=" + remark
				+ ", retCode=" + retCode + ", errMsg=" + errMsg + "]";
	}
}

