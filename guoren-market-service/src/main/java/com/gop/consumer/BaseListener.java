package com.gop.consumer;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.gop.common.MqDeadsMessageService;
import com.gop.domain.MqDeadsMessage;
import com.gop.util.UtilAll;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseListener implements MessageListener {

	private boolean neediDuplicated = false;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private MqDeadsMessageService deadsMessageService;

	@Override
	public void onMessage(Message message) {
		
       
		MessageProperties messageProperties = message.getMessageProperties();
		String charset = (String) Optional.fromNullable(messageProperties.getHeaders().get("charset")).or("UTF-8");
		String recvMessage = null;
		try {
			recvMessage = new String(message.getBody(), charset);
		} catch (UnsupportedEncodingException e3) {
			log.error("序列化消息失败" + message);
			return;
		}
		if (neediDuplicated) {
			// 去重
		} else {
			JSONObject jsonObject = null;
			try {
				jsonObject = JSONObject.parseObject(recvMessage);
				this.onMessage(jsonObject);
			} catch (Exception e) {
				log.error("消息消费异常",e);
				String messageId = null;
				Integer account = null;
				String StringAccount = null;
				try {
					messageId = "" + UtilAll.crc32(message.getBody());
					StringAccount = (String) redisTemplate.opsForValue().get(messageId);
				} catch (Exception e2) {
					try {
						MqDeadsMessage deadsMessage = new MqDeadsMessage();
						deadsMessage.setErrorDesc(e.getClass().getName() + ":" + e.getMessage());
						deadsMessage.setExchange(message.getMessageProperties().getReceivedExchange());
						deadsMessage.setRouter(message.getMessageProperties().getReceivedRoutingKey());
						deadsMessage.setMessage(recvMessage);
						deadsMessageService.saveDeadsMessage(deadsMessage);
					} catch (Exception e1) {
						log.error("插入数据库死信队列失败[" + recvMessage + "]");
					}
					return;
				}

				if (StringAccount == null) {
					account = 0;
				} else {
					account = Integer.parseInt(StringAccount);
				}
				account++;

				if (account > 3) {
					try {
						MqDeadsMessage deadsMessage = new MqDeadsMessage();
						deadsMessage.setErrorDesc(e.getClass().getName() + ":" + e.getMessage());
						deadsMessage.setExchange(message.getMessageProperties().getReceivedExchange());
						deadsMessage.setRouter(message.getMessageProperties().getReceivedRoutingKey());
						deadsMessage.setMessage(recvMessage);
						deadsMessageService.saveDeadsMessage(deadsMessage);
						log.error("消费消息失败:message:" + "[" + recvMessage + "]" + "失败原因:" + e);
					} catch (Exception e1) {
						log.error("插入数据库死信队列失败[" + recvMessage + "]");
					}
					return;
				} else {
					redisTemplate.opsForValue().set(messageId, Integer.toString(account));
					redisTemplate.expire(messageId, 5, TimeUnit.MINUTES);
				}
				Throwables.propagate(e);
			}

		}
	}

	public void setDuplicated(boolean bool) {
		this.neediDuplicated = bool;
	}

	public abstract void onMessage(JSONObject json);

}
