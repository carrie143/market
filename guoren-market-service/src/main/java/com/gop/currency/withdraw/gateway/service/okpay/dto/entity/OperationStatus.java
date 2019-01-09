/**
 * OperationStatus.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gop.currency.withdraw.gateway.service.okpay.dto.entity;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;

import javax.xml.namespace.QName;

import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.EnumDeserializer;
import org.apache.axis.encoding.ser.EnumSerializer;

public class OperationStatus implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _value_;
	private static HashMap<String, OperationStatus> _table_ = new HashMap<String, OperationStatus>();

	// Constructor
	protected OperationStatus(String value) {
		_value_ = value;
		_table_.put(_value_, this);
	}

	public static final String _None = "None";
	public static final String _Error = "Error";
	public static final String _Canceled = "Canceled";
	public static final String _Pending = "Pending";
	public static final String _Reversed = "Reversed";
	public static final String _Hold = "Hold";
	public static final String _Completed = "Completed";
	public static final OperationStatus None = new OperationStatus(_None);
	public static final OperationStatus Error = new OperationStatus(_Error);
	public static final OperationStatus Canceled = new OperationStatus(_Canceled);
	public static final OperationStatus Pending = new OperationStatus(_Pending);
	public static final OperationStatus Reversed = new OperationStatus(_Reversed);
	public static final OperationStatus Hold = new OperationStatus(_Hold);
	public static final OperationStatus Completed = new OperationStatus(_Completed);

	public String getValue() {
		return _value_;
	}

	public static OperationStatus fromValue(String value) throws IllegalArgumentException {
		OperationStatus enumeration = (OperationStatus) _table_.get(value);
		if (enumeration == null)
			throw new IllegalArgumentException();
		return enumeration;
	}

	public static OperationStatus fromString(String value) throws IllegalArgumentException {
		return fromValue(value);
	}

	public boolean equals(Object obj) {
		return (obj == this);
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public java.lang.String toString() {
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
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			OperationStatus.class);

	static {
		typeDesc.setXmlType(
				new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "OperationStatus"));
	}

	/**
	 * Return type metadata object
	 */
	public static org.apache.axis.description.TypeDesc getTypeDesc() {
		return typeDesc;
	}

}
