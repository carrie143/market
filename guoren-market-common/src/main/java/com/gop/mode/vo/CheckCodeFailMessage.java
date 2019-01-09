/*
 * 文件名：CheckCodeFailMessage.java 版权：Copyright by www.guoren.com 描述： 修改人：汪洋 修改时间：2015年12月9日 跟踪单号：
 * 修改单号： 修改内容：
 */

package com.gop.mode.vo;

public class CheckCodeFailMessage extends BaseMessage
{

    public CheckCodeFailMessage()
    {
        setMsg("failure");
        setStatus("400");
    }

}
