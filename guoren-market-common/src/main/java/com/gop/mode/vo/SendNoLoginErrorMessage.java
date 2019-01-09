/*
* 文件名：ServiceErrorMessage.java 版权：Copyright by www.guoren.com
 * 修改单号： 服务器异常返回的消息
 */

package com.gop.mode.vo;

public class SendNoLoginErrorMessage extends BaseMessage
{
    public SendNoLoginErrorMessage()
    {
        setStatus("444");
        setMsg("未登录");
    }
}
