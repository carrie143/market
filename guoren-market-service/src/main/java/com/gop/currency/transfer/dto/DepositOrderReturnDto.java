package com.gop.currency.transfer.dto;

import java.math.BigDecimal;

import com.gop.mode.vo.BaseDto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DepositOrderReturnDto extends BaseDto{
	
String accountNo;
	
	String name;
	
	String currency;
	
	String type;
	
	BigDecimal price;
	
	BigDecimal tax;
	
}
