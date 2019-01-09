package com.gop.meessage.center.jms.disruptor.eventfactory;

import com.gop.mode.vo.ProduceLogVo;
import com.lmax.disruptor.EventFactory;

public class MessageEventFactory implements EventFactory<ProduceLogVo> {

	@Override
	public ProduceLogVo newInstance() {

		return new ProduceLogVo();
	}

}
