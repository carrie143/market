/*
 * 文件名：MaskUtils.java 版权：Copyright by www.guoren.com 描述： 修改人：zhengminghai 修改时间：2016年1月27日 跟踪单号：
 * 修改单号： 修改内容：
 */

package com.gop.util;


import org.apache.commons.lang.StringUtils;

import com.google.common.base.Optional;


/**
 * 马赛克
 * 
 * @author zhengminghai
 * @version 2016年1月27日
 * @see MaskUtils
 * @since
 */
public class MaskUtils
{

    public static String maskPhone(String phone)
    {
        phone = Optional.fromNullable(phone).or("");
        if (phone.length() == 11)// 手机号长度
        {
            phone = phone.substring(0, 3) + "****" + phone.substring(7);
        }
        return phone;
    }

    public static String maskBankCard(String card)
    {
        card = Optional.fromNullable(card).or("");
        if (card.length() > 8)
        {
            card = card.substring(0, 4) + " **** **** "
                   + card.substring(card.length() - 4, card.length());
        }
        return card;
    }

    public static String maskGopAddress(String address)
    {
        address = Optional.fromNullable(address).or("");
        if (address.length() > 8)
        {
            address = address.substring(0, 8) + StringUtils.repeat("*", address.length() - 8);
        }
        return address;
    }

    public static void main(String[] args)
    {
        System.out.println(MaskUtils.maskBankCard(""));
    }

}
