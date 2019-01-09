//package com.gop.common.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.atomic.AtomicLong;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.dao.DuplicateKeyException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.alibaba.fastjson.JSONObject;
//import com.gop.common.SendMessageService;
//import com.gop.domain.MqProduceLog;
//import com.gop.domain.enums.SendMessageStatus;
//import com.gop.exception.MessageCommitedException;
//import com.gop.mapper.MqProduceLogMapper;
//import com.gop.meessage.center.jms.GopJmsMessage;
//import com.gop.mode.vo.ProduceLogVo;
//import com.gop.util.IdHexWorker;
//import com.gop.util.SequenceUtil;
//import com.lmax.disruptor.EventTranslator;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Service
//@Slf4j
//public class SendMessageServiceImpl implements SendMessageService {
//
//	private static AtomicLong hash = new AtomicLong(1);
//	private static final int tableNum = 4;
//	@Autowired
//	private MqProduceLogMapper produceLogMapper;
//
//	@Autowired
//	private ProduceLogCache produceLogCache;
//
//	@Autowired
//	@Qualifier("jmsMqTemplete")
//	private GopJmsMessage<ProduceLogVo> jmsMessage;
//
//	@Override
//	@Deprecated
//	public void sendMessage(Object message, String exchange, String router) {
//		ProduceLogVo produceLogVo = new ProduceLogVo();
//		produceLogVo.setExchangeName(exchange);
//		produceLogVo.setKey(router);
//		produceLogVo.setMessage(message);
//		sendMessage(produceLogVo);
//	}
//
//	@Transactional
//	@Deprecated
//	public void sendMessage(List<ProduceLogVo> messages) {
//		int tableNum = getNextTableNum();
//		List<MqProduceLog> produceLogs = messages.stream()
//				.map(message -> new MqProduceLog(IdHexWorker.getNextId(tableNum), message.getExchangeName(),
//						message.getKey(), JSONObject.toJSONString(message.getMessage()), message.getStatus()))
//				.collect(Collectors.toList());
//		try {
//			produceLogMapper.insertProduceLogs(produceLogs, tableNum);
//		} catch (DuplicateKeyException e) {
//			log.error("重复的消息发送:" + produceLogs);
//			throw new MessageCommitedException("重复的消息");
//		}
//
//		for (ProduceLogVo produceLogVo : messages) {
//			jmsMessage.SendMessage(new EventTranslator<ProduceLogVo>() {
//				@Override
//				public void translateTo(ProduceLogVo event, long sequence) {
//					event.setId(produceLogVo.getId());
//					event.setKey(produceLogVo.getKey());
//					event.setMessage(produceLogVo.getMessage());
//					event.setExchangeName(produceLogVo.getExchangeName());
//					event.setStatus(produceLogVo.getStatus());
//					event.setTablenNum(tableNum);
//				}
//			});
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	@Transactional
//	public void sendMessage(ProduceLogVo message, ProduceLogVo... messages) {
//		List<ProduceLogVo> list = new ArrayList<ProduceLogVo>();
//		list.add(message);
//		for (ProduceLogVo produceLogVo : messages) {
//			list.add(produceLogVo);
//		}
//		sendMessage(list);
//	}
//
//	@Override
//	public Object getSendMessage(Long id) {
//		return produceLogMapper.selectByPrimaryKey(id, id.toString().charAt(id.toString().length() - 1) - 48);
//	}
//
//	@Override
//	public Integer updateMessageStatus(Long id, SendMessageStatus status, int tableNum) {
//		MqProduceLog produceLog = new MqProduceLog();
//		produceLog.setId(id);
//		produceLog.setStatus(status);
//		return produceLogMapper.updateByPrimaryKeySelective(produceLog, tableNum);
//
//	}
//
//	@Override
//	public boolean hasConsumeMessage(Long id) {
//		return false;
//	}
//
//	public Integer confirmMessage(Long id, int tableNum) {
//		return updateMessageStatus(id, SendMessageStatus.COMMITED, tableNum);
//
//	}
//
//	private int getNextTableNum() {
//		return (int) (hash.getAndIncrement() & (tableNum - 1));
//
//	}
//
//	@Transactional(propagation = Propagation.REQUIRES_NEW)
//	public List<Long> tryMessage(List<ProduceLogVo> messages) {
//		int tableNum = getNextTableNum();
//
//		List<MqProduceLog> produceLogs = messages.stream()
//				.map(message -> new MqProduceLog(SequenceUtil.getNextId(tableNum), message.getExchangeName(),
//						message.getKey(), JSONObject.toJSONString(message.getMessage()), message.getStatus()))
//				.collect(Collectors.toList());
//		produceLogMapper.insertProduceLogs(produceLogs, tableNum);
//
//		produceLogCache.put(produceLogs);
//
//		return produceLogs.stream().map(produceLog -> produceLog.getId()).collect(Collectors.toList());
//	}
//
//	@Override
//	public void commitMessage(List<Long> messageIds) {
//		for (Long messageId : messageIds) {
//			try {
//				final MqProduceLog produce = produceLogCache.get(messageId);
//				if (produce == null) {
//					log.error("获取缓存异常：" + messageId);
//					return;
//				}
//				jmsMessage.SendMessage(new EventTranslator<ProduceLogVo>() {
//					@Override
//					public void translateTo(ProduceLogVo event, long sequence) {
//						event.setId(produce.getId());
//						event.setKey(produce.getRouterKey());
//						event.setMessage(JSONObject.parseObject(produce.getMessage()));
//						event.setExchangeName(produce.getExchangeName());
//					}
//				});
//			} catch (ExecutionException e) {
//				log.error("提交消息异常:[" + messageIds + "]", e);
//			}
//		}
//
//	}
//
//	@Transactional(propagation = Propagation.REQUIRES_NEW)
//	public void rollBackMessage(List<Long> messageIds) {
//		try {
//			if (null != messageIds)
//				return;
//			for (int i = 0; i < messageIds.size(); i++) {
//				rollBackMessage(messageIds.get(i));
//			}
//		} catch (Exception e) {
//			log.error("取消消息异常:[" + messageIds + "]", e);
//		}
//	}
//
//	@Transactional(propagation = Propagation.REQUIRES_NEW)
//	public Long tryMessage(ProduceLogVo message) {
//		List<ProduceLogVo> lists = new ArrayList<>();
//		lists.add(message);
//		List<Long> list = tryMessage(lists);
//		return list.get(0);
//	}
//
//	@Override
//	public void commitMessage(Long messageId) {
//		try {
//			final MqProduceLog produce = produceLogCache.get(messageId);
//			if (produce == null) {
//				log.error("获取缓存异常：" + messageId);
//				return;
//			}
//			jmsMessage.SendMessage(new EventTranslator<ProduceLogVo>() {
//				@Override
//				public void translateTo(ProduceLogVo event, long sequence) {
//					event.setId(produce.getId());
//					event.setKey(produce.getRouterKey());
//					event.setMessage(JSONObject.parseObject(produce.getMessage()));
//					event.setExchangeName(produce.getExchangeName());
//				}
//			});
//		} catch (ExecutionException e) {
//			log.error("提交消息异常:消息id为：" + messageId, e);
//		}
//
//	}
//
//	@Transactional(propagation = Propagation.REQUIRES_NEW)
//	public void rollBackMessage(Long messageId) {
//		MqProduceLog produceLog = new MqProduceLog();
//		produceLog.setId(messageId);
//		produceLog.setStatus(SendMessageStatus.ROLLBACK);
//		try {
//			int result = produceLogMapper.updateByPrimaryKeySelective(produceLog, (int) (messageId % 10));
//			if (result <= 0) {
//				throw new IllegalArgumentException("更新消息失败没有发现消息");
//			}
//		} catch (Exception e) {
//
//			log.error("更新状态失败:id:{},error:{}", messageId, e);
//		}
//	}
//
//}
