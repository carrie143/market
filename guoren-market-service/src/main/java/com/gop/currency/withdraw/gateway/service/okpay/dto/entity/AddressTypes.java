/**
 * AddressTypes.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gop.currency.withdraw.gateway.service.okpay.dto.entity;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;

import javax.xml.namespace.QName;

import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.EnumDeserializer;
import org.apache.axis.encoding.ser.EnumSerializer;


public class AddressTypes implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _value_;
	private static HashMap<String, AddressTypes> _table_ = new HashMap<String, AddressTypes>();

	// Constructor
	protected AddressTypes(String value) {
		_value_ = value;
		_table_.put(_value_, this);
	}

	public static final String _Unset = "Unset";
	public static final String _Legal = "Legal";
	public static final String _Actual = "Actual";
	public static final String _Postal = "Postal";
	public static final AddressTypes Unset = new AddressTypes(_Unset);
	public static final AddressTypes Legal = new AddressTypes(_Legal);
	public static final AddressTypes Actual = new AddressTypes(_Actual);
	public static final AddressTypes Postal = new AddressTypes(_Postal);

	public String getValue() {
		return _value_;
	}

	public static AddressTypes fromValue(String value) throws IllegalArgumentException {
		AddressTypes enumeration = (AddressTypes) _table_.get(value);
		if (enumeration == null)
			throw new IllegalArgumentException();
		return enumeration;
	}

	public static AddressTypes fromString(String value) throws IllegalArgumentException {
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
	private static TypeDesc typeDesc = new TypeDesc(AddressTypes.class);

	static {
		typeDesc.setXmlType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "AddressTypes"));
	}

	/**
	 * Return type metadata object
	 */
	public static TypeDesc getTypeDesc() {
		return typeDesc;
	}

}
