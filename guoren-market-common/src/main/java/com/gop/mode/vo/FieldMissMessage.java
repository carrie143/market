/*
 * 文件名：FiledMissMessage.java 版权：Copyright by www.guoren.com 描述： 修改人：汪洋 修改时间：2015年12月8日 跟踪单号： 修改单号：
 * 字段返回的消息
 */

package com.gop.mode.vo;

public class FieldMissMessage extends BaseMessage
{

    public FieldMissMessage()
    {
        setStatus("305");
        setMsg("传入的字段缺失或异常");
    }

}
