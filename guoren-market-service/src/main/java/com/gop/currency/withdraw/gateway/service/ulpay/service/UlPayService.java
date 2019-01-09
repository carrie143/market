package com.gop.currency.withdraw.gateway.service.ulpay.service;

import java.math.BigDecimal;
import java.util.Map;

import com.gop.currency.withdraw.gateway.service.ulpay.dto.RequestBatch;
import com.gop.currency.withdraw.gateway.service.ulpay.dto.RequestQuery;

public interface UlPayService {
	
	/**
	 * 自定义支付模式
	 * @param requestData
	 * @return
	 */
	public Map<String, Object> batchPay(RequestBatch requestData);
	
	/**
	 * 普通支付模式，默认使用银行卡，个人转账
	 * @param innerOrder
	 * @param bankName
	 * @param bankNo
	 * @param bankCode
	 * @param accountName
	 * @param amount
	 * @param msg
	 * @return
	 */
	public Map<String, Object> generalBatchPay(String reqSn, String innerOrder,String bankName,String bankNo,String bankCode,String accountName,BigDecimal amount,String msg);
	
	
	/**
	 * 自定义查询模式
	 * @param requestData
	 * @return
	 */
	public Map<String, Object> batchPayQuery(RequestQuery requestData);
	
	
	/**
	 * 自定义查询模式
	 * @param requestData
	 * @return
	 */
	public Map<String, Object> generalBatchQuery(String reqSn, String payNo);

}
