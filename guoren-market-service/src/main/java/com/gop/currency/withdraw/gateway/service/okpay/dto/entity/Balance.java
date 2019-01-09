/**
 * Balance.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gop.currency.withdraw.gateway.service.okpay.dto.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.namespace.QName;

import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class Balance implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal amount;

	private String currency;

	public Balance() {
	}

	public Balance(BigDecimal amount, String currency) {
		this.amount = amount;
		this.currency = currency;
	}

	/**
	 * Gets the amount value for this Balance.
	 * 
	 * @return amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Sets the amount value for this Balance.
	 * 
	 * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * Gets the currency value for this Balance.
	 * 
	 * @return currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * Sets the currency value for this Balance.
	 * 
	 * @param currency
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	private Object __equalsCalc = null;

	public synchronized boolean equals(Object obj) {
		if (!(obj instanceof Balance))
			return false;
		Balance other = (Balance) obj;
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
				&& ((this.amount == null && other.getAmount() == null)
						|| (this.amount != null && this.amount.equals(other.getAmount())))
				&& ((this.currency == null && other.getCurrency() == null)
						|| (this.currency != null && this.currency.equals(other.getCurrency())));
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
		if (getAmount() != null) {
			_hashCode += getAmount().hashCode();
		}
		if (getCurrency() != null) {
			_hashCode += getCurrency().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static TypeDesc typeDesc = new TypeDesc(Balance.class, true);

	static {
		typeDesc.setXmlType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Balance"));
		ElementDesc elemField = new ElementDesc();
		elemField.setFieldName("amount");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Amount"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("currency");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Currency"));
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

	@Override
	public String toString() {
		return "Balance [amount=" + amount + ", currency=" + currency + "]";
	}

}
