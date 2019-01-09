package com.gop.mode.vo;


import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;


public class BaseMessage implements Serializable
{
    private String status = "200";

    private String msg = "success";

    public BaseMessage()
    {

    }

    public BaseMessage(String status, String msg)
    {
        super();
        this.status = status;
        this.msg = msg;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    @Override
    public String toString()
    {
        return JSONObject.toJSONString(this);
    }

}
