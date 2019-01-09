/*
 * 文件名：Result.java 版权：Copyright by www.guoren.com 描述： 修改人：zhengminghai 修改时间：2016年1月7日 跟踪单号： 修改单号：
 * 修改内容：
 */

package com.gop.mode.vo;


import java.util.HashMap;
import java.util.Map;


/**
 * 通用的返回包装类，用于处理，有失败信息方法调用 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhengminghai
 * @version 2016年1月7日
 * @see Result
 * @since
 */
public class Result
{
    @Override
    public String toString()
    {
        return "Result [success=" + success + ", message=" + message + "]";
    }

    private boolean success;

    /**
     * 携带一些简单信息
     */
    private String message;

    private Map<String, Object> extra = new HashMap<String, Object>();

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Map<String, Object> getExtra()
    {
        return extra;
    }

    public void setExtra(Map<String, Object> extra)
    {
        this.extra = extra;
    }

}
