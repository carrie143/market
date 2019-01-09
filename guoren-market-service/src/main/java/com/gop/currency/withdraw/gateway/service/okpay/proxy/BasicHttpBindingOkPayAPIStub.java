/**
 * BasicHttpBinding_I_OkPayAPIStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gop.currency.withdraw.gateway.service.okpay.proxy;

import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Vector;

import javax.xml.namespace.QName;

import org.apache.axis.AxisFault;
import org.apache.axis.client.Stub;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;
import org.apache.axis.encoding.ser.ArrayDeserializerFactory;
import org.apache.axis.encoding.ser.ArraySerializerFactory;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.apache.axis.encoding.ser.EnumDeserializerFactory;
import org.apache.axis.encoding.ser.EnumSerializerFactory;
//import org.apache.axis.encoding.ser.SimpleDeserializerFactory;
//import org.apache.axis.encoding.ser.SimpleListDeserializerFactory;
//import org.apache.axis.encoding.ser.SimpleListSerializerFactory;
//import org.apache.axis.encoding.ser.SimpleSerializerFactory;

import com.gop.currency.withdraw.gateway.service.okpay.dto.contract.Address;
import com.gop.currency.withdraw.gateway.service.okpay.dto.contract.PreapprovedPayment;
import com.gop.currency.withdraw.gateway.service.okpay.dto.contract.Subscription;
import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.AccountInfo;
import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.AddressTypes;
import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.Balance;
import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.HistoryInfo;
import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.OperationStatus;
import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.PreapprovedPaymentStatuses;
import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.SubscriptionCycleIntervals;
import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.SubscriptionStatuses;
import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.TransactionInfo;
import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.Withdrawal_Info;
import com.gop.currency.withdraw.gateway.service.okpay.dto.status.ClientStatus;
import com.gop.currency.withdraw.gateway.service.okpay.dto.status.VerificationStatuses;

public class BasicHttpBindingOkPayAPIStub extends Stub implements OkPayAPI {
	private Vector<Object> cachedSerClasses = new Vector<Object>();
	private Vector<Object> cachedSerQNames = new Vector<Object>();
	private Vector<Object> cachedSerFactories = new Vector<Object>();
	private Vector<Object> cachedDeserFactories = new Vector<Object>();

	static OperationDesc[] _operations;

	static {
		_operations = new OperationDesc[26];
		_initOperationDesc1();
		_initOperationDesc2();
		_initOperationDesc3();
	}

	private static void _initOperationDesc1() {
		OperationDesc oper;
		ParameterDesc param;
		oper = new OperationDesc();
		oper.setName("Get_Date_Time");
		oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		oper.setReturnClass(String.class);
		oper.setReturnQName(new QName("https://api.okpay.com", "Get_Date_TimeResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[0] = oper;

		oper = new OperationDesc();
		oper.setName("Wallet_Get_Balance");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "ArrayOfBalance"));
		oper.setReturnClass(Balance[].class);
		oper.setReturnQName(new QName("https://api.okpay.com", "Wallet_Get_BalanceResult"));
		param = oper.getReturnParamDesc();
		param.setItemQName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Balance"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[1] = oper;

		oper = new OperationDesc();
		oper.setName("Wallet_Get_Currency_Balance");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Currency"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Balance"));
		oper.setReturnClass(Balance.class);
		oper.setReturnQName(new QName("https://api.okpay.com", "Wallet_Get_Currency_BalanceResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[2] = oper;

		oper = new OperationDesc();
		oper.setName("Send_Money");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Receiver"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Currency"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Amount"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "decimal"), java.math.BigDecimal.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Comment"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "IsReceiverPaysFees"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "boolean"), Boolean.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Invoice"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "TransactionInfo"));
		oper.setReturnClass(TransactionInfo.class);
		oper.setReturnQName(new QName("https://api.okpay.com", "Send_MoneyResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[3] = oper;

		oper = new OperationDesc();
		oper.setName("Transaction_Get");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "TxnID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Invoice"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "TransactionInfo"));
		oper.setReturnClass(TransactionInfo.class);
		oper.setReturnQName(new QName("https://api.okpay.com", "Transaction_GetResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[4] = oper;

		oper = new OperationDesc();
		oper.setName("Transaction_History");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "From"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Till"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "PageSize"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "int"), Integer.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "PageNumber"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "int"), Integer.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "HistoryInfo"));
		oper.setReturnClass(HistoryInfo.class);
		oper.setReturnQName(new QName("https://api.okpay.com", "Transaction_HistoryResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[5] = oper;

		oper = new OperationDesc();
		oper.setName("DebitCard_Prepay");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Email"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Currency"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "IsCourierDelivery"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "boolean"), Boolean.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Comment"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
		oper.setReturnClass(Long.class);
		oper.setReturnQName(new QName("https://api.okpay.com", "DebitCard_PrepayResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[6] = oper;

		oper = new OperationDesc();
		oper.setName("Account_Check");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Account"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
		oper.setReturnClass(Long.class);
		oper.setReturnQName(new QName("https://api.okpay.com", "Account_CheckResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[7] = oper;

		oper = new OperationDesc();
		oper.setName("Withdraw_To_Ecurrency_Calculate");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "PaymentMethod"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Amount"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "decimal"), java.math.BigDecimal.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Currency"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "FeesFromAmount"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "boolean"), Boolean.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Withdrawal_Info"));
		oper.setReturnClass(Withdrawal_Info.class);
		oper.setReturnQName(new QName("https://api.okpay.com", "Withdraw_To_Ecurrency_CalculateResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[8] = oper;

		oper = new OperationDesc();
		oper.setName("Withdraw_To_Ecurrency");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "PaymentMethod"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "PaySystemAccount"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Amount"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "decimal"), java.math.BigDecimal.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Currency"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "FeesFromAmount"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "boolean"), Boolean.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Invoice"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Withdrawal_Info"));
		oper.setReturnClass(Withdrawal_Info.class);
		oper.setReturnQName(new QName("https://api.okpay.com", "Withdraw_To_EcurrencyResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[9] = oper;

	}

	private static void _initOperationDesc2() {
		OperationDesc oper;
		ParameterDesc param;
		oper = new OperationDesc();
		oper.setName("Withdraw_To_Card_RU");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "CardNumber"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Amount"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "decimal"), java.math.BigDecimal.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Invoice"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Withdrawal_Info"));
		oper.setReturnClass(Withdrawal_Info.class);
		oper.setReturnQName(new QName("https://api.okpay.com", "Withdraw_To_Card_RUResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[10] = oper;

		oper = new OperationDesc();
		oper.setName("Withdraw_To_Card_RU_Full");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "CardNumber"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Amount"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "decimal"), java.math.BigDecimal.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "FirstName"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "LastName"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "MidName"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Phone"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "DocumentNumber"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecodDocumentType"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecondDocumentNumber"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Invoice"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Withdrawal_Info"));
		oper.setReturnClass(Withdrawal_Info.class);
		oper.setReturnQName(new QName("https://api.okpay.com", "Withdraw_To_Card_RU_FullResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[11] = oper;

		oper = new OperationDesc();
		oper.setName("Withdraw_To_Card_World");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "CardNumber"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Currency"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Amount"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "decimal"), java.math.BigDecimal.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "ExpiryMonth"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "ExpiryYear"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Invoice"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Withdrawal_Info"));
		oper.setReturnClass(Withdrawal_Info.class);
		oper.setReturnQName(new QName("https://api.okpay.com", "Withdraw_To_Card_WorldResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[12] = oper;

		oper = new OperationDesc();
		oper.setName("Withdraw_To_CryptoCurrency_Calculate");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "PaymentMethod"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Amount"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "decimal"), java.math.BigDecimal.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Currency"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Withdrawal_Info"));
		oper.setReturnClass(Withdrawal_Info.class);
		oper.setReturnQName(new QName("https://api.okpay.com", "Withdraw_To_CryptoCurrency_CalculateResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[13] = oper;

		oper = new OperationDesc();
		oper.setName("Withdraw_To_CryptoCurrency");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "PaymentMethod"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "PaySystemAccount"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Amount"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "decimal"), java.math.BigDecimal.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Currency"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Invoice"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Withdrawal_Info"));
		oper.setReturnClass(Withdrawal_Info.class);
		oper.setReturnQName(new QName("https://api.okpay.com", "Withdraw_To_CryptoCurrencyResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[14] = oper;

		oper = new OperationDesc();
		oper.setName("EX_Account_Check");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Account"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
		oper.setReturnClass(Long.class);
		oper.setReturnQName(new QName("https://api.okpay.com", "EX_Account_CheckResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[15] = oper;

		oper = new OperationDesc();
		oper.setName("EX_Client_Check_Status");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Email"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://schemas.datacontract.org/2004/07/OkPayLink.OkPayService", "ClientStatus"));
		oper.setReturnClass(ClientStatus.class);
		oper.setReturnQName(new QName("https://api.okpay.com", "EX_Client_Check_StatusResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[16] = oper;

		oper = new OperationDesc();
		oper.setName("Subscription_Get");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SubscriptionID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "Subscription"));
		oper.setReturnClass(Subscription.class);
		oper.setReturnQName(new QName("https://api.okpay.com", "Subscription_GetResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[17] = oper;

		oper = new OperationDesc();
		oper.setName("Subscriptions_Filter");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Title"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "From"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Till"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Statuses"), ParameterDesc.IN,
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "ArrayOfSubscriptionStatuses"),
				SubscriptionStatuses[].class, false, false);
		param.setItemQName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "SubscriptionStatuses"));
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		oper.setReturnType(
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "ArrayOfSubscription"));
		oper.setReturnClass(Subscription[].class);
		oper.setReturnQName(new QName("https://api.okpay.com", "Subscriptions_FilterResult"));
		param = oper.getReturnParamDesc();
		param.setItemQName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "Subscription"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[18] = oper;

		oper = new OperationDesc();
		oper.setName("Subscription_Update");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Sub"), ParameterDesc.IN,
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "Subscription"),
				Subscription.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Comment"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "Subscription"));
		oper.setReturnClass(Subscription.class);
		oper.setReturnQName(new QName("https://api.okpay.com", "Subscription_UpdateResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[19] = oper;

	}

	private static void _initOperationDesc3() {
		OperationDesc oper;
		ParameterDesc param;
		oper = new OperationDesc();
		oper.setName("Subscription_Get_Operations");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SubscriptionID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "ArrayOfTransactionInfo"));
		oper.setReturnClass(TransactionInfo[].class);
		oper.setReturnQName(new QName("https://api.okpay.com", "Subscription_Get_OperationsResult"));
		param = oper.getReturnParamDesc();
		param.setItemQName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "TransactionInfo"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[20] = oper;

		oper = new OperationDesc();
		oper.setName("PreapprovedPayment_Get");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "PreapprovedPaymentID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "PreapprovedPayment"));
		oper.setReturnClass(PreapprovedPayment.class);
		oper.setReturnQName(new QName("https://api.okpay.com", "PreapprovedPayment_GetResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[21] = oper;

		oper = new OperationDesc();
		oper.setName("PreapprovedPayment_Charge");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "PreapprovedPaymentID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Amount"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "decimal"), java.math.BigDecimal.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Comment"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Receiver"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "TransactionInfo"));
		oper.setReturnClass(TransactionInfo.class);
		oper.setReturnQName(new QName("https://api.okpay.com", "PreapprovedPayment_ChargeResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[22] = oper;

		oper = new OperationDesc();
		oper.setName("PreapprovedPayment_Cancel");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "PreapprovedPaymentID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Comment"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		oper.setReturnType(
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "PreapprovedPayment"));
		oper.setReturnClass(PreapprovedPayment.class);
		oper.setReturnQName(new QName("https://api.okpay.com", "PreapprovedPayment_CancelResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[23] = oper;

		oper = new OperationDesc();
		oper.setName("PreapprovedPayment_Get_Operations");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "PreapprovedPaymentID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "ArrayOfTransactionInfo"));
		oper.setReturnClass(TransactionInfo[].class);
		oper.setReturnQName(new QName("https://api.okpay.com", "PreapprovedPayment_Get_OperationsResult"));
		param = oper.getReturnParamDesc();
		param.setItemQName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "TransactionInfo"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[24] = oper;

		oper = new OperationDesc();
		oper.setName("PreapprovedPayments_Filter");
		param = new ParameterDesc(new QName("https://api.okpay.com", "WalletID"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "SecurityToken"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Title"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Invoice"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Client"), ParameterDesc.IN,
				new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new QName("https://api.okpay.com", "Statuses"), ParameterDesc.IN,
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "ArrayOfPreapprovedPaymentStatuses"),
				PreapprovedPaymentStatuses[].class, false, false);
		param.setItemQName(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "PreapprovedPaymentStatuses"));
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract",
				"ArrayOfPreapprovedPayment"));
		oper.setReturnClass(PreapprovedPayment[].class);
		oper.setReturnQName(new QName("https://api.okpay.com", "PreapprovedPayments_FilterResult"));
		param = oper.getReturnParamDesc();
		param.setItemQName(
				new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "PreapprovedPayment"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[25] = oper;

	}

	public BasicHttpBindingOkPayAPIStub() throws org.apache.axis.AxisFault {
		this(null);
	}

	public BasicHttpBindingOkPayAPIStub(java.net.URL endpointURL, javax.xml.rpc.Service service)
			throws org.apache.axis.AxisFault {
		this(service);
		super.cachedEndpoint = endpointURL;
	}

	public BasicHttpBindingOkPayAPIStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
		if (service == null) {
			super.service = new org.apache.axis.client.Service();
		} else {
			super.service = service;
		}
		((org.apache.axis.client.Service) super.service).setTypeMappingVersion("1.2");
		Class<?> cls;
		QName qName;
		QName qName2;
		Class<?> beansf = BeanSerializerFactory.class;
		Class<?> beandf = BeanDeserializerFactory.class;
		Class<?> enumsf = EnumSerializerFactory.class;
		Class<?> enumdf = EnumDeserializerFactory.class;
		// Class<?> arraysf = ArraySerializerFactory.class;
		// Class<?> arraydf = ArrayDeserializerFactory.class;
		// Class<?> simplesf = SimpleSerializerFactory.class;
		// Class<?> simpledf = SimpleDeserializerFactory.class;
		// Class<?> simplelistsf = SimpleListSerializerFactory.class;
		// Class<?> simplelistdf = SimpleListDeserializerFactory.class;
		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "Address");
		cachedSerQNames.add(qName);
		cls = Address.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "ArrayOfPreapprovedPayment");
		cachedSerQNames.add(qName);
		cls = PreapprovedPayment[].class;
		cachedSerClasses.add(cls);
		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "PreapprovedPayment");
		qName2 = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "PreapprovedPayment");
		cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
		cachedDeserFactories.add(new ArrayDeserializerFactory());

		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "ArrayOfSubscription");
		cachedSerQNames.add(qName);
		cls = Subscription[].class;
		cachedSerClasses.add(cls);
		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "Subscription");
		qName2 = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "Subscription");
		cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
		cachedDeserFactories.add(new ArrayDeserializerFactory());

		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "PreapprovedPayment");
		cachedSerQNames.add(qName);
		cls = PreapprovedPayment.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI.DataContract", "Subscription");
		cachedSerQNames.add(qName);
		cls = Subscription.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "AccountInfo");
		cachedSerQNames.add(qName);
		cls = AccountInfo.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "AddressTypes");
		cachedSerQNames.add(qName);
		cls = AddressTypes.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(enumsf);
		cachedDeserFactories.add(enumdf);

		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "ArrayOfBalance");
		cachedSerQNames.add(qName);
		cls = Balance[].class;
		cachedSerClasses.add(cls);
		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Balance");
		qName2 = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Balance");
		cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
		cachedDeserFactories.add(new ArrayDeserializerFactory());

		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "ArrayOfPreapprovedPaymentStatuses");
		cachedSerQNames.add(qName);
		cls = PreapprovedPaymentStatuses[].class;
		cachedSerClasses.add(cls);
		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "PreapprovedPaymentStatuses");
		qName2 = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "PreapprovedPaymentStatuses");
		cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
		cachedDeserFactories.add(new ArrayDeserializerFactory());

		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "ArrayOfSubscriptionStatuses");
		cachedSerQNames.add(qName);
		cls = SubscriptionStatuses[].class;
		cachedSerClasses.add(cls);
		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "SubscriptionStatuses");
		qName2 = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "SubscriptionStatuses");
		cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
		cachedDeserFactories.add(new ArrayDeserializerFactory());

		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "ArrayOfTransactionInfo");
		cachedSerQNames.add(qName);
		cls = TransactionInfo[].class;
		cachedSerClasses.add(cls);
		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "TransactionInfo");
		qName2 = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "TransactionInfo");
		cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
		cachedDeserFactories.add(new ArrayDeserializerFactory());

		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Balance");
		cachedSerQNames.add(qName);
		cls = Balance.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "HistoryInfo");
		cachedSerQNames.add(qName);
		cls = HistoryInfo.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "OperationStatus");
		cachedSerQNames.add(qName);
		cls = OperationStatus.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(enumsf);
		cachedDeserFactories.add(enumdf);

		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "PreapprovedPaymentStatuses");
		cachedSerQNames.add(qName);
		cls = PreapprovedPaymentStatuses.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(enumsf);
		cachedDeserFactories.add(enumdf);

		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "SubscriptionCycleIntervals");
		cachedSerQNames.add(qName);
		cls = SubscriptionCycleIntervals.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(enumsf);
		cachedDeserFactories.add(enumdf);

		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "SubscriptionStatuses");
		cachedSerQNames.add(qName);
		cls = SubscriptionStatuses.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(enumsf);
		cachedDeserFactories.add(enumdf);

		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "TransactionInfo");
		cachedSerQNames.add(qName);
		cls = TransactionInfo.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayAPI", "Withdrawal_Info");
		cachedSerQNames.add(qName);
		cls = Withdrawal_Info.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayLink.OkPayService", "ClientStatus");
		cachedSerQNames.add(qName);
		cls = ClientStatus.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(enumsf);
		cachedDeserFactories.add(enumdf);

		qName = new QName("http://schemas.datacontract.org/2004/07/OkPayLink.OkPayService", "VerificationStatuses");
		cachedSerQNames.add(qName);
		cls = VerificationStatuses.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(enumsf);
		cachedDeserFactories.add(enumdf);

	}

	protected org.apache.axis.client.Call createCall() throws RemoteException {
		try {
			org.apache.axis.client.Call _call = super._createCall();
			if (super.maintainSessionSet) {
				_call.setMaintainSession(super.maintainSession);
			}
			if (super.cachedUsername != null) {
				_call.setUsername(super.cachedUsername);
			}
			if (super.cachedPassword != null) {
				_call.setPassword(super.cachedPassword);
			}
			if (super.cachedEndpoint != null) {
				_call.setTargetEndpointAddress(super.cachedEndpoint);
			}
			if (super.cachedTimeout != null) {
				_call.setTimeout(super.cachedTimeout);
			}
			if (super.cachedPortName != null) {
				_call.setPortName(super.cachedPortName);
			}
			Enumeration<Object> keys = super.cachedProperties.keys();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				_call.setProperty(key, super.cachedProperties.get(key));
			}
			// All the type mapping information is registered
			// when the first call is made.
			// The type mapping information is actually registered in
			// the TypeMappingRegistry of the service, which
			// is the reason why registration is only needed for the first call.
			synchronized (this) {
				if (firstCall()) {
					// must set encoding style before registering serializers
					_call.setEncodingStyle(null);
					for (int i = 0; i < cachedSerFactories.size(); ++i) {
						Class<?> cls = (Class<?>) cachedSerClasses.get(i);
						QName qName = (QName) cachedSerQNames.get(i);
						Object x = cachedSerFactories.get(i);
						if (x instanceof Class) {
							Class<?> sf = (Class<?>) cachedSerFactories.get(i);
							Class<?> df = (Class<?>) cachedDeserFactories.get(i);
							_call.registerTypeMapping(cls, qName, sf, df, false);
						} else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
							org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory) cachedSerFactories
									.get(i);
							org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory) cachedDeserFactories
									.get(i);
							_call.registerTypeMapping(cls, qName, sf, df, false);
						}
					}
				}
			}
			return _call;
		} catch (Throwable _t) {
			throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
		}
	}

	public String get_Date_Time() throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[0]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/Get_Date_Time");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "Get_Date_Time"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] {});

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (String) _resp;
				} catch (Exception _exception) {
					return (String) org.apache.axis.utils.JavaUtils.convert(_resp, String.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public Balance[] wallet_Get_Balance(String walletID, String securityToken) throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[1]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/Wallet_Get_Balance");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "Wallet_Get_Balance"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { walletID, securityToken });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (Balance[]) _resp;
				} catch (Exception _exception) {
					return (Balance[]) org.apache.axis.utils.JavaUtils.convert(_resp, Balance[].class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public Balance wallet_Get_Currency_Balance(String walletID, String securityToken, String currency)
			throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[2]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/Wallet_Get_Currency_Balance");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "Wallet_Get_Currency_Balance"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { walletID, securityToken, currency });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (Balance) _resp;
				} catch (Exception _exception) {
					return (Balance) org.apache.axis.utils.JavaUtils.convert(_resp, Balance.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public TransactionInfo send_Money(String walletID, String securityToken, String receiver, String currency,
			java.math.BigDecimal amount, String comment, Boolean isReceiverPaysFees, String invoice)
			throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[3]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/Send_Money");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "Send_Money"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { walletID, securityToken, receiver, currency, amount, comment,
					isReceiverPaysFees, invoice });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (TransactionInfo) _resp;
				} catch (Exception _exception) {
					return (TransactionInfo) org.apache.axis.utils.JavaUtils.convert(_resp, TransactionInfo.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public TransactionInfo transaction_Get(String walletID, String securityToken, Long txnID, String invoice)
			throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[4]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/Transaction_Get");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "Transaction_Get"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { walletID, securityToken, txnID, invoice });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (TransactionInfo) _resp;
				} catch (Exception _exception) {
					return (TransactionInfo) org.apache.axis.utils.JavaUtils.convert(_resp, TransactionInfo.class);
				}
			}
		} catch (AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public HistoryInfo transaction_History(String walletID, String securityToken, String from, String till,
			Integer pageSize, Integer pageNumber) throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[5]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/Transaction_History");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "Transaction_History"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { walletID, securityToken, from, till, pageSize, pageNumber });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (HistoryInfo) _resp;
				} catch (Exception _exception) {
					return (HistoryInfo) org.apache.axis.utils.JavaUtils.convert(_resp, HistoryInfo.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public Long debitCard_Prepay(String walletID, String securityToken, String email, String currency,
			Boolean isCourierDelivery, String comment) throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[6]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/DebitCard_Prepay");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "DebitCard_Prepay"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call
					.invoke(new Object[] { walletID, securityToken, email, currency, isCourierDelivery, comment });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (Long) _resp;
				} catch (Exception _exception) {
					return (Long) org.apache.axis.utils.JavaUtils.convert(_resp, Long.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public Long account_Check(String walletID, String securityToken, String account) throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[7]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/Account_Check");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "Account_Check"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { walletID, securityToken, account });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (Long) _resp;
				} catch (Exception _exception) {
					return (Long) org.apache.axis.utils.JavaUtils.convert(_resp, Long.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public Withdrawal_Info withdraw_To_Ecurrency_Calculate(String walletID, String securityToken, String paymentMethod,
			java.math.BigDecimal amount, String currency, Boolean feesFromAmount) throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[8]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/Withdraw_To_Ecurrency_Calculate");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "Withdraw_To_Ecurrency_Calculate"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call
					.invoke(new Object[] { walletID, securityToken, paymentMethod, amount, currency, feesFromAmount });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (Withdrawal_Info) _resp;
				} catch (Exception _exception) {
					return (Withdrawal_Info) org.apache.axis.utils.JavaUtils.convert(_resp, Withdrawal_Info.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public Withdrawal_Info withdraw_To_Ecurrency(String walletID, String securityToken, String paymentMethod,
			String paySystemAccount, java.math.BigDecimal amount, String currency, Boolean feesFromAmount,
			String invoice) throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[9]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/Withdraw_To_Ecurrency");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "Withdraw_To_Ecurrency"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { walletID, securityToken, paymentMethod, paySystemAccount, amount,
					currency, feesFromAmount, invoice });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (Withdrawal_Info) _resp;
				} catch (Exception _exception) {
					return (Withdrawal_Info) org.apache.axis.utils.JavaUtils.convert(_resp, Withdrawal_Info.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public Withdrawal_Info withdraw_To_Card_RU(String walletID, String securityToken, String cardNumber,
			java.math.BigDecimal amount, String invoice) throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[10]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/Withdraw_To_Card_RU");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "Withdraw_To_Card_RU"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { walletID, securityToken, cardNumber, amount, invoice });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (Withdrawal_Info) _resp;
				} catch (Exception _exception) {
					return (Withdrawal_Info) org.apache.axis.utils.JavaUtils.convert(_resp, Withdrawal_Info.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public Withdrawal_Info withdraw_To_Card_RU_Full(String walletID, String securityToken, String cardNumber,
			java.math.BigDecimal amount, String firstName, String lastName, String midName, String phone,
			String documentNumber, String secodDocumentType, String secondDocumentNumber, String invoice)
			throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[11]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/Withdraw_To_Card_RU_Full");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "Withdraw_To_Card_RU_Full"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { walletID, securityToken, cardNumber, amount, firstName, lastName,
					midName, phone, documentNumber, secodDocumentType, secondDocumentNumber, invoice });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (Withdrawal_Info) _resp;
				} catch (Exception _exception) {
					return (Withdrawal_Info) org.apache.axis.utils.JavaUtils.convert(_resp, Withdrawal_Info.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public Withdrawal_Info withdraw_To_Card_World(String walletID, String securityToken, String cardNumber,
			String currency, java.math.BigDecimal amount, String expiryMonth, String expiryYear, String invoice)
			throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[12]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/Withdraw_To_Card_World");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "Withdraw_To_Card_World"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { walletID, securityToken, cardNumber, currency, amount,
					expiryMonth, expiryYear, invoice });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (Withdrawal_Info) _resp;
				} catch (Exception _exception) {
					return (Withdrawal_Info) org.apache.axis.utils.JavaUtils.convert(_resp, Withdrawal_Info.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public Withdrawal_Info withdraw_To_CryptoCurrency_Calculate(String walletID, String securityToken,
			String paymentMethod, java.math.BigDecimal amount, String currency) throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[13]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/Withdraw_To_CryptoCurrency_Calculate");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "Withdraw_To_CryptoCurrency_Calculate"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { walletID, securityToken, paymentMethod, amount, currency });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (Withdrawal_Info) _resp;
				} catch (Exception _exception) {
					return (Withdrawal_Info) org.apache.axis.utils.JavaUtils.convert(_resp, Withdrawal_Info.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public Withdrawal_Info withdraw_To_CryptoCurrency(String walletID, String securityToken, String paymentMethod,
			String paySystemAccount, java.math.BigDecimal amount, String currency, String invoice)
			throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[14]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/Withdraw_To_CryptoCurrency");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "Withdraw_To_CryptoCurrency"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { walletID, securityToken, paymentMethod, paySystemAccount, amount,
					currency, invoice });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (Withdrawal_Info) _resp;
				} catch (Exception _exception) {
					return (Withdrawal_Info) org.apache.axis.utils.JavaUtils.convert(_resp, Withdrawal_Info.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public Long EX_Account_Check(String walletID, String securityToken, String account) throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[15]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/EX_Account_Check");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "EX_Account_Check"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { walletID, securityToken, account });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (Long) _resp;
				} catch (Exception _exception) {
					return (Long) org.apache.axis.utils.JavaUtils.convert(_resp, Long.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public ClientStatus EX_Client_Check_Status(String walletID, String securityToken, String email)
			throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[16]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/EX_Client_Check_Status");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "EX_Client_Check_Status"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { walletID, securityToken, email });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ClientStatus) _resp;
				} catch (Exception _exception) {
					return (ClientStatus) org.apache.axis.utils.JavaUtils.convert(_resp, ClientStatus.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public Subscription subscription_Get(String walletID, String securityToken, Long subscriptionID)
			throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[17]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/Subscription_Get");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "Subscription_Get"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { walletID, securityToken, subscriptionID });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (Subscription) _resp;
				} catch (Exception _exception) {
					return (Subscription) org.apache.axis.utils.JavaUtils.convert(_resp, Subscription.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public Subscription[] subscriptions_Filter(String walletID, String securityToken, String title, String from,
			String till, SubscriptionStatuses[] statuses) throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[18]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/Subscriptions_Filter");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "Subscriptions_Filter"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { walletID, securityToken, title, from, till, statuses });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (Subscription[]) _resp;
				} catch (Exception _exception) {
					return (Subscription[]) org.apache.axis.utils.JavaUtils.convert(_resp, Subscription[].class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public Subscription subscription_Update(String walletID, String securityToken, Subscription sub, String comment)
			throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[19]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/Subscription_Update");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "Subscription_Update"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { walletID, securityToken, sub, comment });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (Subscription) _resp;
				} catch (Exception _exception) {
					return (Subscription) org.apache.axis.utils.JavaUtils.convert(_resp, Subscription.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public TransactionInfo[] subscription_Get_Operations(String walletID, String securityToken, Long subscriptionID)
			throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[20]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/Subscription_Get_Operations");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "Subscription_Get_Operations"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { walletID, securityToken, subscriptionID });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (TransactionInfo[]) _resp;
				} catch (Exception _exception) {
					return (TransactionInfo[]) org.apache.axis.utils.JavaUtils.convert(_resp, TransactionInfo[].class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public PreapprovedPayment preapprovedPayment_Get(String walletID, String securityToken, Long preapprovedPaymentID)
			throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[21]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/PreapprovedPayment_Get");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "PreapprovedPayment_Get"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { walletID, securityToken, preapprovedPaymentID });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (PreapprovedPayment) _resp;
				} catch (Exception _exception) {
					return (PreapprovedPayment) org.apache.axis.utils.JavaUtils.convert(_resp,
							PreapprovedPayment.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public TransactionInfo preapprovedPayment_Charge(String walletID, String securityToken, Long preapprovedPaymentID,
			java.math.BigDecimal amount, String comment, String receiver) throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[22]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/PreapprovedPayment_Charge");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "PreapprovedPayment_Charge"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call
					.invoke(new Object[] { walletID, securityToken, preapprovedPaymentID, amount, comment, receiver });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (TransactionInfo) _resp;
				} catch (Exception _exception) {
					return (TransactionInfo) org.apache.axis.utils.JavaUtils.convert(_resp, TransactionInfo.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public PreapprovedPayment preapprovedPayment_Cancel(String walletID, String securityToken,
			Long preapprovedPaymentID, String comment) throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[23]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/PreapprovedPayment_Cancel");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "PreapprovedPayment_Cancel"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { walletID, securityToken, preapprovedPaymentID, comment });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (PreapprovedPayment) _resp;
				} catch (Exception _exception) {
					return (PreapprovedPayment) org.apache.axis.utils.JavaUtils.convert(_resp,
							PreapprovedPayment.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public TransactionInfo[] preapprovedPayment_Get_Operations(String walletID, String securityToken,
			Long preapprovedPaymentID) throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[24]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/PreapprovedPayment_Get_Operations");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "PreapprovedPayment_Get_Operations"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { walletID, securityToken, preapprovedPaymentID });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (TransactionInfo[]) _resp;
				} catch (Exception _exception) {
					return (TransactionInfo[]) org.apache.axis.utils.JavaUtils.convert(_resp, TransactionInfo[].class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public PreapprovedPayment[] preapprovedPayments_Filter(String walletID, String securityToken, String title,
			String invoice, String client, PreapprovedPaymentStatuses[] statuses) throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[25]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("https://api.okpay.com/I_OkPayAPI/PreapprovedPayments_Filter");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("https://api.okpay.com", "PreapprovedPayments_Filter"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { walletID, securityToken, title, invoice, client, statuses });

			if (_resp instanceof RemoteException) {
				throw (RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (PreapprovedPayment[]) _resp;
				} catch (Exception _exception) {
					return (PreapprovedPayment[]) org.apache.axis.utils.JavaUtils.convert(_resp,
							PreapprovedPayment[].class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

}
