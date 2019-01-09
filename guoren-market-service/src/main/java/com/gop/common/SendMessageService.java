package com.gop.common;

import java.util.List;

import com.gop.domain.enums.SendMessageStatus;
import com.gop.mode.vo.ProduceLogVo;

public interface SendMessageService {

	@Deprecated
	public void sendMessage(Object message, String exchange, String router);

	@Deprecated
	public void sendMessage(List<ProduceLogVo> messages);

	@Deprecated
	public void sendMessage(ProduceLogVo message, ProduceLogVo... messages);

	public List<Long> tryMessage(List<ProduceLogVo> messages);

	public Long tryMessage(ProduceLogVo message);

	public void commitMessage(List<Long> messageIds);

	public void rollBackMessage(List<Long> messageIds);

	public void commitMessage(Long messageIds);

	public void rollBackMessage(Long messageIds);

	public Object getSendMessage(Long id);

	public Integer updateMessageStatus(Long id, SendMessageStatus status, int tableNum);

	public Integer confirmMessage(Long id, int tableNum);

	public boolean hasConsumeMessage(Long id);

}
