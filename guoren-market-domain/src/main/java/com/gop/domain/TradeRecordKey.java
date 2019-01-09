package com.gop.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TradeRecordKey implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -855685401432184166L;

	private String buyTradeNo;

    private String sellTradeNo;

    
    
}