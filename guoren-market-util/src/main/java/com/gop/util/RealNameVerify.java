package com.gop.util;


import java.util.regex.Pattern;


/**
 * 姓名校验
 * 
 * @author tjy [\u4e00-\u9fa5] 匹配汉字的正则
 */
public class RealNameVerify
{

    public static boolean nameValidate(String name)
    {
        if (Pattern.compile("^[\u4e00-\u9fa5a-zA-Z]+$").matcher(name).matches())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
