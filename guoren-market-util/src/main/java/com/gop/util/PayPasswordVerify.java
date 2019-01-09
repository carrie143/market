package com.gop.util;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * 1. 交易密码为6位数字 2. 不要使用连续或相同的数字
 * 
 * @author tjy
 */
public class PayPasswordVerify
{
    public static String validate(String paypass)
    {
        if (!stepOne(paypass))
        {
            return "交易密码为6位数字";
        }
        else if (!stepTwo(paypass))
        {
            return "不要使用连续或相同的数字";
        }
        else
        {
            return "成功";
        }
    }

    private static boolean stepOne(String paypass)
    {
        return Pattern.compile("^\\d{6}$").matcher(paypass).matches();
    }

    @SuppressWarnings("unused")
    private static boolean stepTwo(String paypass)
    {
        List<String> list = list();
        for (int i = 0; i < list.size(); i++ )
        {
            if (list.get(i).equals(paypass))
            {
                return false;
            }
            break;
        }
        return true;
    }

    private static List<String> list()
    {
        List<String> list = new ArrayList<String>();
        list.add("000000");
        list.add("111111");
        list.add("222222");
        list.add("333333");
        list.add("444444");
        list.add("555555");
        list.add("666666");
        list.add("777777");
        list.add("888888");
        list.add("999999");
        list.add("012345");
        list.add("123456");
        list.add("234567");
        list.add("345678");
        list.add("456789");
        list.add("567890");
        list.add("098765");
        list.add("987654");
        list.add("876543");
        list.add("765432");
        list.add("654321");
        list.add("543210");
        return list;
    }
}
