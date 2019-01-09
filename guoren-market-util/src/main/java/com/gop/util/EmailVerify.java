package com.gop.util;

import java.util.regex.Pattern;

/**
 * 手机验证
 * 
 * @author liu
 */
public class EmailVerify {

	public static boolean validEmailNumber(String email)
    {
    	//String regex="^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
    	String regex="^([a-zA-Z0-9]+[_|\\_|\\.\\-]?)*[a-zA-Z0-9-_]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$";
        boolean result = Pattern.compile(regex).matcher(email).matches();
        return result;
    }

	public static void main(String[] args) {
		System.out.println(validEmailNumber("123456_@qq.com"));
	}
}
