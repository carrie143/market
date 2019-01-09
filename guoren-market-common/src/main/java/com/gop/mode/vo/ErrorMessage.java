/**
 * js验证失效，或者夸js访问返回的消息
 */

package com.gop.mode.vo;

public class ErrorMessage extends BaseMessage
{

    public ErrorMessage()
    {
        setStatus("302");
        setMsg("js验证失效/js跨界访问");
    }

}
