package com.gop.currency.withdraw.gateway.service.okpay.proxy;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.Stub;

import org.springframework.stereotype.Service;

import com.gop.currency.withdraw.gateway.service.okpay.dto.contract.PreapprovedPayment;
import com.gop.currency.withdraw.gateway.service.okpay.dto.contract.Subscription;
import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.Balance;
import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.HistoryInfo;
import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.PreapprovedPaymentStatuses;
import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.SubscriptionStatuses;
import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.TransactionInfo;
import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.Withdrawal_Info;

@Service
public class OkPayAPIProxy implements OkPayAPI {
	private String _endpoint = null;
	private OkPayAPI i_OkPayAPI = null;

	public OkPayAPIProxy() {
		_initI_OkPayAPIProxy();
	}

	public OkPayAPIProxy(String endpoint) {
		_endpoint = endpoint;
		_initI_OkPayAPIProxy();
	}

	private void _initI_OkPayAPIProxy() {
		try {
			i_OkPayAPI = (new OkPayAPIImplementationLocator()).getBasicHttpBinding_I_OkPayAPI();
			if (i_OkPayAPI != null) {
				if (_endpoint != null)
					((Stub) i_OkPayAPI)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
				else
					_endpoint = (String) ((Stub) i_OkPayAPI)._getProperty("javax.xml.rpc.service.endpoint.address");
			}

		} catch (ServiceException serviceException) {
		}
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
		if (i_OkPayAPI != null)
			((Stub) i_OkPayAPI)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

	}

	public OkPayAPI getI_OkPayAPI() {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI;
	}

