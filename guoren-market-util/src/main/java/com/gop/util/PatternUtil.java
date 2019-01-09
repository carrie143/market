package com.gop.util;

/**
 * 
 * @author zhangxianglong
 *
 */
public class PatternUtil {

	/**
	 * 判断字符串是否为：整数或者最多2位小数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str, int scale) {
		return str.matches(String.format("^[0-9]+\\.{0,1}[0-9]{0,%d}$", scale));
	}

	/**
	 * 判断字符串是否为：整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		return str.matches("^[0-9]*$");
	}

	public static void main(String[] args) {
		System.out.println(isNumeric("123.1111", 3));
	}

}
