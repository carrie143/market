package com.gop.currency.transfer.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.WithdrawCurrencyOrderUser;
import com.gop.mode.vo.BaseDto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class WithdrawHistory extends BaseDto {

	private Date createDate;

	private String AccountNo;
	
	private String channelName;

	private BigDecimal pay;

	private BigDecimal fee;

	private String Status;

	private String AssetCode;

	public WithdrawHistory(WithdrawCurrencyOrderUser withdraw) {
		this.createDate = withdraw.getCreateDate();
		this.channelName = withdraw.getBank();
		this.setAccountNo(withdraw.getAcnumber());
		this.setAssetCode(withdraw.getAssetCode());
		this.setFee(withdraw.getFee());
		this.setPay(withdraw.getMoney());
		this.setStatus(withdraw.getStatus().toString());
	}

}
