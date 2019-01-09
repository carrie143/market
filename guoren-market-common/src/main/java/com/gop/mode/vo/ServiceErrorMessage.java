/*
 * 文件名：ServiceErrorMessage.java 版权：Copyright by www.guoren.com 描述： 修改人：汪洋 修改时间：2015年12月8日 跟踪单号：
 * 修改单号： 服务器异常返回的消息
 */

package com.gop.mode.vo;

public class ServiceErrorMessage extends BaseMessage
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1947369222164813186L;

	public ServiceErrorMessage()
    {
        setStatus("304");
        setMsg("服务器异常");
    }
}
