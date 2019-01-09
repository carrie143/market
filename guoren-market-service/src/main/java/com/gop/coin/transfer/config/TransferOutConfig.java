//package com.gop.coin.transfer.config;
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
//@PropertySource("classpath:coinTransfer.properties")
//@Data
//public class TransferOutConfig {
//
//	
//	@Value(value = "${gop_transfer_out_min_fee}")
//	private BigDecimal gopMinFeeOut;
//	
//	@Value(value = "${gop_transfer_out_min}")
//	private BigDecimal gopMinWithdrawAmount;
//	
//	@Value(value = "${gop_transfer_out_max}")
//	private BigDecimal gopMaxWithdrawAmount;
//	
//	@Value(value = "${btc_transfer_out_min_fee}")
//	private BigDecimal btcMinFeeOut;
//	
//	@Value(value = "${btc_transfer_out_min}")
//	private BigDecimal btcMinWithdrawAmount;
//
//	@Value(value = "${btc_transfer_out_max}")
//	private BigDecimal btcMaxWithdrawAmount;
//	
//}
