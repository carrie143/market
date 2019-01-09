/**
 * PreapprovedPaymentStatuses.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gop.currency.withdraw.gateway.service.okpay.dto.entity;

import java.io.Serializable;
import java.util.HashMap;

import javax.xml.namespace.QName;

import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.EnumDeserializer;
import org.apache.axis.encoding.ser.EnumSerializer;

public class PreapprovedPaymentStatuses implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _value_;
	private static HashMap<String, PreapprovedPaymentStatuses> _table_ = new HashMap<String, PreapprovedPaymentStatuses>();

	// Constructor
	protected PreapprovedPaymentStatuses(String value) {
		_value_ = value;
		_table_.put(_value_, this);
	}

	public static final String _Active = "Active";
	public static final String _Canceled = "Canceled";
	public static final String _Failed = "Failed";
	public static final String _Expired = "Expired";
	public static final PreapprovedPaymentStatuses Active = new PreapprovedPaymentStatuses(_Active);
	public static final PreapprovedPaymentStatuses Canceled = new PreapprovedPaymentStatuses(_Canceled);
	public static final PreapprovedPaymentStatuses Failed = new PreapprovedPaymentStatuses(_Failed);
	public static final PreapprovedPaymentStatuses Expired = new PreapprovedPaymentStatuses(_Expired);

	public String getValue() {
		return _value_;
	}

	public static PreapprovedPaymentStatuses fromValue(String value) throws IllegalArgumentException {
		PreapprovedPaymentStatuses enumeration = (PreapprovedPaymentStatuses) _table_.get(value);
		if (enumeration == null)
			throw new IllegalArgumentException();
		return enumeration;
	}

	public static PreapprovedPaymentStatuses fromString(String value) throws IllegalArgumentException {
		return fromValue(value);
	}

	public boolean equals(Object obj) {
		return (obj == this);
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public String toString() {
		return _value_;
	}

	public Object readResolve() throws java.io.ObjectStreamException {
		return fromValue(_value_);
	}

	public static Serializer getSerializer(String mechType, Class<?> _javaType, QName _xmlType) {
		return new EnumSerializer(_javaType, _xmlType);
	}

	public static Deserializer getDeserializer(String mechType, Class<?> _javaType, QName _xmlType) {
		return new EnumDeserializer(_javaType, _xmlType);
	}

	// Type metadata
	private static TypeDesc typeDesc = new TypeDesc(PreapprovedPaymentStatuses.class);

	static {
		typeDesc.setXmlType(
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "PreapprovedPaymentStatuses"));
	}

	/**
	 * Return type metadata object
	 */
	public static TypeDesc getTypeDesc() {
		return typeDesc;
	}

}
