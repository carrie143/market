package com.gop.cache.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.gop.cache.RedisCache;

@Repository("redisCache")
public class RedisCacheImpl implements RedisCache {
	private static final Logger log = LoggerFactory.getLogger(RedisCacheImpl.class);

	@Autowired
	@Qualifier("stringRedisTemplate")
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public boolean saveValue(String phone, String value) {
		String key = Joiner.on(":").join("sms.send.count:userphone", phone);
		try {
			redisTemplate.opsForValue().set(key, value);
		} catch (Exception e) {
			log.error("redis error,key:{},value:{}", key, value, e);
			return false;
		}
		return true;
	}

	@Override
	public String getValue(String phone) {
		String value = "";
		String key = Joiner.on(":").join("sms.send.count:userphone", phone);
		try {
			value = Optional.fromNullable(redisTemplate.opsForValue().get(key)).or("0");
		} catch (Exception e) {
			log.error("redis error: key {}", key, e);
		}
		return value;
	}

	@Override
	public void delKey(String phone) {
		String key = Joiner.on(":").join("sms.send.count:userphone", phone);
		try {
			redisTemplate.delete(key);
		} catch (Exception e) {
			log.error("redis error: key {}", key, e);
		}

	}
}
