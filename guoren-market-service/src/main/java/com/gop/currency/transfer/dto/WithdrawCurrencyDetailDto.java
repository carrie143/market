package com.gop.currency.transfer.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.WithdrawCurrencyOrderUser;
import com.gop.domain.enums.WithdrawCurrencyOrderStatus;

import lombok.Data;

@Data
public class WithdrawCurrencyDetailDto {

	private Integer brokerId;

	private String account;

	private String assetCode;

	private BigDecimal money;

	private BigDecimal pay;

	private BigDecimal fee;

	private String orderNo;

	private String acnumber;

	private String bank;

	private String name;

	private String payTransactionNo;

	private WithdrawCurrencyOrderStatus status;

	private String msg;

	private Date updateDate;

	public WithdrawCurrencyDetailDto(WithdrawCurrencyOrderUser dto) {
		this.orderNo = dto.getOuterOrderNo();
		this.payTransactionNo = dto.getInnerOrderNo();
		this.brokerId = dto.getBrokerId();
		this.money = dto.getMoney().stripTrailingZeros();
		this.pay = dto.getPay().stripTrailingZeros();
		this.fee = dto.getFee().stripTrailingZeros();
		this.name = dto.getName();
		this.bank = dto.getBank();
		this.acnumber = dto.getAcnumber();
		this.status = dto.getStatus();
		this.updateDate = dto.getUpdateDate();
		this.msg = dto.getMsg();

	}

}
