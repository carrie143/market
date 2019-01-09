package com.gop.currency.transfer.dto;

import java.util.Date;

import com.gop.domain.ChannelAlipayUserAccount;
import com.gop.domain.ChannelBankUserAccount;
import com.gop.domain.ChannelOkpayUserAccount;
import com.gop.domain.enums.UserAccountChannelType;
import com.gop.mode.vo.BaseDto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AcAccountDto extends BaseDto {

	private Integer accountId;

	private UserAccountChannelType channelType;

	private String channelAccountNo;
	
	private String channelAccountName;

	private Date updateDate;

	public AcAccountDto(ChannelBankUserAccount account) {
		this.channelType = UserAccountChannelType.BANK;
		this.accountId = account.getId();
		this.channelAccountNo = account.getAcnumber();
		this.channelAccountName = account.getBank();
		this.updateDate = account.getUpdateDate();
	}

	public AcAccountDto(ChannelAlipayUserAccount account) {
		this.channelType = UserAccountChannelType.ALIPAY;
		this.accountId = account.getId();
		this.channelAccountNo = account.getAccountNo();
		this.channelAccountName = account.getAccountName();
		this.updateDate = account.getUpdateDate();
	}

	public AcAccountDto(ChannelOkpayUserAccount account) {
		this.accountId = account.getId();
		this.channelType = UserAccountChannelType.OKPAY;
		this.channelAccountNo = account.getAccountNo();
		this.channelAccountName = account.getAccountName();
		this.updateDate = account.getUpdateDate();
	}

}
