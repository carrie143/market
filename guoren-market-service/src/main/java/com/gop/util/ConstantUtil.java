package com.gop.util;

import java.math.BigDecimal;

/**
 * 类说明
 * 
 * @Author Lorne Zhang
 */
public class ConstantUtil {
	/**
	 * 定时任务是否开启
	 */
	public static final boolean ENABLE = true;

	/**
	 * 人民币转出费率
	 */
	public static final BigDecimal rmbWithdrawalsRate = new BigDecimal("0.005");
	
	/**
	 * okpay转出费率
	 */
	public static final BigDecimal OKPAY_RECHARGE_RATE = new BigDecimal("0.01");

	/**
	 * 人民币充值最小金额
	 */
	public static final BigDecimal rmbRechargeMinMoney = new BigDecimal("100");

	public static final BigDecimal coinRechargeMinAmount = new BigDecimal("0.02");

	/**
	 * USD充值最小金额 //////////////////////////
	 */
	public static final BigDecimal USD_RECHARGE_MIN_MONEY = new BigDecimal("5");
	
	/**
	 * USD充值最大金额 //////////////////////////代码中已加
	 */
	public static final BigDecimal USD_RECHARGE_MAX_MONEY = new BigDecimal("5000");
	
	/**
	 * 人民币充值最大金额
	 */
	public static final BigDecimal rmbRechargeMaxMoney = new BigDecimal("20000");

	/**
	 * 人民币充值最小提示
	 */
	public static final String rmbRechargeMinMoneyPrompt = "人民币充值最小金额为" + rmbRechargeMinMoney;

	/**
	 * 人民币充值最大提示
	 */
	public static final String rmbRechargeMaxMoneyPrompt = "人民币充值最大金额为" + rmbRechargeMaxMoney;

	/**
	 * 人民币转出最小金额
	 */
	public static final BigDecimal rmbWithdrawalsMinMoney = new BigDecimal("10");
	
	public static final BigDecimal coinWithdrawalsMinMoney = new BigDecimal("0.02");
	
	/**
	 * usd转出最小金额////////////
	 */
	public static final BigDecimal USD_WITHDRAALS_MIN_MONEY = new BigDecimal("5");
	

	/**
	 * 人民币单笔转出最大金额
	 */
	public static final BigDecimal rmbWithdrawalsMaxMoney = new BigDecimal("50000");

	/**
	 * USD单笔转出最大金额 ///////////////////
	 */
	public static final BigDecimal USD_WITHDRAWALS_MAX_MONEY = new BigDecimal("5000");
	
	/**
	 * 人民币单笔转出范围
	 */
	public static final String rmbWithdrawalsRange = "单笔转出范围10-50000元";
	
	/**
	 * USD单笔转出范围   /////////////没用着
	 */
	public static final String USD_WITHDRAWALS_RANGE = "单笔转出范围10-50000元";

	/**
	 * 人民币转出最小手续费
	 */
	public static final BigDecimal rmbWithdrawalsMinFee = new BigDecimal("2.00");
	
	/**
	 * usd转出最小手续费
	 */
	public static final BigDecimal USD_WITHDRAWLS_MIN_FEE = new BigDecimal("0");
	
	/**
	 * okpay充值最小手续费
	 */
	public static final BigDecimal OKPAY_RECHARGE_MIN_FEE = new BigDecimal("0.01");

	/**
	 * 默认金额0.00
	 */
	public static final BigDecimal Zero = new BigDecimal("0.00");

	/**
	 * 支付锁定提示语
	 */
	public static final String payLockedPrompt = "为保证资金安全，您的支付密码已被锁定，请找回支付密码";

	/**
	 * 金额必须为整数
	 */
	public static final String rmbMoneyIntegerPrompt = "金额必须为整数";

	/**
	 * 支付密码错误提示
	 */
	public static final String payErrorPrompt = "支付密码错误";

	/**
	 * 登录锁定提示
	 */
	public static final String loginLockedPrompt = "帐号已经锁定，请找回您的登录密码";

	/**
	 * 登录密码错误提示
	 */
	public static final String loginErrorPrompt = "登录密码错误";

	/**
	 * 人民币转出每日限额20万
	 */
	public static final BigDecimal dayLimted = new BigDecimal("200000");
	
	/**
	 * usd转出每日限额
	 */
	public static final BigDecimal  USD_DAY_LIMITED  = new BigDecimal("30000");

	/**
	 * 挂单最小额
	 */
	public static final BigDecimal gopMinAmount = new BigDecimal("0.1");
	public static final BigDecimal gopMinPrice = new BigDecimal("0.1");

	/**
	 * 市价买入最大额
	 */
	public static final BigDecimal MAX_GOP_MARKET_BUY = new BigDecimal("20000");
	public static final BigDecimal MIN_GOP_MARKET_BUY = new BigDecimal("0.1");
	/**
	 * 市价卖出最大额
	 */
	public static final BigDecimal MAX_GOP_MARKET_SELL = new BigDecimal("1000");
	
	public static final BigDecimal MIN_GOP_MARKET_SELL = new BigDecimal("0.1");
	/**
	 * 限价挂单最大额
	 */
	public static final BigDecimal gopMaxPrice = new BigDecimal("99999.99");

	/**
	 * 限价挂单最大额提示
	 */
	public static final String gopMaxMoneyPrompt = "挂单最大额为99999.99";

	/**
	 * 挂单最小额提示
	 */
	public static final String gopMinMoneyPrompt = "挂单最小额为0.1";

	/**
	 * 挂单最小量提示
	 */
	public static final String gopMinNumPrompt = "挂单最小量为0.1";

	/**
	 * 人民币转出每日限额提示
	 */
	public static final String dayLimtedPrompt = "每日转出金额不能超过20万";

	/**
	 * 未实名认证
	 */
	public static final String NO_REAL_NAME_PROMPT = "未实名认证";

	/**
	 * 人民币单笔转出范围
	 */
	public static final Integer YAJIE_UID = 27825;

	/**
	 * 特殊字符处理
	 */
	public static String[] characterPS() {
		String[] chars = { "&lt;", "&gt;", "&#40;", "&#41;", "&#39;" };
		return chars;
	}

	/**
	 * 原始字符
	 * 
	 * @return
	 */
	public static String[] characterOriginal() {
		String[] chars = { "<", ">", "\\(", "\\)", "'" };
		return chars;
	}

	/**
	 * 将特殊字符处理
	 * 
	 * @param newPwd
	 * @return
	 */
	public static String charConvert(String newPwd) {
		if (newPwd.contains(characterPS()[0])) {
			newPwd = newPwd.replaceAll(characterPS()[0], characterOriginal()[0]);
		}
		if (newPwd.contains(characterPS()[1])) {
			newPwd = newPwd.replaceAll(characterPS()[1], characterOriginal()[1]);
		}
		if (newPwd.contains(characterPS()[2])) {
			newPwd = newPwd.replaceAll(characterPS()[2], characterOriginal()[2]);
		}
		if (newPwd.contains(characterPS()[3])) {
			newPwd = newPwd.replaceAll(characterPS()[3], characterOriginal()[3]);
		}
		if (newPwd.contains(characterPS()[4])) {
			newPwd = newPwd.replaceAll(characterPS()[4], characterOriginal()[4]);
		}
		return newPwd;
	}

	/**
	 * 无法识别此卡
	 */
	public static final String NOT_RECOGNIZED_BANK_CARD = "无法识别此卡";
	/**
	 * 暂不支持该银行卡
	 */
	public static final String NOT_SUPPORTED_BANK_CARD = "暂不支持该银行卡";

}
