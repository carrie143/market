package com.gop.asset.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.FinanceDetail;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FinanceDetailDto {
	
	private Integer userId;
	
	private String assetCode;
	
	private String BusinessSubject;
	
	private BigDecimal amountAvailable;
	
	private BigDecimal amountLock;
	
	private Date createDate;
	
	public FinanceDetailDto(FinanceDetail detail){
		this.userId = detail.getUid();
		this.assetCode = detail.getAssetCode();
		this.BusinessSubject = detail.getBusinessSubject();
		this.amountAvailable = detail.getAmountAvailable();
		this.amountLock = detail.getAmountLock();
		this.createDate = detail.getCreateDate();
	}
}
