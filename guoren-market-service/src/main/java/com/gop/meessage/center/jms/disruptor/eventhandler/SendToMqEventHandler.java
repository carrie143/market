package com.gop.meessage.center.jms.disruptor.eventhandler;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gop.mode.vo.ProduceLogVo;
import com.lmax.disruptor.EventHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SendToMqEventHandler implements EventHandler<ProduceLogVo> {

	@Autowired
	RabbitTemplate rabbitMqTemplete;

	@Override
	public void onEvent(ProduceLogVo produceLogVo, long sequence, boolean endOfBatch) throws Exception {
		String exchange = produceLogVo.getExchangeName();
		String key = produceLogVo.getKey();
		Long id = produceLogVo.getId();
		Object message = produceLogVo.getMessage();
		try {
			MessageProperties messageProperties = new MessageProperties();

			String correlationDataId = produceLogVo.getId().toString();
			messageProperties.setMessageId(correlationDataId);
			messageProperties.setCorrelationId(correlationDataId.getBytes());
			Message rabbitmqMessage = new Message(message.toString().getBytes(), messageProperties);

			rabbitMqTemplete.send(exchange, key, rabbitmqMessage, new CorrelationData(correlationDataId));
			// rabbitMqTemplete.convertAndSend(exchange, key, message, new
			// CorrelationData(id.toString()));
		} catch (Exception e) {
			log.error("发送mq异常" + e);
		}
	}

}
