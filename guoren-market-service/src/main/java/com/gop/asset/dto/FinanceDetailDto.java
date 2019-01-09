package com.gop.asset.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.FinanceDetail;
import com.gop.mode.vo.BaseDto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FinanceDetailDto extends BaseDto {

	private Integer id;

	private String businessSubject;

	private BigDecimal amount;

	private BigDecimal amountLoan;

	private BigDecimal amountLock;

	private Date createDate;
	
	public FinanceDetailDto(FinanceDetail detail){
		this.setAmount(detail.getAmountAvailable());
		this.setAmountLoan(detail.getAmountLoan());
		this.setAmountLock(detail.getAmountLock());
		this.setBusinessSubject(detail.getBusinessSubject());
		this.setCreateDate(detail.getCreateDate());
	}

}
