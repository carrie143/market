/**
 * I_OkPayAPI.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gop.currency.withdraw.gateway.service.okpay.proxy;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.gop.currency.withdraw.gateway.service.okpay.dto.contract.PreapprovedPayment;
import com.gop.currency.withdraw.gateway.service.okpay.dto.contract.Subscription;
import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.Balance;
import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.HistoryInfo;
import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.PreapprovedPaymentStatuses;
import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.SubscriptionStatuses;
import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.TransactionInfo;
import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.Withdrawal_Info;
import com.gop.currency.withdraw.gateway.service.okpay.dto.status.ClientStatus;

//import Balance;
//import HistoryInfo;
//import TransactionInfo;
//import Withdrawal_Info;
//import com.gop.exchange.okpay.domain.OkPayAPI_DataContract.PreapprovedPayment;
//import com.gop.exchange.okpay.domain.OkPayAPI_DataContract.Subscription;
//import com.gop.exchange.okpay.domain.OkPayLink_OkPayService.ClientStatus;

public interface OkPayAPI extends Remote {
	public String get_Date_Time() throws RemoteException;

	public Balance[] wallet_Get_Balance(String walletID, String securityToken) throws RemoteException;

	public Balance wallet_Get_Currency_Balance(String walletID, String securityToken, String currency)
			throws RemoteException;

	public TransactionInfo send_Money(String walletID, String securityToken, String receiver, String currency,
			java.math.BigDecimal amount, String comment, Boolean isReceiverPaysFees, String invoice)
			throws RemoteException;

	public TransactionInfo transaction_Get(String walletID, String securityToken, Long txnID, String invoice)
			throws RemoteException;

	public HistoryInfo transaction_History(String walletID, String securityToken, String from, String till,
			Integer pageSize, Integer pageNumber) throws RemoteException;

	public Long debitCard_Prepay(String walletID, String securityToken, String email, String currency,
			Boolean isCourierDelivery, String comment) throws RemoteException;

	public Long account_Check(String walletID, String securityToken, String account) throws RemoteException;

	public Withdrawal_Info withdraw_To_Ecurrency_Calculate(String walletID, String securityToken, String paymentMethod,
			java.math.BigDecimal amount, String currency, Boolean feesFromAmount) throws RemoteException;

	public Withdrawal_Info withdraw_To_Ecurrency(String walletID, String securityToken, String paymentMethod,
			String paySystemAccount, java.math.BigDecimal amount, String currency, Boolean feesFromAmount,
			String invoice) throws RemoteException;

	public Withdrawal_Info withdraw_To_Card_RU(String walletID, String securityToken, String cardNumber,
			java.math.BigDecimal amount, String invoice) throws RemoteException;

	public Withdrawal_Info withdraw_To_Card_RU_Full(String walletID, String securityToken, String cardNumber,
			java.math.BigDecimal amount, String firstName, String lastName, String midName, String phone,
			String documentNumber, String secodDocumentType, String secondDocumentNumber, String invoice)
			throws RemoteException;

	public Withdrawal_Info withdraw_To_Card_World(String walletID, String securityToken, String cardNumber,
			String currency, java.math.BigDecimal amount, String expiryMonth, String expiryYear, String invoice)
			throws RemoteException;

	public Withdrawal_Info withdraw_To_CryptoCurrency_Calculate(String walletID, String securityToken,
			String paymentMethod, java.math.BigDecimal amount, String currency) throws RemoteException;

	public Withdrawal_Info withdraw_To_CryptoCurrency(String walletID, String securityToken, String paymentMethod,
			String paySystemAccount, java.math.BigDecimal amount, String currency, String invoice)
			throws RemoteException;

	public Long EX_Account_Check(String walletID, String securityToken, String account) throws RemoteException;

	public ClientStatus EX_Client_Check_Status(String walletID, String securityToken, String email)
			throws RemoteException;

	public Subscription subscription_Get(String walletID, String securityToken, Long subscriptionID)
			throws RemoteException;

	public Subscription[] subscriptions_Filter(String walletID, String securityToken, String title, String from,
			String till, SubscriptionStatuses[] statuses) throws RemoteException;

	public Subscription subscription_Update(String walletID, String securityToken,
			com.gop.currency.withdraw.gateway.service.okpay.dto.contract.Subscription sub, String comment)
			throws RemoteException;

	public TransactionInfo[] subscription_Get_Operations(String walletID, String securityToken, Long subscriptionID)
			throws RemoteException;

	public PreapprovedPayment preapprovedPayment_Get(String walletID, String securityToken, Long preapprovedPaymentID)
			throws RemoteException;

	public TransactionInfo preapprovedPayment_Charge(String walletID, String securityToken, Long preapprovedPaymentID,
			java.math.BigDecimal amount, String comment, String receiver) throws RemoteException;

	public PreapprovedPayment preapprovedPayment_Cancel(String walletID, String securityToken,
			Long preapprovedPaymentID, String comment) throws RemoteException;

	public TransactionInfo[] preapprovedPayment_Get_Operations(String walletID, String securityToken,
			Long preapprovedPaymentID) throws RemoteException;

	public PreapprovedPayment[] preapprovedPayments_Filter(String walletID, String securityToken, String title,
			String invoice, String client, PreapprovedPaymentStatuses[] statuses)
			throws RemoteException;
}
