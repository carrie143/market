//package com.gop.meessage.center.rabbitmq.callback;
//
//import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
//import org.springframework.amqp.rabbit.support.CorrelationData;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.gop.common.SendMessageService;
//import com.gop.common.impl.ProduceLogCache;
//import com.gop.domain.enums.SendMessageStatus;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Service("callBack")
//public class SendCallBack implements ConfirmCallback {
//
//	@Autowired
//	private SendMessageService sendMessageService;
//
//	@Autowired
//	private ProduceLogCache ProduceLogCache;
//
//	@Override
//	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//		if (null == correlationData)
//			return;
//		String id = correlationData.getId();
//		if (ack) {
//			try {
//				;
//				Integer result = sendMessageService.updateMessageStatus(Long.valueOf(id), SendMessageStatus.COMMITED,
//						(int) (Long.parseLong(id) % 10));
//				if (result <= 0) {
//					log.error("确认消息id" + id + "失败无法查到此记录");
//				}
//				ProduceLogCache.remove(Long.valueOf(id));
//
//			} catch (Exception e) {
//				log.error("确认消息id" + id + "失败" + e.getMessage());
//			}
//
//		} else {
//			log.error("消息id:" + id + "发送失败,失败原因" + cause);
//		}
//
//	}
//
//}
