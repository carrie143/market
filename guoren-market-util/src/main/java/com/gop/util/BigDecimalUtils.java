/*
 * 文件名：BigDecimalUtils.java 版权：Copyright by www.guoren.com 描述： 修改人：zhengminghai 修改时间：2016年1月12日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.gop.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;

public class BigDecimalUtils {

	/**
	 * +
	 * 
	 * @param big1
	 * @param big2
	 * @return
	 */
	public static BigDecimal add(BigDecimal big1, BigDecimal big2) {
		return big1.add(big2);
	}

	/**
	 * -
	 * 
	 * @param big1
	 * @param big2
	 * @return
	 */
	public static BigDecimal subtract(BigDecimal big1, BigDecimal big2) {
		return big1.subtract(big2);
	}

	/**
	 * 金额相除，保留6位小数
	 * 
	 * @param a
	 * @param b
	 * @return BigDecimal
	 */
	public static BigDecimal divide(BigDecimal a, BigDecimal b) {
		return a.divide(b, 6, RoundingMode.HALF_UP);
	}

	/**
	 * 金额相乘
	 * 
	 * @param a
	 * @param b
	 * @return BigDecimal
	 */
	public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
		return a.multiply(b);
	}
	public static BigDecimal multiply(int scale,BigDecimal a, BigDecimal b,RoundingMode rm) {
		return a.multiply(b).setScale(scale, rm);
	}


	/**
	 * *
	 */
	public static BigDecimal multiplyNoRounding(BigDecimal big1, BigDecimal big2) {
		return big1.multiply(big2);
	}

	public static boolean isBigger(BigDecimal big1, BigDecimal big2) {
		return big1.compareTo(big2) > 0 ? true : false;
	}

	public static boolean isBiggerZero(BigDecimal decimal) {
		return decimal.compareTo(BigDecimal.ZERO) > 0 ? true : false;
	}

	public static boolean isBiggerOrEqual(BigDecimal big1, BigDecimal big2) {
		return big1.compareTo(big2) >= 0 ? true : false;
	}

	public static boolean isLess(BigDecimal big1, BigDecimal big2) {
		return big1.compareTo(big2) < 0 ? true : false;
	}

	public static boolean isLessZero(BigDecimal decimal) {
		return decimal.compareTo(BigDecimal.ZERO) < 0 ? true : false;
	}

	public static boolean isLessOrEqual(BigDecimal big1, BigDecimal big2) {
		return big1.compareTo(big2) <= 0 ? true : false;
	}
	
	public static boolean isEqualZero(BigDecimal decimal) {
		return decimal.compareTo(BigDecimal.ZERO) == 0 ? true : false;
	}

	public static BigDecimal getBigDecimal(String num) {
		return new BigDecimal(num);
	}

	public static BigDecimal getZero() {
		return BigDecimal.ZERO;
	}

	public static String getFormattedString(BigDecimal bd) {
		int length = 0;
		int position = 0;
		String str = bd.toPlainString();
		if (str.indexOf(".") < 0) {
			return str + ".00";
		}
		length = str.length();
		position = str.indexOf(".");
		if (length < position + 3) {
			return str + "0";
		} else {
			return str.substring(0, position + 3);
		}
	}

	public static boolean isValidGOPAmount(String amount) {
		String regex = "^(([1-9]\\d*)|0)(\\.\\d{1,5})?$";

		if (amount == null)
			return false;

		Pattern p = Pattern.compile(regex);
		if (p.matcher(amount).matches()) {
			return true;
		}
		return false;
	}

	public static boolean isValidMoneyAmount(String amount) {
		String regex = "^(([1-9]\\d*)|0)(\\.\\d{1,2})?$";

		if (amount == null)
			return false;

		Pattern p = Pattern.compile(regex);
		if (p.matcher(amount).matches()) {
			return true;
		}
		return false;
	}

	public static BigDecimal get(String numStr) {
     return new BigDecimal(numStr);
	}

	public static void main(String[] args) {
		System.out.println(isValidMoneyAmount("100"));
		System.out.println(isValidMoneyAmount("100.1"));
		System.out.println(isValidMoneyAmount("100.11"));
		System.out.println(isValidMoneyAmount("0.1"));
		System.out.println(isValidMoneyAmount("0.10"));
		System.out.println(isValidMoneyAmount("0.01"));
		System.out.println(isValidMoneyAmount("0.0"));
		System.out.println(isValidMoneyAmount("6.33"));

		System.out.println("-----");
		System.out.println(isValidMoneyAmount("100.111"));
		System.out.println(isValidMoneyAmount("100.1000"));
		System.out.println(isValidMoneyAmount("0.100"));
		System.out.println(isValidMoneyAmount("01.0"));
		System.out.println(isValidMoneyAmount("0111"));
		
		System.out.println("-----");
		System.out.println(isValidGOPAmount("100.11111"));
		System.out.println(isValidGOPAmount("100.100001"));
		System.out.println(isValidGOPAmount("0.100"));
		System.out.println(isValidGOPAmount("01.0"));
		System.out.println(isValidGOPAmount("0111"));

		System.out.println((new BigDecimal("10.987")).precision());
	}

}
