/**
 * ClientStatus.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gop.currency.withdraw.gateway.service.okpay.dto.status;

import java.io.ObjectStreamException;
import java.util.HashMap;

import javax.xml.namespace.QName;

import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.EnumDeserializer;
import org.apache.axis.encoding.ser.EnumSerializer;

public class ClientStatus implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _value_;
	private static HashMap<String, ClientStatus> _table_ = new HashMap<String, ClientStatus>();

	// Constructor
	protected ClientStatus(String value) {
		_value_ = value;
		_table_.put(_value_, this);
	}

	public static final String _Unregistered = "Unregistered";
	public static final String _Registration = "Registration";
	public static final String _Unverified = "Unverified";
	public static final String _Formal = "Formal";
	public static final String _Verified = "Verified";
	public static final String _Blocked = "Blocked";
	public static final ClientStatus Unregistered = new ClientStatus(_Unregistered);
	public static final ClientStatus Registration = new ClientStatus(_Registration);
	public static final ClientStatus Unverified = new ClientStatus(_Unverified);
	public static final ClientStatus Formal = new ClientStatus(_Formal);
	public static final ClientStatus Verified = new ClientStatus(_Verified);
	public static final ClientStatus Blocked = new ClientStatus(_Blocked);

	public String getValue() {
		return _value_;
	}

	public static ClientStatus fromValue(String value) throws IllegalArgumentException {
		ClientStatus enumeration = (ClientStatus) _table_.get(value);
		if (enumeration == null)
			throw new IllegalArgumentException();
		return enumeration;
	}

	public static ClientStatus fromString(String value) throws IllegalArgumentException {
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
	private static TypeDesc typeDesc = new TypeDesc(ClientStatus.class);

	static {
		typeDesc.setXmlType(
				new QName("http://schemas.datacontract.org/2004/07/OkPayLink.OkPayService", "ClientStatus"));
	}

	/**
	 * Return type metadata object
	 */
	public static TypeDesc getTypeDesc() {
		return typeDesc;
	}

}
