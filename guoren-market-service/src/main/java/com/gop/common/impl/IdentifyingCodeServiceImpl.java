/*
 * 文件名：RedisBankkeyCodeImpl.java 版权：Copyright by www.guoren.com 描述： 修改人：郑明海 修改时间：2015年12月27日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.gop.common.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.gop.common.IdentifyingCodeService;

import lombok.extern.slf4j.Slf4j;

@Service("IdentifyingCodeServiceImpl")
@Slf4j
public class IdentifyingCodeServiceImpl implements IdentifyingCodeService {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public Boolean checkSendCode(String key) {
		boolean hasKey = false;
		try {
			hasKey = redisTemplate.hasKey(Joiner.on(":").join(key, "avoid"));
		} catch (Exception e) {
			log.error("redis error", e);
			return null;
		}
		return hasKey;
	}

	public Boolean saveCode(final String code, final String key, final long liveTime, final long avoidTime) {

		try {
			String codeKey = Joiner.on(":").join(key, "code");

			String avoidKey = Joiner.on(":").join(key, "avoid");
			redisTemplate.execute(new RedisCallback<Boolean>() {

				@Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					connection.set(codeKey.getBytes(), code.getBytes());
					connection.expire(codeKey.getBytes(), liveTime);
					connection.set(avoidKey.getBytes(), "void".getBytes());
					connection.expire(avoidKey.getBytes(), avoidTime);
					return true;
				}
			});
			// redisTemplate.expire(codeKey, avoidTime, TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error("redis error", e);
			return false;
		}
		return true;

	}

	public String getCode(String key) {
		if (Strings.isNullOrEmpty(key)) {
			return null;
		}
		String code = "";
		String queryKey = Joiner.on(":").join(key, "code");
		try {
			code = Optional.fromNullable(redisTemplate.opsForValue().get(queryKey)).or("");
		} catch (Exception e) {
			log.error("redis error", e);
			return null;
		}
		log.info("获取到的key:{},value:{}", key, code);
		return code;
	}

	@Override
	public void delCode(String key) {
		if (Strings.isNullOrEmpty(key)) {
			return;
		}
		try {
			redisTemplate.delete(Joiner.on(":").join(key, "code"));
			redisTemplate.delete(Joiner.on(":").join(key, "avoid"));
		} catch (Exception e) {
			log.error("redis error", e);
		}

	}

	@Override
	public void updateAvoidKey(String key, final long avoidTime) {
		String avoidKey = Joiner.on(":").join(key, "avoid");
		redisTemplate.opsForValue().set(avoidKey, "void", avoidTime, TimeUnit.SECONDS);
	}

}
