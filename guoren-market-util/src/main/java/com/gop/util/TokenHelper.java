package com.gop.util;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Slf4j
public class TokenHelper {

	@Autowired
	private StringRedisTemplate template;

	private static final String webName = "brokerWeb";

	private static final String secret = "915fc714cf7000744c908d1bc140166f";

	private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

	private static final String TOKEN_NAMESPACE = "broker:token:";

	private static final long THIRTY_MINUTES = 1000 * 60 * 30L;

	public String generateToken(Integer uid) {
		Assert.notNull(uid, "用户id不能为空");
		long currentTimeStamp = Instant.now().toEpochMilli();
		Token token = new Token();
		token.setUid(uid);
		token.setExpiration(new Date(currentTimeStamp + THIRTY_MINUTES));
		token.setToken(
				Jwts.builder().setIssuer(webName).setSubject(JSONObject.toJSONString(uid)).setAudience("web")
						.setIssuedAt(new Date(currentTimeStamp))
						.setExpiration(new Date(currentTimeStamp + 1000 * 60 * 60 * 24 * 180L))
						.signWith(SIGNATURE_ALGORITHM, secret).compact()
									);
		template.opsForValue().set(TOKEN_NAMESPACE + uid, JSONObject.toJSONString(token), 30L, TimeUnit.MINUTES);
		return token.getToken();
	}

	public Long getFromToken(String token) {
		return JSONObject.parseObject(getJsonStringFromBody(token), Long.class);
	}

	public Integer validToken(String token) {
		long uid = getFromToken(token);
		Token tokenDto = JSONObject.parseObject(template.opsForValue().get(TOKEN_NAMESPACE + uid), Token.class);
		if (tokenDto == null
				|| LocalDateTime.now().isAfter(tokenDto.getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
				|| !Objects.equals(token, tokenDto.getToken())) {
			throw new RuntimeException("TOKE_HAS_INVALID");
		}
		tokenDto.setExpiration(new Date(Instant.now().toEpochMilli() + THIRTY_MINUTES));
		template.opsForValue().set(TOKEN_NAMESPACE + uid, JSONObject.toJSONString(tokenDto), 30L, TimeUnit.MINUTES);
		return tokenDto.getUid();
	}

	public void removeToken(String token) {
		if (token == null) {
			log.warn("token is empty, ignore clean");
			return;
		}
		long uid = getFromToken(token);
		Token tokenDto = JSONObject.parseObject(template.opsForValue().get(TOKEN_NAMESPACE + uid), Token.class);
		if (tokenDto != null && Objects.equals(token, tokenDto.getToken())) {
			template.delete(TOKEN_NAMESPACE + uid);
			return;
		}
		log.warn("token invalid or not match, ignore clean");
	}

	private String getJsonStringFromBody(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}

	private Claims getAllClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	@Data
	static class Token {
		private Integer uid;
		private String token;
		private Date expiration;
	}

}
