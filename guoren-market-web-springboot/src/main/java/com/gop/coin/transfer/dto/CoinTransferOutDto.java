package com.gop.coin.transfer.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.FinanceDetail;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CoinTransferOutDto {
	
	private Integer userId;
	
	private String assetCode;
	
	private String BusinessSubject;
	
	private BigDecimal amountAvailable;
	
	private BigDecimal amountLoan;
	
	private Date createDate;
	
	public CoinTransferOutDto(FinanceDetail detail){
		this.userId = detail.getUid();
		this.assetCode = detail.getAssetCode();
		this.BusinessSubject = detail.getBusinessSubject();
		this.amountAvailable = detail.getAmountAvailable();
		this.amountLoan = detail.getAmountLoan();
		this.createDate = detail.getCreateDate();
	}
}
