package com.gop.domain.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TransferCoinMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3018323479303290765L;
	
	// 内部订单号
	private String txid;
	
	// 入账钱包
	private String address;
	
	// 转账数量
	private String amount;
	
	// 转账附言
	private String txfee;
	
	// 转账日期
	private String message;
	
}
