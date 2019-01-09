/**
 * TransactionInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gop.currency.withdraw.gateway.service.okpay.dto.entity;

import java.math.BigDecimal;

import javax.xml.namespace.QName;

import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class TransactionInfo implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal amount;

	private String comment;

	private String currency;

	private String date;

	private BigDecimal fees;

	private Long ID;

	private String invoice;

	private BigDecimal net;

	private String operationName;

	private AccountInfo receiver;

	private AccountInfo sender;

	private OperationStatus status;

	public TransactionInfo() {
	}

	public TransactionInfo(BigDecimal amount, String comment, String currency, String date, BigDecimal fees, Long ID,
			String invoice, BigDecimal net, String operationName, AccountInfo receiver, AccountInfo sender,
			OperationStatus status) {
		this.amount = amount;
		this.comment = comment;
		this.currency = currency;
		this.date = date;
		this.fees = fees;
		this.ID = ID;
		this.invoice = invoice;
		this.net = net;
		this.operationName = operationName;
		this.receiver = receiver;
		this.sender = sender;
		this.status = status;
	}

	/**
	 * Gets the amount value for this TransactionInfo.
	 * 
	 * @return amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Sets the amount value for this TransactionInfo.
	 * 
	 * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * Gets the comment value for this TransactionInfo.
	 * 
	 * @return comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets the comment value for this TransactionInfo.
	 * 
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Gets the currency value for this TransactionInfo.
	 * 
	 * @return currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * Sets the currency value for this TransactionInfo.
	 * 
	 * @param currency
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * Gets the date value for this TransactionInfo.
	 * 
	 * @return date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Sets the date value for this TransactionInfo.
	 * 
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Gets the fees value for this TransactionInfo.
	 * 
	 * @return fees
	 */
	public BigDecimal getFees() {
		return fees;
	}

	/**
	 * Sets the fees value for this TransactionInfo.
	 * 
	 * @param fees
	 */
	public void setFees(BigDecimal fees) {
		this.fees = fees;
	}

	/**
	 * Gets the ID value for this TransactionInfo.
	 * 
	 * @return ID
	 */
	public Long getID() {
		return ID;
	}

	/**
	 * Sets the ID value for this TransactionInfo.
	 * 
	 * @param ID
	 */
	public void setID(Long ID) {
		this.ID = ID;
	}

	/**
	 * Gets the invoice value for this TransactionInfo.
	 * 
	 * @return invoice
	 */
	public String getInvoice() {
		return invoice;
	}

	/**
	 * Sets the invoice value for this TransactionInfo.
	 * 
	 * @param invoice
	 */
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	/**
	 * Gets the net value for this TransactionInfo.
	 * 
	 * @return net
	 */
	public BigDecimal getNet() {
		return net;
	}

	/**
	 * Sets the net value for this TransactionInfo.
	 * 
	 * @param net
	 */
	public void setNet(BigDecimal net) {
		this.net = net;
	}

	/**
	 * Gets the operationName value for this TransactionInfo.
	 * 
	 * @return operationName
	 */
	public String getOperationName() {
		return operationName;
	}

	/**
	 * Sets the operationName value for this TransactionInfo.
	 * 
	 * @param operationName
	 */
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	/**
	 * Gets the receiver value for this TransactionInfo.
	 * 
	 * @return receiver
	 */
	public AccountInfo getReceiver() {
		return receiver;
	}

	/**
	 * Sets the receiver value for this TransactionInfo.
	 * 
	 * @param receiver
	 */
	public void setReceiver(AccountInfo receiver) {
		this.receiver = receiver;
	}

	/**
	 * Gets the sender value for this TransactionInfo.
	 * 
	 * @return sender
	 */
	public AccountInfo getSender() {
		return sender;
	}

	/**
	 * Sets the sender value for this TransactionInfo.
	 * 
	 * @param sender
	 */
	public void setSender(AccountInfo sender) {
		this.sender = sender;
	}

	/**
	 * Gets the status value for this TransactionInfo.
	 * 
	 * @return status
	 */
	public OperationStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status value for this TransactionInfo.
	 * 
	 * @param status
	 */
	public void setStatus(OperationStatus status) {
		this.status = status;
	}

	private Object __equalsCalc = null;

	public synchronized boolean equals(Object obj) {
		if (!(obj instanceof TransactionInfo))
			return false;
		TransactionInfo other = (TransactionInfo) obj;
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
				&& ((this.comment == null && other.getComment() == null)
						|| (this.comment != null && this.comment.equals(other.getComment())))
				&& ((this.currency == null && other.getCurrency() == null)
						|| (this.currency != null && this.currency.equals(other.getCurrency())))
				&& ((this.date == null && other.getDate() == null)
						|| (this.date != null && this.date.equals(other.getDate())))
				&& ((this.fees == null && other.getFees() == null)
						|| (this.fees != null && this.fees.equals(other.getFees())))
				&& ((this.ID == null && other.getID() == null) || (this.ID != null && this.ID.equals(other.getID())))
				&& ((this.invoice == null && other.getInvoice() == null)
						|| (this.invoice != null && this.invoice.equals(other.getInvoice())))
				&& ((this.net == null && other.getNet() == null)
						|| (this.net != null && this.net.equals(other.getNet())))
				&& ((this.operationName == null && other.getOperationName() == null)
						|| (this.operationName != null && this.operationName.equals(other.getOperationName())))
				&& ((this.receiver == null && other.getReceiver() == null)
						|| (this.receiver != null && this.receiver.equals(other.getReceiver())))
				&& ((this.sender == null && other.getSender() == null)
						|| (this.sender != null && this.sender.equals(other.getSender())))
				&& ((this.status == null && other.getStatus() == null)
						|| (this.status != null && this.status.equals(other.getStatus())));
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
		if (getComment() != null) {
			_hashCode += getComment().hashCode();
		}
		if (getCurrency() != null) {
			_hashCode += getCurrency().hashCode();
		}
		if (getDate() != null) {
			_hashCode += getDate().hashCode();
		}
		if (getFees() != null) {
			_hashCode += getFees().hashCode();
		}
		if (getID() != null) {
			_hashCode += getID().hashCode();
		}
		if (getInvoice() != null) {
			_hashCode += getInvoice().hashCode();
		}
		if (getNet() != null) {
			_hashCode += getNet().hashCode();
		}
		if (getOperationName() != null) {
			_hashCode += getOperationName().hashCode();
		}
		if (getReceiver() != null) {
			_hashCode += getReceiver().hashCode();
		}
		if (getSender() != null) {
			_hashCode += getSender().hashCode();
		}
		if (getStatus() != null) {
			_hashCode += getStatus().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static TypeDesc typeDesc = new TypeDesc(TransactionInfo.class, true);

	static {
		typeDesc.setXmlType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "TransactionInfo"));
		ElementDesc elemField = new ElementDesc();
		elemField.setFieldName("amount");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Amount"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("comment");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Comment"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("currency");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Currency"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("date");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Date"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("fees");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Fees"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("ID");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "ID"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("invoice");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Invoice"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("net");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Net"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("operationName");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "OperationName"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("receiver");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Receiver"));
		elemField.setXmlType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "AccountInfo"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("sender");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Sender"));
		elemField.setXmlType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "AccountInfo"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("status");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Status"));
		elemField.setXmlType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "OperationStatus"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
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
		return "TransactionInfo [amount=" + amount + ", comment=" + comment + ", currency=" + currency + ", date="
				+ date + ", fees=" + fees + ", ID=" + ID + ", invoice=" + invoice + ", net=" + net + ", operationName="
				+ operationName + ", receiver=" + receiver + ", sender=" + sender + ", status=" + status + "]";
	}
	

}
