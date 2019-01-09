package com.gop.mode.vo;

/**
 * 登录失败返回的消息 〈功能详细描述〉
 * 
 * @author 汪洋
 * @version 2015年12月9日
 * @see CheckLoginErrorMessage
 * @since 1.0
 */
public class CheckLoginErrorMessage extends BaseMessage
{

    public CheckLoginErrorMessage()
    {
        setStatus("300");
        setMsg("用户登录/验证失败，请重新登录");
    }

    public CheckLoginErrorMessage(String msg)
    {
        setStatus("300");
        setMsg(msg);
    }

}
