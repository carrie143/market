
package com.gop.meessage.center.jms.disruptor;


import java.util.List;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.base.Optional;
import com.gop.meessage.center.jms.GopJmsMessage;
import com.gop.meessage.center.jms.JmsFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;


public class GopMessageJms<T> implements InitializingBean, DisposableBean, GopJmsMessage<T>
{
    @SuppressWarnings("rawtypes")
    private JmsFactory<Disruptor> factory;

    @SuppressWarnings("rawtypes")
    private List<EventHandler> handlerList;

    private Disruptor<T> disruptor;

    private EventHandlerGroup<T> eventHandlerGroup;

    @SuppressWarnings("rawtypes")
    public JmsFactory<Disruptor> getFactory()
    {
        return factory;
    }

    public Disruptor<T> getDisruptor()
    {
        return disruptor;
    }

    public void setDisruptor(Disruptor<T> disruptor)
    {
        this.disruptor = disruptor;
    }

    public EventHandlerGroup<T> getEventHandlerGroup()
    {
        return eventHandlerGroup;
    }

    public void setEventHandlerGroup(EventHandlerGroup<T> eventHandlerGroup)
    {
        this.eventHandlerGroup = eventHandlerGroup;
    }

    public void setFactory(@SuppressWarnings("rawtypes") JmsFactory<Disruptor> factory)
    {
        this.factory = factory;
    }

    public GopMessageJms()
    {

    }

    public GopMessageJms(@SuppressWarnings("rawtypes") JmsFactory<Disruptor> factory)
    {

        this.factory = factory;
    }

    @Override
	public void afterPropertiesSet()
        throws Exception
    {
        init();
    }

    @SuppressWarnings("unchecked")
    public void init()
    {
        this.factory = Optional.fromNullable(factory).or(new DefaultDisruptorCreate<T>());
        this.disruptor = factory.get();
        disruptor.handleEventsWith(handlerList.toArray(new EventHandler[handlerList.size()]));
        disruptor.start();
    }

    @Override
	public void SendMessage(EventTranslator<T> message)
    {
        disruptor.publishEvent(message);
    }

    public void addEvent(EventHandler<T> eventHandler)
    {
        handlerList.add(eventHandler);
    }

    @SuppressWarnings("rawtypes")
    public void setHandlerList(List<EventHandler> list)
    {
        this.handlerList = list;
    }

    @Override
	public void destroy()

    {
        factory.Destory();

    }

    @Override
	public void SendMessage(T message)
    {

    }

    @Override
    public void destoryMessgae()
    {

        destroy();

    }


}
