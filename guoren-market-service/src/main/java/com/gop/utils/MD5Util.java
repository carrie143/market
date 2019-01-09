package com.gop.utils;


import java.security.MessageDigest;


public class MD5Util
{

    public final static String genMD5Code(String s)
    {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f'};
        try
        {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md5 = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int l = md5.length;
            char str[] = new char[l * 2];
            for (int i = 0; i < l; i++ )
            {
                byte abyte = md5[i];
                str[i * 2] = hexDigits[abyte >>> 4 & 0xf];
                str[i * 2 + 1] = hexDigits[abyte & 0xf];
            }
            return new String(str);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "error: there is a exception.";
        }
    }

}
