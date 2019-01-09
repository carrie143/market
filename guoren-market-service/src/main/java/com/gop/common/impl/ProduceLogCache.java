package com.gop.common.impl;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.gop.domain.MqProduceLog;
import com.gop.mapper.MqProduceLogMapper;

@Component
public class ProduceLogCache implements InitializingBean {
	private LoadingCache<Long, MqProduceLog> localCache;

	@Autowired
	private MqProduceLogMapper produceLogMapper;

	@Override
	public void afterPropertiesSet() throws Exception {
		localCache = CacheBuilder.newBuilder().concurrencyLevel(10).expireAfterWrite(60, TimeUnit.SECONDS)
				.initialCapacity(500).maximumSize(10000).removalListener(new RemovalListener<Long, MqProduceLog>() {

					@Override
					public void onRemoval(RemovalNotification<Long, MqProduceLog> notification) {

					}
				}).build(new CacheLoader<Long, MqProduceLog>() {
					@Override
					public MqProduceLog load(Long key) throws Exception {
						String strValue = key.toString();
						int tableNum = strValue.charAt(strValue.length() - 1) - 48;
						return produceLogMapper.selectByPrimaryKey(key, tableNum);
					}
				});
	}

	public MqProduceLog get(Long key) throws ExecutionException {
		return localCache.get(key);
	}

	public void put(MqProduceLog... logs) {
		if (logs == null)
			return;
		for (int i = 0; i < logs.length; i++) {
			localCache.put(logs[i].getId(), logs[i]);
		}
	}

	public void put(List<MqProduceLog> logs) {
		if (logs == null)
			return;
		for (int i = 0; i < logs.size(); i++) {
			localCache.put(logs.get(i).getId(), logs.get(i));
		}
	}

	public void remove(Long id) {
		localCache.invalidate(id);
	}

	public void remove(MqProduceLog... logs) {
		if (logs == null)
			return;
		for (int i = 0; i < logs.length; i++) {
			localCache.invalidate(logs[i].getId());
		}
	}

}
