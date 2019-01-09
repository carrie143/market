/**
 * SubscriptionCycleIntervals.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gop.currency.withdraw.gateway.service.okpay.dto.entity;

import java.io.ObjectStreamException;
import java.util.HashMap;

import javax.xml.namespace.QName;

import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.EnumDeserializer;
import org.apache.axis.encoding.ser.EnumSerializer;

public class SubscriptionCycleIntervals implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _value_;
	private static HashMap<String, SubscriptionCycleIntervals> _table_ = new HashMap<String, SubscriptionCycleIntervals>();

	// Constructor
	protected SubscriptionCycleIntervals(String value) {
		_value_ = value;
		_table_.put(_value_, this);
	}

	public static final String _Days = "Days";
	public static final String _Weeks = "Weeks";
	public static final String _Months = "Months";
	public static final String _Years = "Years";
	public static final SubscriptionCycleIntervals Days = new SubscriptionCycleIntervals(_Days);
	public static final SubscriptionCycleIntervals Weeks = new SubscriptionCycleIntervals(_Weeks);
	public static final SubscriptionCycleIntervals Months = new SubscriptionCycleIntervals(_Months);
	public static final SubscriptionCycleIntervals Years = new SubscriptionCycleIntervals(_Years);

	public String getValue() {
		return _value_;
	}

	public static SubscriptionCycleIntervals fromValue(String value) throws IllegalArgumentException {
		SubscriptionCycleIntervals enumeration = (SubscriptionCycleIntervals) _table_.get(value);
		if (enumeration == null)
			throw new IllegalArgumentException();
		return enumeration;
	}

	public static SubscriptionCycleIntervals fromString(String value) throws IllegalArgumentException {
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
	private static TypeDesc typeDesc = new TypeDesc(SubscriptionCycleIntervals.class);

	static {
		typeDesc.setXmlType(
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "SubscriptionCycleIntervals"));
	}

	/**
	 * Return type metadata object
	 */
	public static TypeDesc getTypeDesc() {
		return typeDesc;
	}

}
