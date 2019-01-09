package com.gop.currency.transfer.utils;

public class AlipayAccountUtil {

	private static String EMAIL_FORMAT_REG = "^([a-zA-Z0-9]+[_|\\_|\\.\\-]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$";
	private static String PHONE_FORMAT_REG = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";

	public static boolean checkAccountNormal(String Account) {
		if (Account.matches(EMAIL_FORMAT_REG) || Account.matches(PHONE_FORMAT_REG)) {
			return true;
		} else {
			return false;
		}
	}
}