	public String get_Date_Time() throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.get_Date_Time();
	}

	public Balance[] wallet_Get_Balance(String walletID, String securityToken) throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.wallet_Get_Balance(walletID, securityToken);
	}

	public Balance wallet_Get_Currency_Balance(String walletID, String securityToken, String currency)
			throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.wallet_Get_Currency_Balance(walletID, securityToken, currency);
	}

	public TransactionInfo send_Money(String walletID, String securityToken, String receiver, String currency,
			java.math.BigDecimal amount, String comment, Boolean isReceiverPaysFees, String invoice)
			throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.send_Money(walletID, securityToken, receiver, currency, amount, comment, isReceiverPaysFees,
				invoice);
	}

	public TransactionInfo transaction_Get(String walletID, String securityToken, Long txnID, String invoice)
			throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.transaction_Get(walletID, securityToken, txnID, invoice);
	}

	public HistoryInfo transaction_History(String walletID, String securityToken, String from, String till,
			Integer pageSize, Integer pageNumber) throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.transaction_History(walletID, securityToken, from, till, pageSize, pageNumber);
	}

	public Long debitCard_Prepay(String walletID, String securityToken, String email, String currency,
			Boolean isCourierDelivery, String comment) throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.debitCard_Prepay(walletID, securityToken, email, currency, isCourierDelivery, comment);
	}

	public Long account_Check(String walletID, String securityToken, String account) throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.account_Check(walletID, securityToken, account);
	}

	public Withdrawal_Info withdraw_To_Ecurrency_Calculate(String walletID, String securityToken, String paymentMethod,
			java.math.BigDecimal amount, String currency, Boolean feesFromAmount) throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.withdraw_To_Ecurrency_Calculate(walletID, securityToken, paymentMethod, amount, currency,
				feesFromAmount);
	}

	public Withdrawal_Info withdraw_To_Ecurrency(String walletID, String securityToken, String paymentMethod,
			String paySystemAccount, java.math.BigDecimal amount, String currency, Boolean feesFromAmount,
			String invoice) throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.withdraw_To_Ecurrency(walletID, securityToken, paymentMethod, paySystemAccount, amount,
				currency, feesFromAmount, invoice);
	}

	public Withdrawal_Info withdraw_To_Card_RU(String walletID, String securityToken, String cardNumber,
			java.math.BigDecimal amount, String invoice) throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.withdraw_To_Card_RU(walletID, securityToken, cardNumber, amount, invoice);
	}

	public Withdrawal_Info withdraw_To_Card_RU_Full(String walletID, String securityToken, String cardNumber,
			java.math.BigDecimal amount, String firstName, String lastName, String midName, String phone,
			String documentNumber, String secodDocumentType, String secondDocumentNumber, String invoice)
			throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.withdraw_To_Card_RU_Full(walletID, securityToken, cardNumber, amount, firstName, lastName,
				midName, phone, documentNumber, secodDocumentType, secondDocumentNumber, invoice);
	}

	public Withdrawal_Info withdraw_To_Card_World(String walletID, String securityToken, String cardNumber,
			String currency, java.math.BigDecimal amount, String expiryMonth, String expiryYear, String invoice)
			throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.withdraw_To_Card_World(walletID, securityToken, cardNumber, currency, amount, expiryMonth,
				expiryYear, invoice);
	}

	public Withdrawal_Info withdraw_To_CryptoCurrency_Calculate(String walletID, String securityToken,
			String paymentMethod, java.math.BigDecimal amount, String currency) throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.withdraw_To_CryptoCurrency_Calculate(walletID, securityToken, paymentMethod, amount,
				currency);
	}

	public Withdrawal_Info withdraw_To_CryptoCurrency(String walletID, String securityToken, String paymentMethod,
			String paySystemAccount, java.math.BigDecimal amount, String currency, String invoice)
			throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.withdraw_To_CryptoCurrency(walletID, securityToken, paymentMethod, paySystemAccount, amount,
				currency, invoice);
	}

	public Long EX_Account_Check(String walletID, String securityToken, String account) throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.EX_Account_Check(walletID, securityToken, account);
	}

	public com.gop.currency.withdraw.gateway.service.okpay.dto.status.ClientStatus EX_Client_Check_Status(String walletID,
			String securityToken, String email) throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.EX_Client_Check_Status(walletID, securityToken, email);
	}

	public Subscription subscription_Get(String walletID,
			String securityToken, Long subscriptionID) throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.subscription_Get(walletID, securityToken, subscriptionID);
	}

	public Subscription[] subscriptions_Filter(String walletID,
			String securityToken, String title, String from, String till, SubscriptionStatuses[] statuses)
			throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.subscriptions_Filter(walletID, securityToken, title, from, till, statuses);
	}

	public Subscription subscription_Update(String walletID,
			String securityToken, Subscription sub, String comment)
			throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.subscription_Update(walletID, securityToken, sub, comment);
	}

	public TransactionInfo[] subscription_Get_Operations(String walletID, String securityToken, Long subscriptionID)
			throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.subscription_Get_Operations(walletID, securityToken, subscriptionID);
	}

	public PreapprovedPayment preapprovedPayment_Get(
			String walletID, String securityToken, Long preapprovedPaymentID) throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.preapprovedPayment_Get(walletID, securityToken, preapprovedPaymentID);
	}

	public TransactionInfo preapprovedPayment_Charge(String walletID, String securityToken, Long preapprovedPaymentID,
			java.math.BigDecimal amount, String comment, String receiver) throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.preapprovedPayment_Charge(walletID, securityToken, preapprovedPaymentID, amount, comment,
				receiver);
	}

	public PreapprovedPayment preapprovedPayment_Cancel(
			String walletID, String securityToken, Long preapprovedPaymentID, String comment) throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.preapprovedPayment_Cancel(walletID, securityToken, preapprovedPaymentID, comment);
	}

	public TransactionInfo[] preapprovedPayment_Get_Operations(String walletID, String securityToken,
			Long preapprovedPaymentID) throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.preapprovedPayment_Get_Operations(walletID, securityToken, preapprovedPaymentID);
	}

	public PreapprovedPayment[] preapprovedPayments_Filter(
			String walletID, String securityToken, String title, String invoice, String client,
			PreapprovedPaymentStatuses[] statuses) throws RemoteException {
		if (i_OkPayAPI == null)
			_initI_OkPayAPIProxy();
		return i_OkPayAPI.preapprovedPayments_Filter(walletID, securityToken, title, invoice, client, statuses);
	}

}