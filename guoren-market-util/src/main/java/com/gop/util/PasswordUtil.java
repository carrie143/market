package com.gop.util;

public class PasswordUtil {

	private static final String reg1 = "^[0-9]*$";

	private static final String hanzi = ".*[\u4e00-\u9fa5]+.*";

	private static final int minPwLen = 6;
	
	private static final int minPayPwLen = 8;

	private static final int maxPwLen = 20;

	public static boolean checkPasswordFormat(String s) {

		boolean flag = true;
		
//		Pattern p = Pattern.compile(hanzi);
//		Matcher m = p.matcher(s);
		
		if (s.matches(reg1) || s.matches(hanzi))
			flag = false;
		
		return flag;
	}

	public static boolean checkLoginPasswordLen(String s) {

		boolean flag = true;

		if (s.trim().length() < minPwLen || s.trim().length() > maxPwLen)
			flag = false;
		return flag;

	}
	
	
	public static boolean checkPayPasswordLen(String s) {

		boolean flag = true;
		
		if (s.trim().length() < minPayPwLen || s.trim().length() > maxPwLen)
			flag = false;
		
		return flag;

	}

}
