package com.gop.config;

import java.math.BigDecimal;

/**
 * 常量
 * @author yuxiaojian
 *
 */
public class Constants {

	/**
	 * 充值过期小时数-72
	 */
	public static final int EXPIRE=72;
	
	/**
     * 人民币转出费率
     */
    public static final BigDecimal rmbWithdrawalsRate = new BigDecimal("0.005");
    
    /**
     * 人民币转出最小手续费
     */
    public static final BigDecimal rmbWithdrawalsMinFee = new BigDecimal("2.00");
}
