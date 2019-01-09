/**
 * AccountInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gop.currency.withdraw.gateway.service.okpay.dto.entity;

import java.io.Serializable;

import javax.xml.namespace.QName;

import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

import com.gop.currency.withdraw.gateway.service.okpay.dto.status.VerificationStatuses;

public class AccountInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long accountID;

	private String country_ISO;

	private String email;

	private String name;

	private VerificationStatuses verificationStatus;

	private String walletID;

	public AccountInfo() {
	}

	public AccountInfo(Long accountID, String country_ISO, String email, String name,
			VerificationStatuses verificationStatus,
			String walletID) {
		this.accountID = accountID;
		this.country_ISO = country_ISO;
		this.email = email;
		this.name = name;
		this.verificationStatus = verificationStatus;
		this.walletID = walletID;
	}

	/**
	 * Gets the accountID value for this AccountInfo.
	 * 
	 * @return accountID
	 */
	public Long getAccountID() {
		return accountID;
	}

	/**
	 * Sets the accountID value for this AccountInfo.
	 * 
	 * @param accountID
	 */
	public void setAccountID(Long accountID) {
		this.accountID = accountID;
	}

	/**
	 * Gets the country_ISO value for this AccountInfo.
	 * 
	 * @return country_ISO
	 */
	public String getCountry_ISO() {
		return country_ISO;
	}

	/**
	 * Sets the country_ISO value for this AccountInfo.
	 * 
	 * @param country_ISO
	 */
	public void setCountry_ISO(String country_ISO) {
		this.country_ISO = country_ISO;
	}

	/**
	 * Gets the email value for this AccountInfo.
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email value for this AccountInfo.
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the name value for this AccountInfo.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name value for this AccountInfo.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the verificationStatus value for this AccountInfo.
	 * 
	 * @return verificationStatus
	 */
	public VerificationStatuses getVerificationStatus() {
		return verificationStatus;
	}

	/**
	 * Sets the verificationStatus value for this AccountInfo.
	 * 
	 * @param verificationStatus
	 */
	public void setVerificationStatus(
			VerificationStatuses verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	/**
	 * Gets the walletID value for this AccountInfo.
	 * 
	 * @return walletID
	 */
	public String getWalletID() {
		return walletID;
	}

	/**
	 * Sets the walletID value for this AccountInfo.
	 * 
	 * @param walletID
	 */
	public void setWalletID(String walletID) {
		this.walletID = walletID;
	}

	private Object __equalsCalc = null;

	public synchronized boolean equals(Object obj) {
		if (!(obj instanceof AccountInfo))
			return false;
		AccountInfo other = (AccountInfo) obj;
//		if (obj == null)
//			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true
				&& ((this.accountID == null && other.getAccountID() == null)
						|| (this.accountID != null && this.accountID.equals(other.getAccountID())))
				&& ((this.country_ISO == null && other.getCountry_ISO() == null)
						|| (this.country_ISO != null && this.country_ISO.equals(other.getCountry_ISO())))
				&& ((this.email == null && other.getEmail() == null)
						|| (this.email != null && this.email.equals(other.getEmail())))
				&& ((this.name == null && other.getName() == null)
						|| (this.name != null && this.name.equals(other.getName())))
				&& ((this.verificationStatus == null && other.getVerificationStatus() == null)
						|| (this.verificationStatus != null
								&& this.verificationStatus.equals(other.getVerificationStatus())))
				&& ((this.walletID == null && other.getWalletID() == null)
						|| (this.walletID != null && this.walletID.equals(other.getWalletID())));
		__equalsCalc = null;
		return _equals;
	}

	private boolean __hashCodeCalc = false;

	public synchronized int hashCode() {
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = 1;
		if (getAccountID() != null) {
			_hashCode += getAccountID().hashCode();
		}
		if (getCountry_ISO() != null) {
			_hashCode += getCountry_ISO().hashCode();
		}
		if (getEmail() != null) {
			_hashCode += getEmail().hashCode();
		}
		if (getName() != null) {
			_hashCode += getName().hashCode();
		}
		if (getVerificationStatus() != null) {
			_hashCode += getVerificationStatus().hashCode();
		}
		if (getWalletID() != null) {
			_hashCode += getWalletID().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static TypeDesc typeDesc = new TypeDesc(AccountInfo.class, true);

	static {
		typeDesc.setXmlType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "AccountInfo"));
		ElementDesc elemField = new ElementDesc();
		elemField.setFieldName("accountID");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "AccountID"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("country_ISO");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Country_ISO"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("email");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Email"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("name");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Name"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("verificationStatus");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "VerificationStatus"));
		elemField.setXmlType(
				new QName("http://schemas.datacontract.org/2004/07/OkPayLink.OkPayService", "VerificationStatuses"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("walletID");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "WalletID"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}

	/**
	 * Return type metadata object
	 */
	public static TypeDesc getTypeDesc() {
		return typeDesc;
	}

	/**
	 * Get Custom Serializer
	 */
	public static Serializer getSerializer(String mechType, Class<?> _javaType, QName _xmlType) {
		return new BeanSerializer(_javaType, _xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	public static Deserializer getDeserializer(String mechType, Class<?> _javaType, QName _xmlType) {
		return new BeanDeserializer(_javaType, _xmlType, typeDesc);
	}

}
