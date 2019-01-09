/**
 * VerificationStatuses.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gop.currency.withdraw.gateway.service.okpay.dto.status;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;

import javax.xml.namespace.QName;

import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.EnumDeserializer;
import org.apache.axis.encoding.ser.EnumSerializer;

public class VerificationStatuses implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _value_;
	private static HashMap<String, VerificationStatuses> _table_ = new HashMap<String, VerificationStatuses>();

	// Constructor
	protected VerificationStatuses(String value) {
		_value_ = value;
		_table_.put(_value_, this);
	}

	public static final String _Unverified = "Unverified";
	public static final String _Formal = "Formal";
	public static final String _Verified = "Verified";
	public static final VerificationStatuses Unverified = new VerificationStatuses(_Unverified);
	public static final VerificationStatuses Formal = new VerificationStatuses(_Formal);
	public static final VerificationStatuses Verified = new VerificationStatuses(_Verified);

	public String getValue() {
		return _value_;
	}

	public static VerificationStatuses fromValue(String value) throws IllegalArgumentException {
		VerificationStatuses enumeration = (VerificationStatuses) _table_.get(value);
		if (enumeration == null)
			throw new IllegalArgumentException();
		return enumeration;
	}

	public static VerificationStatuses fromString(String value) throws IllegalArgumentException {
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

	public Object readResolve() throws ObjectStreamException {
		return fromValue(_value_);
	}

	public static Serializer getSerializer(String mechType, Class<?> _javaType, QName _xmlType) {
		return new EnumSerializer(_javaType, _xmlType);
	}

	public static Deserializer getDeserializer(String mechType, Class<?> _javaType, QName _xmlType) {
		return new EnumDeserializer(_javaType, _xmlType);
	}

	// Type metadata
	private static TypeDesc typeDesc = new TypeDesc(VerificationStatuses.class);

	static {
		typeDesc.setXmlType(
				new QName("http://schemas.datacontract.org/2004/07/OkPayLink.OkPayService", "VerificationStatuses"));
	}

	/**
	 * Return type metadata object
	 */
	public static TypeDesc getTypeDesc() {
		return typeDesc;
	}

}
