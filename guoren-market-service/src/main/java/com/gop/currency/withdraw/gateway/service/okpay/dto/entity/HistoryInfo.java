/**
 * HistoryInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gop.currency.withdraw.gateway.service.okpay.dto.entity;

import java.io.Serializable;
import java.lang.reflect.Array;

import javax.xml.namespace.QName;

import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class HistoryInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer pageCount;

	private Integer pageNumber;

	private Integer pageSize;

	private Integer totalCount;

	private TransactionInfo[] transactions;

	public HistoryInfo() {
	}

	public HistoryInfo(Integer pageCount, Integer pageNumber, Integer pageSize, Integer totalCount,
			TransactionInfo[] transactions) {
		this.pageCount = pageCount;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.transactions = transactions;
	}

	/**
	 * Gets the pageCount value for this HistoryInfo.
	 * 
	 * @return pageCount
	 */
	public Integer getPageCount() {
		return pageCount;
	}

	/**
	 * Sets the pageCount value for this HistoryInfo.
	 * 
	 * @param pageCount
	 */
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * Gets the pageNumber value for this HistoryInfo.
	 * 
	 * @return pageNumber
	 */
	public Integer getPageNumber() {
		return pageNumber;
	}

	/**
	 * Sets the pageNumber value for this HistoryInfo.
	 * 
	 * @param pageNumber
	 */
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * Gets the pageSize value for this HistoryInfo.
	 * 
	 * @return pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * Sets the pageSize value for this HistoryInfo.
	 * 
	 * @param pageSize
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * Gets the totalCount value for this HistoryInfo.
	 * 
	 * @return totalCount
	 */
	public Integer getTotalCount() {
		return totalCount;
	}

	/**
	 * Sets the totalCount value for this HistoryInfo.
	 * 
	 * @param totalCount
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * Gets the transactions value for this HistoryInfo.
	 * 
	 * @return transactions
	 */
	public TransactionInfo[] getTransactions() {
		return transactions;
	}

	/**
	 * Sets the transactions value for this HistoryInfo.
	 * 
	 * @param transactions
	 */
	public void setTransactions(TransactionInfo[] transactions) {
		this.transactions = transactions;
	}

	private Object __equalsCalc = null;

	public synchronized boolean equals(Object obj) {
		if (!(obj instanceof HistoryInfo))
			return false;
		HistoryInfo other = (HistoryInfo) obj;
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
				&& ((this.pageCount == null && other.getPageCount() == null)
						|| (this.pageCount != null && this.pageCount.equals(other.getPageCount())))
				&& ((this.pageNumber == null && other.getPageNumber() == null)
						|| (this.pageNumber != null && this.pageNumber.equals(other.getPageNumber())))
				&& ((this.pageSize == null && other.getPageSize() == null)
						|| (this.pageSize != null && this.pageSize.equals(other.getPageSize())))
				&& ((this.totalCount == null && other.getTotalCount() == null)
						|| (this.totalCount != null && this.totalCount.equals(other.getTotalCount())))
				&& ((this.transactions == null && other.getTransactions() == null) || (this.transactions != null
						&& java.util.Arrays.equals(this.transactions, other.getTransactions())));
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
		if (getPageCount() != null) {
			_hashCode += getPageCount().hashCode();
		}
		if (getPageNumber() != null) {
			_hashCode += getPageNumber().hashCode();
		}
		if (getPageSize() != null) {
			_hashCode += getPageSize().hashCode();
		}
		if (getTotalCount() != null) {
			_hashCode += getTotalCount().hashCode();
		}
		if (getTransactions() != null) {
			for (int i = 0; i < Array.getLength(getTransactions()); i++) {
				java.lang.Object obj = Array.get(getTransactions(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static TypeDesc typeDesc = new TypeDesc(HistoryInfo.class, true);

	static {
		typeDesc.setXmlType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "HistoryInfo"));
		ElementDesc elemField = new ElementDesc();
		elemField.setFieldName("pageCount");
		elemField.setXmlName(
				new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "PageCount"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("pageNumber");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "PageNumber"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("pageSize");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "PageSize"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("totalCount");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "TotalCount"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("transactions");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Transactions"));
		elemField.setXmlType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "TransactionInfo"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		elemField.setItemQName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "TransactionInfo"));
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
