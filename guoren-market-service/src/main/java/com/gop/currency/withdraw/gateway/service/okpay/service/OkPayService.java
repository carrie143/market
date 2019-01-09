package com.gop.currency.withdraw.gateway.service.okpay.service;

import java.math.BigDecimal;

import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.TransactionInfo;

public interface OkPayService {
	
	
	public TransactionInfo pay(String accountNo, String currency, BigDecimal amount, String remark, String invoice);
	
	public TransactionInfo query(Long transcationId, String invoice);

}
