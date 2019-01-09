
package com.gop.meessage.center.jms;


import com.lmax.disruptor.EventTranslator;


public interface GopJmsMessage<T>
{
    /*
     * 发送消息
     */
    public void SendMessage(EventTranslator<T> message);

    public void destoryMessgae();
    

    @Deprecated
    public void SendMessage(T message);
}
