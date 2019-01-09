package com.gop.util;


import com.gop.mode.vo.Result;


/**
 * 银行卡校验
 * 
 * @author tjy
 */
public class BankCardVerify
{
  

    /**
     * 校验银行卡卡号
     * 
     * @param cardId
     * @return
     */
    public static Result checkBankCard(String cardId)
    {
        Result result = new Result();
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == 'N')
        {
            result.setSuccess(false);
            result.setMessage("银行卡输入错误");
            return result;
        }
        if (cardId.charAt(cardId.length() - 1) == bit)
        {
            result.setSuccess(true);
            return result;
        }
        else
        {
            result.setSuccess(false);
            result.setMessage("银行卡输入错误");
            return result;
        }
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * 
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId)
    {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
            || !nonCheckCodeCardId.matches("\\d+"))
        {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i-- , j++ )
        {
            int k = chs[i] - '0';
            if (j % 2 == 0)
            {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }
}
