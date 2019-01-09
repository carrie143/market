package com.gop.financecheck.domain;

import java.math.BigDecimal;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ItemFinanceHistory {
	private Long id;

	private String accountNo;

	private String accountClass;

	private Integer brokerId;

	private String assetCode;

	private Integer userId;

	private String businessSubject;

	private String outTxNo;

	private Integer subject1;

	private Integer subject2;

	private BigDecimal amountDebit;

	private BigDecimal amountCredit;

	private BigDecimal amountPlus;

	private BigDecimal amountLess;

	private BigDecimal balanceMoment;

	private Integer balanceVersion;

	private Integer createTime;

	private String memo;

	public ItemFinanceHistory() {
	}

 	public ItemFinanceHistory(String accountNo, String accountClass,
 			Integer brokerId, String assetCode, Integer userId,
 			String businessSubject, String outTxNo, Integer subject1,
 			Integer subject2, BigDecimal amountDebit, BigDecimal amountCredit,
 			BigDecimal amountPlus, BigDecimal amountLess,
 			BigDecimal balanceMoment, Integer balanceVersion,
 			Integer createTime, String memo) {
		this.accountNo = accountNo;
		this.accountClass = accountClass;
		this.brokerId = brokerId;
		this.assetCode = assetCode;
		this.userId = userId;
		this.businessSubject = businessSubject;
		this.outTxNo = outTxNo;
		this.subject1 = subject1;
		this.subject2 = subject2;
		this.amountDebit = amountDebit;
		this.amountCredit = amountCredit;
		this.amountPlus = amountPlus;
		this.amountLess = amountLess;
		this.balanceMoment = balanceMoment;
		this.balanceVersion = balanceVersion;
		this.createTime = createTime;
		this.memo = memo;
	}

}