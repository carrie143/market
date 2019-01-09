//package com.gop.currency.transfer.config;
//
//import java.math.BigDecimal;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//
//import lombok.Data;
//
//@Configuration
//@PropertySource("classpath:withdraw.properties")
//@Data
//public class WithdrawConfig {
//
//	@Value("${withdraw.fee.ratio}")
//	private BigDecimal withdrawFeeRatio;
//	
//	@Value("${withdraw.min.fee}")
//	private BigDecimal withdrawMinFee;
//	
//	@Value("${withdraw.min.money}")
//	private BigDecimal withdrawMinMoney;
//	
//	@Value("${withdraw.max.money}")
//	private BigDecimal withdrawMaxMoney;
//	
//	@Value("${withdraw.fee.precision}")
//	private int withdrawFeePrecision;
//
//}
