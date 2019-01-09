/**
 * Address.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gop.currency.withdraw.gateway.service.okpay.dto.contract;

import java.io.Serializable;

import javax.xml.namespace.QName;

import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.AddressTypes;

public class Address implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String addressLine;

	private String city;

	private String country;

	private Boolean isVerified;

	private String region;

	private AddressTypes type;

	private String zipCode;

	public Address() {
	}

	public Address(String addressLine, String city, String country, Boolean isVerified, String region,
			AddressTypes type, String zipCode) {
		this.addressLine = addressLine;
		this.city = city;
		this.country = country;
		this.isVerified = isVerified;
		this.region = region;
		this.type = type;
		this.zipCode = zipCode;
	}

	/**
	 * Gets the addressLine value for this Address.
	 * 
	 * @return addressLine
	 */
	public String getAddressLine() {
		return addressLine;
	}

	/**
	 * Sets the addressLine value for this Address.
	 * 
	 * @param addressLine
	 */
	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	/**
	 * Gets the city value for this Address.
	 * 
	 * @return city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city value for this Address.
	 * 
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the country value for this Address.
	 * 
	 * @return country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country value for this Address.
	 * 
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the isVerified value for this Address.
	 * 
	 * @return isVerified
	 */
	public Boolean getIsVerified() {
		return isVerified;
	}

	/**
	 * Sets the isVerified value for this Address.
	 * 
	 * @param isVerified
	 */
	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}

	/**
	 * Gets the region value for this Address.
	 * 
	 * @return region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * Sets the region value for this Address.
	 * 
	 * @param region
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * Gets the type value for this Address.
	 * 
	 * @return type
	 */
	public AddressTypes getType() {
		return type;
	}

	/**
	 * Sets the type value for this Address.
	 * 
	 * @param type
	 */
	public void setType(AddressTypes type) {
		this.type = type;
	}

	/**
	 * Gets the zipCode value for this Address.
	 * 
	 * @return zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * Sets the zipCode value for this Address.
	 * 
	 * @param zipCode
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	private Object __equalsCalc = null;

	public synchronized boolean equals(Object obj) {
		if (!(obj instanceof Address))
			return false;
		Address other = (Address) obj;
		// if (obj == null)
		// return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true
				&& ((this.addressLine == null && other.getAddressLine() == null)
						|| (this.addressLine != null && this.addressLine.equals(other.getAddressLine())))
				&& ((this.city == null && other.getCity() == null)
						|| (this.city != null && this.city.equals(other.getCity())))
				&& ((this.country == null && other.getCountry() == null)
						|| (this.country != null && this.country.equals(other.getCountry())))
				&& ((this.isVerified == null && other.getIsVerified() == null)
						|| (this.isVerified != null && this.isVerified.equals(other.getIsVerified())))
				&& ((this.region == null && other.getRegion() == null)
						|| (this.region != null && this.region.equals(other.getRegion())))
				&& ((this.type == null && other.getType() == null)
						|| (this.type != null && this.type.equals(other.getType())))
				&& ((this.zipCode == null && other.getZipCode() == null)
						|| (this.zipCode != null && this.zipCode.equals(other.getZipCode())));
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
		if (getAddressLine() != null) {
			_hashCode += getAddressLine().hashCode();
		}
		if (getCity() != null) {
			_hashCode += getCity().hashCode();
		}
		if (getCountry() != null) {
			_hashCode += getCountry().hashCode();
		}
		if (getIsVerified() != null) {
			_hashCode += getIsVerified().hashCode();
		}
		if (getRegion() != null) {
			_hashCode += getRegion().hashCode();
		}
		if (getType() != null) {
			_hashCode += getType().hashCode();
		}
		if (getZipCode() != null) {
			_hashCode += getZipCode().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static TypeDesc typeDesc = new TypeDesc(Address.class, true);

	static {
		typeDesc.setXmlType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "Address"));
		ElementDesc elemField = new ElementDesc();
		elemField.setFieldName("addressLine");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "AddressLine"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("city");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "City"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("country");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "Country"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("isVerified");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "IsVerified"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("region");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "Region"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("type");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "Type"));
		elemField.setXmlType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "AddressTypes"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("zipCode");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "ZipCode"));
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
