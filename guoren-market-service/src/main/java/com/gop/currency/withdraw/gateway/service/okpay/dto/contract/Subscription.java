/**
 * Subscription.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gop.currency.withdraw.gateway.service.okpay.dto.contract;

import javax.xml.namespace.QName;

import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class Subscription implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private com.gop.currency.withdraw.gateway.service.okpay.dto.entity.AccountInfo client;

	private String currency;

	private Long ID;

	private Boolean isActive;

	private String lastPaymentDateTime;

	private String merchantWalletID;

	private String nextPaymentDateTime;

	private java.math.BigDecimal outstandingBalance;

	private Boolean reattemptAcum;

	private Integer reattemptDays;

	private Integer regularCount;

	private Integer regularCycle;

	private com.gop.currency.withdraw.gateway.service.okpay.dto.entity.SubscriptionCycleIntervals regularInterval;

	private Integer regularPaymentsDone;

	private java.math.BigDecimal regularPrice;

	private String retryAt;

	private java.math.BigDecimal setupPrice;

	private com.gop.currency.withdraw.gateway.service.okpay.dto.contract.Address shippingAddress;

	private Boolean shippingNeeded;

	private String startDateTime;

	private com.gop.currency.withdraw.gateway.service.okpay.dto.entity.SubscriptionStatuses status;

	private String title;

	private Integer trialCount;

	private Integer trialCycle;

	private com.gop.currency.withdraw.gateway.service.okpay.dto.entity.SubscriptionCycleIntervals trialInterval;

	private Integer trialPaymentsDone;

	private java.math.BigDecimal trialPrice;

	private String validUntil;

	public Subscription() {
	}

	public Subscription(com.gop.currency.withdraw.gateway.service.okpay.dto.entity.AccountInfo client, String currency, Long ID,
			Boolean isActive, String lastPaymentDateTime, String merchantWalletID, String nextPaymentDateTime,
			java.math.BigDecimal outstandingBalance, Boolean reattemptAcum, Integer reattemptDays, Integer regularCount,
			Integer regularCycle, com.gop.currency.withdraw.gateway.service.okpay.dto.entity.SubscriptionCycleIntervals regularInterval,
			Integer regularPaymentsDone, java.math.BigDecimal regularPrice, String retryAt,
			java.math.BigDecimal setupPrice,
			com.gop.currency.withdraw.gateway.service.okpay.dto.contract.Address shippingAddress, Boolean shippingNeeded,
			String startDateTime, com.gop.currency.withdraw.gateway.service.okpay.dto.entity.SubscriptionStatuses status, String title,
			Integer trialCount, Integer trialCycle,
			com.gop.currency.withdraw.gateway.service.okpay.dto.entity.SubscriptionCycleIntervals trialInterval, Integer trialPaymentsDone,
			java.math.BigDecimal trialPrice, String validUntil) {
		this.client = client;
		this.currency = currency;
		this.ID = ID;
		this.isActive = isActive;
		this.lastPaymentDateTime = lastPaymentDateTime;
		this.merchantWalletID = merchantWalletID;
		this.nextPaymentDateTime = nextPaymentDateTime;
		this.outstandingBalance = outstandingBalance;
		this.reattemptAcum = reattemptAcum;
		this.reattemptDays = reattemptDays;
		this.regularCount = regularCount;
		this.regularCycle = regularCycle;
		this.regularInterval = regularInterval;
		this.regularPaymentsDone = regularPaymentsDone;
		this.regularPrice = regularPrice;
		this.retryAt = retryAt;
		this.setupPrice = setupPrice;
		this.shippingAddress = shippingAddress;
		this.shippingNeeded = shippingNeeded;
		this.startDateTime = startDateTime;
		this.status = status;
		this.title = title;
		this.trialCount = trialCount;
		this.trialCycle = trialCycle;
		this.trialInterval = trialInterval;
		this.trialPaymentsDone = trialPaymentsDone;
		this.trialPrice = trialPrice;
		this.validUntil = validUntil;
	}

	/**
	 * Gets the client value for this Subscription.
	 * 
	 * @return client
	 */
	public com.gop.currency.withdraw.gateway.service.okpay.dto.entity.AccountInfo getClient() {
		return client;
	}

	/**
	 * Sets the client value for this Subscription.
	 * 
	 * @param client
	 */
	public void setClient(com.gop.currency.withdraw.gateway.service.okpay.dto.entity.AccountInfo client) {
		this.client = client;
	}

	/**
	 * Gets the currency value for this Subscription.
	 * 
	 * @return currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * Sets the currency value for this Subscription.
	 * 
	 * @param currency
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * Gets the ID value for this Subscription.
	 * 
	 * @return ID
	 */
	public Long getID() {
		return ID;
	}

	/**
	 * Sets the ID value for this Subscription.
	 * 
	 * @param ID
	 */
	public void setID(Long ID) {
		this.ID = ID;
	}

	/**
	 * Gets the isActive value for this Subscription.
	 * 
	 * @return isActive
	 */
	public Boolean getIsActive() {
		return isActive;
	}

	/**
	 * Sets the isActive value for this Subscription.
	 * 
	 * @param isActive
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * Gets the lastPaymentDateTime value for this Subscription.
	 * 
	 * @return lastPaymentDateTime
	 */
	public String getLastPaymentDateTime() {
		return lastPaymentDateTime;
	}

	/**
	 * Sets the lastPaymentDateTime value for this Subscription.
	 * 
	 * @param lastPaymentDateTime
	 */
	public void setLastPaymentDateTime(String lastPaymentDateTime) {
		this.lastPaymentDateTime = lastPaymentDateTime;
	}

	/**
	 * Gets the merchantWalletID value for this Subscription.
	 * 
	 * @return merchantWalletID
	 */
	public String getMerchantWalletID() {
		return merchantWalletID;
	}

	/**
	 * Sets the merchantWalletID value for this Subscription.
	 * 
	 * @param merchantWalletID
	 */
	public void setMerchantWalletID(String merchantWalletID) {
		this.merchantWalletID = merchantWalletID;
	}

	/**
	 * Gets the nextPaymentDateTime value for this Subscription.
	 * 
	 * @return nextPaymentDateTime
	 */
	public String getNextPaymentDateTime() {
		return nextPaymentDateTime;
	}

	/**
	 * Sets the nextPaymentDateTime value for this Subscription.
	 * 
	 * @param nextPaymentDateTime
	 */
	public void setNextPaymentDateTime(String nextPaymentDateTime) {
		this.nextPaymentDateTime = nextPaymentDateTime;
	}

	/**
	 * Gets the outstandingBalance value for this Subscription.
	 * 
	 * @return outstandingBalance
	 */
	public java.math.BigDecimal getOutstandingBalance() {
		return outstandingBalance;
	}

	/**
	 * Sets the outstandingBalance value for this Subscription.
	 * 
	 * @param outstandingBalance
	 */
	public void setOutstandingBalance(java.math.BigDecimal outstandingBalance) {
		this.outstandingBalance = outstandingBalance;
	}

	/**
	 * Gets the reattemptAcum value for this Subscription.
	 * 
	 * @return reattemptAcum
	 */
	public Boolean getReattemptAcum() {
		return reattemptAcum;
	}

	/**
	 * Sets the reattemptAcum value for this Subscription.
	 * 
	 * @param reattemptAcum
	 */
	public void setReattemptAcum(Boolean reattemptAcum) {
		this.reattemptAcum = reattemptAcum;
	}

	/**
	 * Gets the reattemptDays value for this Subscription.
	 * 
	 * @return reattemptDays
	 */
	public Integer getReattemptDays() {
		return reattemptDays;
	}

	/**
	 * Sets the reattemptDays value for this Subscription.
	 * 
	 * @param reattemptDays
	 */
	public void setReattemptDays(Integer reattemptDays) {
		this.reattemptDays = reattemptDays;
	}

	/**
	 * Gets the regularCount value for this Subscription.
	 * 
	 * @return regularCount
	 */
	public Integer getRegularCount() {
		return regularCount;
	}

	/**
	 * Sets the regularCount value for this Subscription.
	 * 
	 * @param regularCount
	 */
	public void setRegularCount(Integer regularCount) {
		this.regularCount = regularCount;
	}

	/**
	 * Gets the regularCycle value for this Subscription.
	 * 
	 * @return regularCycle
	 */
	public Integer getRegularCycle() {
		return regularCycle;
	}

	/**
	 * Sets the regularCycle value for this Subscription.
	 * 
	 * @param regularCycle
	 */
	public void setRegularCycle(Integer regularCycle) {
		this.regularCycle = regularCycle;
	}

	/**
	 * Gets the regularInterval value for this Subscription.
	 * 
	 * @return regularInterval
	 */
	public com.gop.currency.withdraw.gateway.service.okpay.dto.entity.SubscriptionCycleIntervals getRegularInterval() {
		return regularInterval;
	}

	/**
	 * Sets the regularInterval value for this Subscription.
	 * 
	 * @param regularInterval
	 */
	public void setRegularInterval(com.gop.currency.withdraw.gateway.service.okpay.dto.entity.SubscriptionCycleIntervals regularInterval) {
		this.regularInterval = regularInterval;
	}

	/**
	 * Gets the regularPaymentsDone value for this Subscription.
	 * 
	 * @return regularPaymentsDone
	 */
	public Integer getRegularPaymentsDone() {
		return regularPaymentsDone;
	}

	/**
	 * Sets the regularPaymentsDone value for this Subscription.
	 * 
	 * @param regularPaymentsDone
	 */
	public void setRegularPaymentsDone(Integer regularPaymentsDone) {
		this.regularPaymentsDone = regularPaymentsDone;
	}

	/**
	 * Gets the regularPrice value for this Subscription.
	 * 
	 * @return regularPrice
	 */
	public java.math.BigDecimal getRegularPrice() {
		return regularPrice;
	}

	/**
	 * Sets the regularPrice value for this Subscription.
	 * 
	 * @param regularPrice
	 */
	public void setRegularPrice(java.math.BigDecimal regularPrice) {
		this.regularPrice = regularPrice;
	}

	/**
	 * Gets the retryAt value for this Subscription.
	 * 
	 * @return retryAt
	 */
	public String getRetryAt() {
		return retryAt;
	}

	/**
	 * Sets the retryAt value for this Subscription.
	 * 
	 * @param retryAt
	 */
	public void setRetryAt(String retryAt) {
		this.retryAt = retryAt;
	}

	/**
	 * Gets the setupPrice value for this Subscription.
	 * 
	 * @return setupPrice
	 */
	public java.math.BigDecimal getSetupPrice() {
		return setupPrice;
	}

	/**
	 * Sets the setupPrice value for this Subscription.
	 * 
	 * @param setupPrice
	 */
	public void setSetupPrice(java.math.BigDecimal setupPrice) {
		this.setupPrice = setupPrice;
	}

	/**
	 * Gets the shippingAddress value for this Subscription.
	 * 
	 * @return shippingAddress
	 */
	public com.gop.currency.withdraw.gateway.service.okpay.dto.contract.Address getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * Sets the shippingAddress value for this Subscription.
	 * 
	 * @param shippingAddress
	 */
	public void setShippingAddress(com.gop.currency.withdraw.gateway.service.okpay.dto.contract.Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	/**
	 * Gets the shippingNeeded value for this Subscription.
	 * 
	 * @return shippingNeeded
	 */
	public Boolean getShippingNeeded() {
		return shippingNeeded;
	}

	/**
	 * Sets the shippingNeeded value for this Subscription.
	 * 
	 * @param shippingNeeded
	 */
	public void setShippingNeeded(Boolean shippingNeeded) {
		this.shippingNeeded = shippingNeeded;
	}

	/**
	 * Gets the startDateTime value for this Subscription.
	 * 
	 * @return startDateTime
	 */
	public String getStartDateTime() {
		return startDateTime;
	}

	/**
	 * Sets the startDateTime value for this Subscription.
	 * 
	 * @param startDateTime
	 */
	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	/**
	 * Gets the status value for this Subscription.
	 * 
	 * @return status
	 */
	public com.gop.currency.withdraw.gateway.service.okpay.dto.entity.SubscriptionStatuses getStatus() {
		return status;
	}

	/**
	 * Sets the status value for this Subscription.
	 * 
	 * @param status
	 */
	public void setStatus(com.gop.currency.withdraw.gateway.service.okpay.dto.entity.SubscriptionStatuses status) {
		this.status = status;
	}

	/**
	 * Gets the title value for this Subscription.
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title value for this Subscription.
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the trialCount value for this Subscription.
	 * 
	 * @return trialCount
	 */
	public Integer getTrialCount() {
		return trialCount;
	}

	/**
	 * Sets the trialCount value for this Subscription.
	 * 
	 * @param trialCount
	 */
	public void setTrialCount(Integer trialCount) {
		this.trialCount = trialCount;
	}

	/**
	 * Gets the trialCycle value for this Subscription.
	 * 
	 * @return trialCycle
	 */
	public Integer getTrialCycle() {
		return trialCycle;
	}

	/**
	 * Sets the trialCycle value for this Subscription.
	 * 
	 * @param trialCycle
	 */
	public void setTrialCycle(Integer trialCycle) {
		this.trialCycle = trialCycle;
	}

	/**
	 * Gets the trialInterval value for this Subscription.
	 * 
	 * @return trialInterval
	 */
	public com.gop.currency.withdraw.gateway.service.okpay.dto.entity.SubscriptionCycleIntervals getTrialInterval() {
		return trialInterval;
	}

	/**
	 * Sets the trialInterval value for this Subscription.
	 * 
	 * @param trialInterval
	 */
	public void setTrialInterval(com.gop.currency.withdraw.gateway.service.okpay.dto.entity.SubscriptionCycleIntervals trialInterval) {
		this.trialInterval = trialInterval;
	}

	/**
	 * Gets the trialPaymentsDone value for this Subscription.
	 * 
	 * @return trialPaymentsDone
	 */
	public Integer getTrialPaymentsDone() {
		return trialPaymentsDone;
	}

	/**
	 * Sets the trialPaymentsDone value for this Subscription.
	 * 
	 * @param trialPaymentsDone
	 */
	public void setTrialPaymentsDone(Integer trialPaymentsDone) {
		this.trialPaymentsDone = trialPaymentsDone;
	}

	/**
	 * Gets the trialPrice value for this Subscription.
	 * 
	 * @return trialPrice
	 */
	public java.math.BigDecimal getTrialPrice() {
		return trialPrice;
	}

	/**
	 * Sets the trialPrice value for this Subscription.
	 * 
	 * @param trialPrice
	 */
	public void setTrialPrice(java.math.BigDecimal trialPrice) {
		this.trialPrice = trialPrice;
	}

	/**
	 * Gets the validUntil value for this Subscription.
	 * 
	 * @return validUntil
	 */
	public String getValidUntil() {
		return validUntil;
	}

	/**
	 * Sets the validUntil value for this Subscription.
	 * 
	 * @param validUntil
	 */
	public void setValidUntil(String validUntil) {
		this.validUntil = validUntil;
	}

	private Object __equalsCalc = null;

	public synchronized boolean equals(Object obj) {
		if (!(obj instanceof Subscription))
			return false;
		Subscription other = (Subscription) obj;
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
				&& ((this.client == null && other.getClient() == null)
						|| (this.client != null && this.client.equals(other.getClient())))
				&& ((this.currency == null && other.getCurrency() == null)
						|| (this.currency != null && this.currency.equals(other.getCurrency())))
				&& ((this.ID == null && other.getID() == null) || (this.ID != null && this.ID.equals(other.getID())))
				&& ((this.isActive == null && other.getIsActive() == null)
						|| (this.isActive != null && this.isActive.equals(other.getIsActive())))
				&& ((this.lastPaymentDateTime == null && other.getLastPaymentDateTime() == null)
						|| (this.lastPaymentDateTime != null
								&& this.lastPaymentDateTime.equals(other.getLastPaymentDateTime())))
				&& ((this.merchantWalletID == null && other.getMerchantWalletID() == null)
						|| (this.merchantWalletID != null && this.merchantWalletID.equals(other.getMerchantWalletID())))
				&& ((this.nextPaymentDateTime == null && other.getNextPaymentDateTime() == null)
						|| (this.nextPaymentDateTime != null
								&& this.nextPaymentDateTime.equals(other.getNextPaymentDateTime())))
				&& ((this.outstandingBalance == null && other.getOutstandingBalance() == null)
						|| (this.outstandingBalance != null
								&& this.outstandingBalance.equals(other.getOutstandingBalance())))
				&& ((this.reattemptAcum == null && other.getReattemptAcum() == null)
						|| (this.reattemptAcum != null && this.reattemptAcum.equals(other.getReattemptAcum())))
				&& ((this.reattemptDays == null && other.getReattemptDays() == null)
						|| (this.reattemptDays != null && this.reattemptDays.equals(other.getReattemptDays())))
				&& ((this.regularCount == null && other.getRegularCount() == null)
						|| (this.regularCount != null && this.regularCount.equals(other.getRegularCount())))
				&& ((this.regularCycle == null && other.getRegularCycle() == null)
						|| (this.regularCycle != null && this.regularCycle.equals(other.getRegularCycle())))
				&& ((this.regularInterval == null && other.getRegularInterval() == null)
						|| (this.regularInterval != null && this.regularInterval.equals(other.getRegularInterval())))
				&& ((this.regularPaymentsDone == null && other.getRegularPaymentsDone() == null)
						|| (this.regularPaymentsDone != null
								&& this.regularPaymentsDone.equals(other.getRegularPaymentsDone())))
				&& ((this.regularPrice == null && other.getRegularPrice() == null)
						|| (this.regularPrice != null && this.regularPrice.equals(other.getRegularPrice())))
				&& ((this.retryAt == null && other.getRetryAt() == null)
						|| (this.retryAt != null && this.retryAt.equals(other.getRetryAt())))
				&& ((this.setupPrice == null && other.getSetupPrice() == null)
						|| (this.setupPrice != null && this.setupPrice.equals(other.getSetupPrice())))
				&& ((this.shippingAddress == null && other.getShippingAddress() == null)
						|| (this.shippingAddress != null && this.shippingAddress.equals(other.getShippingAddress())))
				&& ((this.shippingNeeded == null && other.getShippingNeeded() == null)
						|| (this.shippingNeeded != null && this.shippingNeeded.equals(other.getShippingNeeded())))
				&& ((this.startDateTime == null && other.getStartDateTime() == null)
						|| (this.startDateTime != null && this.startDateTime.equals(other.getStartDateTime())))
				&& ((this.status == null && other.getStatus() == null)
						|| (this.status != null && this.status.equals(other.getStatus())))
				&& ((this.title == null && other.getTitle() == null)
						|| (this.title != null && this.title.equals(other.getTitle())))
				&& ((this.trialCount == null && other.getTrialCount() == null)
						|| (this.trialCount != null && this.trialCount.equals(other.getTrialCount())))
				&& ((this.trialCycle == null && other.getTrialCycle() == null)
						|| (this.trialCycle != null && this.trialCycle.equals(other.getTrialCycle())))
				&& ((this.trialInterval == null && other.getTrialInterval() == null)
						|| (this.trialInterval != null && this.trialInterval.equals(other.getTrialInterval())))
				&& ((this.trialPaymentsDone == null && other.getTrialPaymentsDone() == null)
						|| (this.trialPaymentsDone != null
								&& this.trialPaymentsDone.equals(other.getTrialPaymentsDone())))
				&& ((this.trialPrice == null && other.getTrialPrice() == null)
						|| (this.trialPrice != null && this.trialPrice.equals(other.getTrialPrice())))
				&& ((this.validUntil == null && other.getValidUntil() == null)
						|| (this.validUntil != null && this.validUntil.equals(other.getValidUntil())));
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
		if (getClient() != null) {
			_hashCode += getClient().hashCode();
		}
		if (getCurrency() != null) {
			_hashCode += getCurrency().hashCode();
		}
		if (getID() != null) {
			_hashCode += getID().hashCode();
		}
		if (getIsActive() != null) {
			_hashCode += getIsActive().hashCode();
		}
		if (getLastPaymentDateTime() != null) {
			_hashCode += getLastPaymentDateTime().hashCode();
		}
		if (getMerchantWalletID() != null) {
			_hashCode += getMerchantWalletID().hashCode();
		}
		if (getNextPaymentDateTime() != null) {
			_hashCode += getNextPaymentDateTime().hashCode();
		}
		if (getOutstandingBalance() != null) {
			_hashCode += getOutstandingBalance().hashCode();
		}
		if (getReattemptAcum() != null) {
			_hashCode += getReattemptAcum().hashCode();
		}
		if (getReattemptDays() != null) {
			_hashCode += getReattemptDays().hashCode();
		}
		if (getRegularCount() != null) {
			_hashCode += getRegularCount().hashCode();
		}
		if (getRegularCycle() != null) {
			_hashCode += getRegularCycle().hashCode();
		}
		if (getRegularInterval() != null) {
			_hashCode += getRegularInterval().hashCode();
		}
		if (getRegularPaymentsDone() != null) {
			_hashCode += getRegularPaymentsDone().hashCode();
		}
		if (getRegularPrice() != null) {
			_hashCode += getRegularPrice().hashCode();
		}
		if (getRetryAt() != null) {
			_hashCode += getRetryAt().hashCode();
		}
		if (getSetupPrice() != null) {
			_hashCode += getSetupPrice().hashCode();
		}
		if (getShippingAddress() != null) {
			_hashCode += getShippingAddress().hashCode();
		}
		if (getShippingNeeded() != null) {
			_hashCode += getShippingNeeded().hashCode();
		}
		if (getStartDateTime() != null) {
			_hashCode += getStartDateTime().hashCode();
		}
		if (getStatus() != null) {
			_hashCode += getStatus().hashCode();
		}
		if (getTitle() != null) {
			_hashCode += getTitle().hashCode();
		}
		if (getTrialCount() != null) {
			_hashCode += getTrialCount().hashCode();
		}
		if (getTrialCycle() != null) {
			_hashCode += getTrialCycle().hashCode();
		}
		if (getTrialInterval() != null) {
			_hashCode += getTrialInterval().hashCode();
		}
		if (getTrialPaymentsDone() != null) {
			_hashCode += getTrialPaymentsDone().hashCode();
		}
		if (getTrialPrice() != null) {
			_hashCode += getTrialPrice().hashCode();
		}
		if (getValidUntil() != null) {
			_hashCode += getValidUntil().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static TypeDesc typeDesc = new TypeDesc(Subscription.class, true);

	static {
		typeDesc.setXmlType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "Subscription"));
		org.apache.axis.description.ElementDesc elemField = new ElementDesc();
		elemField.setFieldName("client");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "Client"));
		elemField.setXmlType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "AccountInfo"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("currency");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "Currency"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("ID");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "ID"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("isActive");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "IsActive"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("lastPaymentDateTime");
		elemField.setXmlName(
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "LastPaymentDateTime"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("merchantWalletID");
		elemField.setXmlName(
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "MerchantWalletID"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("nextPaymentDateTime");
		elemField.setXmlName(
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "NextPaymentDateTime"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("outstandingBalance");
		elemField.setXmlName(
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "OutstandingBalance"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("reattemptAcum");
		elemField.setXmlName(
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "ReattemptAcum"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("reattemptDays");
		elemField.setXmlName(
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "ReattemptDays"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("regularCount");
		elemField
				.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "RegularCount"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("regularCycle");
		elemField
				.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "RegularCycle"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("regularInterval");
		elemField.setXmlName(
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "RegularInterval"));
		elemField.setXmlType(
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "SubscriptionCycleIntervals"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("regularPaymentsDone");
		elemField.setXmlName(
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "RegularPaymentsDone"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("regularPrice");
		elemField
				.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "RegularPrice"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("retryAt");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "RetryAt"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("setupPrice");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "SetupPrice"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("shippingAddress");
		elemField.setXmlName(
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "ShippingAddress"));
		elemField.setXmlType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "Address"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("shippingNeeded");
		elemField.setXmlName(
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "ShippingNeeded"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("startDateTime");
		elemField.setXmlName(
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "StartDateTime"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("status");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "Status"));
		elemField.setXmlType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "SubscriptionStatuses"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("title");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "Title"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("trialCount");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "TrialCount"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("trialCycle");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "TrialCycle"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("trialInterval");
		elemField.setXmlName(
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "TrialInterval"));
		elemField.setXmlType(
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "SubscriptionCycleIntervals"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("trialPaymentsDone");
		elemField.setXmlName(
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "TrialPaymentsDone"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("trialPrice");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "TrialPrice"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("validUntil");
		elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "ValidUntil"));
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
