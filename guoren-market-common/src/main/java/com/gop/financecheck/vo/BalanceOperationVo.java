package com.gop.financecheck.vo;

import java.math.BigDecimal;

import com.gop.financecheck.enums.AccountChange;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BalanceOperationVo {
	private Integer userId;

	private String accountNo;

	private AccountChange accountChange;

	private String assetCode;

	private BigDecimal balanceAvailable;

	private BigDecimal balanceLock;

	private BigDecimal balanceTotal;

	private Integer balanceVersion;

	private BigDecimal amount;

	public BalanceOperationVo() {
		this.userId = 0;
		this.accountNo = "";
		this.accountChange = AccountChange.UNKNOWN;
//		this.assetType = ;
		this.balanceAvailable = BigDecimal.ZERO;
		this.balanceLock = BigDecimal.ZERO;
		this.balanceTotal = BigDecimal.ZERO;
		this.balanceVersion = 0;
		this.amount = BigDecimal.ZERO;
	}
	
	public BalanceOperationVo(Integer userId, String accountNo,
			AccountChange accountChange, String assetCode,
			BigDecimal balanceAvailable, BigDecimal balanceLock,
			BigDecimal balanceTotal, Integer balanceVersion,
			BigDecimal amount) {
		this.userId = userId;
		this.accountNo = accountNo;
		this.accountChange = accountChange;
		this.assetCode = assetCode;
		this.balanceAvailable = balanceAvailable;
		this.balanceLock = balanceLock;
		this.balanceTotal = balanceTotal;
		this.balanceVersion = balanceVersion;
		this.amount = amount;
	}

}
