package com.gop.asset.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.Finance;
import com.gop.domain.FinanceDetail;

import lombok.Data;

@Data
public class FinanceDetailDto {

	Date createDate;

	BigDecimal amount;

	String assetCode;

	public FinanceDetailDto(FinanceDetail detail) {

		this.createDate = detail.getCreateDate();

		this.amount = detail.getAmountAvailable();
		this.assetCode = detail.getAssetCode();
	}

}
