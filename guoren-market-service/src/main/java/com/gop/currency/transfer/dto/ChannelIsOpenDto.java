package com.gop.currency.transfer.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ChannelIsOpenDto {
	
	String accountName;
	
	String accountCode;
	
	String accountNo;
	
	BigDecimal minAmount;
	
	BigDecimal maxAmount;
	
	String status;

}
