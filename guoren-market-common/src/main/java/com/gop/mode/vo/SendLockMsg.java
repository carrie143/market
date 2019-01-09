/*
 * 文件名：SendErrorMsg.java 版权：Copyright by www.guoren.com 描述： 修改人：汪洋 修改时间：2015年12月11日 跟踪单号： 修改单号：
 * 修改内容：
 */

package com.gop.mode.vo;

public class SendLockMsg extends BaseMessage
{
    private Integer lockTimes;

    public SendLockMsg()
    {
        setStatus("310");
        setMsg("用户支付密码锁住");
    }

    public Integer getLockTimes()
    {
        return lockTimes;
    }

    public void setLockTimes(Integer lockTimes)
    {
        this.lockTimes = lockTimes;
    }

}