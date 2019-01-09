package com.gop.util;


import java.util.regex.Pattern;


/**
 * 手机验证
 * 
 * @author tjy
 */
public class PhoneVerify
{

    public static boolean validMobileNumber(String mobiles)
    {
        boolean result = Pattern.compile(
            "^(1)[0,3-5,7-8]\\d{9}").matcher(mobiles).matches();
        return result;
    }

    public static void main(String[] args)
    {
        System.out.println(validMobileNumber("17711111111"));
    }
}
