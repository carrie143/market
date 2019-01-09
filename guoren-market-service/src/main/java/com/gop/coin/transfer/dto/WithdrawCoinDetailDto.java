package com.gop.coin.transfer.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.WithdrawCoinOrderUser;
import com.gop.domain.enums.WithdrawCoinOrderStatus;
import com.gop.mode.vo.BaseDto;

import lombok.Data;

@Data
public class WithdrawCoinDetailDto extends BaseDto {


	private Date createDate;

	
	private String assetCode;

	
	private String transferOutAddress;

	
	private BigDecimal amount;

	
	private WithdrawCoinOrderStatus status;


	private BigDecimal fee;

	private String payTransactionNo;

	private String orderNo;

	public WithdrawCoinDetailDto(WithdrawCoinOrderUser withdraw) {

		this.createDate = withdraw.getCreateDate();
		this.assetCode = withdraw.getAssetCode();
		this.transferOutAddress = withdraw.getCoinAddress();
		this.amount = withdraw.getNumber().stripTrailingZeros();
		this.status = withdraw.getStatus();
		this.fee = withdraw.getTxFee();
		this.payTransactionNo = withdraw.getInnerOrderNo();
		this.orderNo = withdraw.getOuterOrderNo();
	}

	public WithdrawCoinDetailDto() {

	}
}
