
package com.gop.meessage.center.jms.disruptor;

import org.springframework.beans.factory.InitializingBean;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.gop.meessage.center.jms.JmsFactory;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

@SuppressWarnings("rawtypes")
public class DefaultDisruptorCreate<T> implements JmsFactory<Disruptor>, InitializingBean {
	
	private Disruptor disruptor;

	private EventFactory<T> eventFactory;

	private ProducerType producerType;

	private WaitStrategy waitStrategy;
	private static final int DEFAULT_BUFFER_SIZE = 1024;
	private int bufferSize = DEFAULT_BUFFER_SIZE;

	public WaitStrategy getWaitStrategy() {
		return waitStrategy;
	}

	public void setWaitStrategy(WaitStrategy waitStrategy) {
		this.waitStrategy = waitStrategy;
	}

	public Disruptor getDisruptor() {
		return disruptor;
	}

	public void setDisruptor(Disruptor disruptor) {
		this.disruptor = disruptor;
	}

	public ProducerType getProducerType() {
		return producerType;
	}

	public void setProducerType(ProducerType producerType) {
		this.producerType = producerType;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public EventFactory<T> getEventFactory() {
		return eventFactory;
	}

	public void setEventFactory(EventFactory<T> eventFactory) {
		this.eventFactory = eventFactory;
	}

	@Override
	public Disruptor<?> get() {
		if (null == disruptor)
			CreateDisruptor();
		return this.disruptor;
	}

	@Override
	public void Destory() {
		disruptor.shutdown();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Preconditions.checkNotNull(eventFactory, "eventFactory can not be null!");
		this.producerType = Optional.fromNullable(producerType).or(ProducerType.MULTI);
		this.waitStrategy = Optional.fromNullable(waitStrategy).or(new BlockingWaitStrategy());

	}

	@SuppressWarnings("unchecked")

	public void CreateDisruptor() {
		this.disruptor = new Disruptor(eventFactory, bufferSize, new DaemonThreadFactory("disrputor"), producerType,
				waitStrategy);

	}

	public DefaultDisruptorCreate() {

	}

	public DefaultDisruptorCreate(ProducerType producerType, EventFactory<T> eventFactory, WaitStrategy strategy) {
		this.producerType = producerType;
		this.eventFactory = eventFactory;
		this.waitStrategy = strategy;
	}

}
