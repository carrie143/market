package com.gop.coin.transfer.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.DepositCoinOrderUser;
import com.gop.domain.enums.DepositCoinAssetStatus;
import com.gop.mode.vo.BaseDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepositCoinDto extends BaseDto {

	private String coindAddress;

	private String message;

	private String orderNo;

	private Date updateDate;

	private String assetCode;

	private BigDecimal amount;

	private BigDecimal fee;
	
	private String payTransactionNo;
	private DepositCoinAssetStatus status;

	public DepositCoinDto(DepositCoinOrderUser order) {

		this.updateDate = order.getUpdateDate();
		this.assetCode = order.getAssetCode();
		this.coindAddress = order.getCoinAddress();
		this.message = order.getMsg();
		this.updateDate = order.getUpdateDate();
		status=	order.getAssetStatus();
		this.amount = order.getNumber();

		this.fee = order.getFee();
		this.payTransactionNo=order.getInnerOrderNo();

	}

}
