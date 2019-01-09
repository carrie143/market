package com.gop.asset.dto;

import java.math.BigDecimal;

import com.gop.domain.Finance;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AssetDto {
	private Integer userId;

	private String accountNo;

	private String assetCode;

	private BigDecimal amountAvailable;
	
	private BigDecimal amountLock;

	private BigDecimal tradeAmountTotal;  //交易总量

	private BigDecimal withdrawAmountTotal;  //提现总量

	public AssetDto(Finance finance,BigDecimal tradeAmountTotal,BigDecimal withdrawAmountTotal){
		
		this.userId = finance.getUid();
		this.accountNo= finance.getAccountNo();
		this.assetCode = finance.getAssetCode();
		this.amountAvailable = finance.getAmountAvailable();
		this.amountLock = finance.getAmountLock();
		this.tradeAmountTotal = tradeAmountTotal == null ? BigDecimal.ZERO:tradeAmountTotal;
		this.withdrawAmountTotal = withdrawAmountTotal == null ? BigDecimal.ZERO:withdrawAmountTotal;
	}
}
