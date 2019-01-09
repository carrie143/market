/*
 * 文件名：SendErrorMsg.java 版权：Copyright by www.guoren.com 描述： 修改人：汪洋 修改时间：2015年12月11日 跟踪单号： 修改单号：
 * 修改内容：
 */

package com.gop.mode.vo;

public class SendErrorMsg extends BaseMessage
{

    public SendErrorMsg(String msg)
    {
        super("400", msg);
    }

    public SendErrorMsg()
    {
        setStatus("400");
    }

}
