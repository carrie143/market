package com.gop.currency.withdraw.gateway.service.cibpay.service;

public interface CibPayService {
	
	 public String cibPay(String order_no, String to_bank_no, String to_acct_no, String to_acct_name, String acct_type, String trans_amt, String trans_usage);

	 public String cibQuery(String order_no);
}
