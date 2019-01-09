package com.gop.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gop.meessage.center.jms.disruptor.DefaultDisruptorCreate;
import com.gop.meessage.center.jms.disruptor.GopMessageJms;
import com.gop.meessage.center.jms.disruptor.eventfactory.MessageEventFactory;
import com.gop.meessage.center.jms.disruptor.eventhandler.SendToMqEventHandler;

//import com.gop.jms.disruptor.DefaultDisruptorCreate;
//import com.gop.jms.disruptor.GopMessageJms;
//import com.gop.jms.disruptor.eventfactory.MessageEventFactory;
//import com.gop.jms.disruptor.eventhandler.SendToMqEventHandler;

@Configuration
public class Disruptor {

	@Bean
	public MessageEventFactory messageEventFactory() {
		return new MessageEventFactory();
	}

	@Bean
	public DefaultDisruptorCreate orderjmsfactory() {
		DefaultDisruptorCreate defaultD = new DefaultDisruptorCreate();
		defaultD.setEventFactory(messageEventFactory());
		return defaultD;
	}


	@Bean("jmsMqTemplete")
	public GopMessageJms defaultDisruptorCreate(@Autowired SendToMqEventHandler sendToMqEventHandler) {
		GopMessageJms jmsMqTemplete = new GopMessageJms();
		jmsMqTemplete.setFactory(orderjmsfactory());
		List lst = new ArrayList<>();
		lst.add(sendToMqEventHandler);
		jmsMqTemplete.setHandlerList(lst);
		return jmsMqTemplete;
	}

}
