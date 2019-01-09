package com.gop.c2c.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.gop.domain.enums.C2cPayType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class C2cAdvertSellSimpleDto {
	
	@NotBlank
	//交易币种
	private String assetcode;
	
	@NotBlank
	//国家
	private String country;
	
	@NotBlank
	//货币
	private String currency;
		
	@NotNull
	//交易价格
	private BigDecimal tradePrice; 
	
	
	@NotNull
	//最小售出个数
	private BigDecimal minAmount; 
	
	@NotNull
	//最大售出个数
	private BigDecimal maxAmount;
	
	@NotNull
	//最小售出金额
	private BigDecimal minMoney;
	
	@NotNull
	//最大售出金额
	private BigDecimal maxMoney;
	
	@NotNull
	//备注信息
	private String remark;
	
	@NotNull
	//支付方式
	private List<C2cPayType> payType;
	
	//售出广告ID
	private String advertId;
	
	//用户交易次数、鼓励次数及交易信息
	private C2cTransactionInfoDto c2cTransactionInfoDto;
	

}
