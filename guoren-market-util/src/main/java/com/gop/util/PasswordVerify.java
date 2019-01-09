package com.gop.util;


import java.util.regex.Pattern;


/**
 * 密码的校验 1. 6-20位字符 2. 只能包含字母、数字以及标点符号（除空格） 3. 大写字母、小写字母、数字以和标点符号至少两种
 * 
 * @author tjy
 */
public class PasswordVerify
{
    public static String validate(String password)
    {
        if (!stepOne(password))
        {
            return "登录密码6-20位字符";
        }
        else if (!stepTwo(password))
        {
            return "登录密码只能包含字母、数字以及标点符号（除空格）";
        }
        else if (!stepThree(password))
        {
            return "登录密码大写字母、小写字母、数字以和标点符号至少两种";
        }
        else
        {
            return "成功";
        }
    }

    private static boolean stepOne(String password)
    {
        if (password.length() > 5 && password.length() < 21)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private static boolean stepTwo(String password)
    {
        if (Pattern.compile("^[^\\s]+$").matcher(password).matches()
            && Pattern.compile("^([\\dA-Za-z\\W_]+)$").matcher(password).matches())
        {
            return true;
        }
        return false;
    }

    private static boolean stepThree(String password)
    {
        return Pattern.compile("^(?![A-Z]+$)(?![a-z]+$)(?!\\d+$)(?![\\W_]+$)\\S+$").matcher(
            password).matches();
    }

    public static void main(String[] args)
    {
        System.out.println(validate("dadfsfz"));
    }
}
