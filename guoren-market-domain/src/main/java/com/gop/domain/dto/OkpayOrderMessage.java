package com.gop.domain.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OkpayOrderMessage {
	
	String accountNo;
	
	String name;
	
	String currency;
	
	String type;
	
	BigDecimal price;
	
	BigDecimal tax;

}
