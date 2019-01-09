package com.gop.currency.withdraw.gateway.service.ulpay.dto.body.detail;


import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 批量开户交易明细
 * @author wujh
 *
 */
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)  
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlType(name = "TRANS_DETAIL", propOrder={"sn", "eUserCode", "bankCode", "accountType", "accountNo", "accountName", 
		"province", "city", "bankName", "accountProp", "amount", "currency", "protocol", "protocolUserid", "idType", 
		"id", "tel", "reckonAccount", "custUserid", "remark", "reserve1", "reserve2", "reserve3", "reserve4", "eleBankno", 
		"abs","ps","use", "creValDate","creCvn2","accPass"})
public class RequestBatchDetail {
	// 交易流水号
	@XmlElement(name="SN")
	private String sn;
	// 合众支付用户账号
	@XmlElement(name="E_USER_CODE")
	private String eUserCode;
	// 银行代码
	@XmlElement(name="BANK_CODE")
	private String bankCode;
	// 账号类型
	@XmlElement(name="ACCOUNT_TYPE")
	private String accountType;
	// 账号
	@XmlElement(name="ACCOUNT_NO")
	private String accountNo;
	// 账号名
	@XmlElement(name="ACCOUNT_NAME")
	private String accountName;
	// 开户行所在省
	@XmlElement(name="PROVINCE")
	private String province;
	// 开户行所在市
	@XmlElement(name="CITY")
	private String city;
	// 开户行名称
	@XmlElement(name="BANK_NAME")
	private String bankName;
	// 账号属性
	@XmlElement(name="ACCOUNT_PROP")
	private String accountProp;
	// 金额
	@XmlElement(name="AMOUNT")
	private String amount;
	// 货币类型
	@XmlElement(name="CURRENCY")
	private String currency;
	// 协议号
	@XmlElement(name="PROTOCOL")
	private String protocol;
	// 协议用户编号
	@XmlElement(name="PROTOCOL_USERID")
	private String protocolUserid;
	// 开户证件类型
	@XmlElement(name="ID_TYPE")
	private String idType;
	// 证件号
	@XmlElement(name="ID")
	private String id;
	// 手机号
	@XmlElement(name="TEL")
	private String tel;
	// 清算账号
	@XmlElement(name="RECKON_ACCOUNT")
	private String reckonAccount;
	// 自定义用户号
	@XmlElement(name="CUST_USERID")
	private String custUserid;
	// 备注
	@XmlElement(name="REMARK")
	private String remark;
	// 开户行名称简写
	@XmlElement(name="RESERVE1")
	private String reserve1;
	// 保留域
	@XmlElement(name="RESERVE2")
	private String reserve2;
	// 保留域
	@XmlElement(name="RESERVE3")
	private String reserve3;
	// 保留域
	@XmlElement(name="RESERVE4")
	private String reserve4;
	// 电子联行号
	@XmlElement(name="ELE_BANKNO")
	private String eleBankno;
	// 摘要
	@XmlElement(name="ABS")
	private String abs;
	// 附言
	@XmlElement(name="PS")
	private String ps;
	// 用途
	@XmlElement(name="USE")
	private String use;
	// 信用卡有效期
	@XmlElement(name="CRE_VAL_DATE")
	private String creValDate;
	// CVN2码
	@XmlElement(name="CRE_CVN2")
	private String creCvn2;
	// 账号密码
	@XmlElement(name="ACC_PASS")
	private String accPass;
	
	
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String geteUserCode() {
		return eUserCode;
	}
	public void seteUserCode(String eUserCode) {
		this.eUserCode = eUserCode;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountProp() {
		return accountProp;
	}
	public void setAccountProp(String accountProp) {
		this.accountProp = accountProp;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getProtocolUserid() {
		return protocolUserid;
	}
	public void setProtocolUserid(String protocolUserid) {
		this.protocolUserid = protocolUserid;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getReckonAccount() {
		return reckonAccount;
	}
	public void setReckonAccount(String reckonAccount) {
		this.reckonAccount = reckonAccount;
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
	public String getReserve1() {
		return reserve1;
	}
	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}
	public String getReserve2() {
		return reserve2;
	}
	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}
	public String getReserve3() {
		return reserve3;
	}
	public void setReserve3(String reserve3) {
		this.reserve3 = reserve3;
	}
	public String getReserve4() {
		return reserve4;
	}
	public void setReserve4(String reserve4) {
		this.reserve4 = reserve4;
	}
	public String getEleBankno() {
		return eleBankno;
	}
	public void setEleBankno(String eleBankno) {
		this.eleBankno = eleBankno;
	}
	public String getAbs() {
		return abs;
	}
	public void setAbs(String abs) {
		this.abs = abs;
	}
	public String getPs() {
		return ps;
	}
	public void setPs(String ps) {
		this.ps = ps;
	}
	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}
	public String getCreValDate() {
		return creValDate;
	}
	public void setCreValDate(String creValDate) {
		this.creValDate = creValDate;
	}
	public String getCreCvn2() {
		return creCvn2;
	}
	public void setCreCvn2(String creCvn2) {
		this.creCvn2 = creCvn2;
	}
	public String getAccPass() {
		return accPass;
	}
	public void setAccPass(String accPass) {
		this.accPass = accPass;
	}
	@Override
	public String toString() {
		return "RequestBatchDetail [sn=" + sn + ", eUserCode=" + eUserCode
				+ ", bankCode=" + bankCode + ", accountType=" + accountType
				+ ", accountNo=" + accountNo + ", accountName=" + accountName
				+ ", province=" + province + ", city=" + city + ", bankName="
				+ bankName + ", accountProp=" + accountProp + ", amount="
				+ amount + ", currency=" + currency + ", protocol=" + protocol
				+ ", protocolUserid=" + protocolUserid + ", idType=" + idType
				+ ", id=" + id + ", tel=" + tel + ", reckonAccount="
				+ reckonAccount + ", custUserid=" + custUserid + ", remark="
				+ remark + ", reserve1=" + reserve1 + ", reserve2=" + reserve2
				+ ", reserve3=" + reserve3 + ", reserve4=" + reserve4
				+ ", eleBankno=" + eleBankno + ", abs=" + abs + ", ps=" + ps
				+ ", use=" + use + ", creValDate=" + creValDate + ", creCvn2="
				+ creCvn2 + ", accPass=" + accPass + "]";
	}
}

