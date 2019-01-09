package com.gop.c2c.facade;

/**
 * C2C发送提醒消息
 * @author sunhaotian
 *
 */
public interface C2cMessageFacade {
	
	public void sendMessageByUid(Integer uid,String message);

}
