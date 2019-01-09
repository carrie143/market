package com.gop.offlineorder.service;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.OfflineOrderConst;
import com.gop.domain.enums.TokenOrderState;
import com.gop.exception.AppException;

@Component
public class TokenOrderStateContext {
	@Autowired
	private ApplicationContext applicationContext;

	private Map<TokenOrderState, TokenOrderStateHandler> map;

	public TokenOrderStateHandler getTokenOrderStateHandler(TokenOrderState tokenOrderState) {
		TokenOrderStateHandler tokenOrderStateHandler = map.get(tokenOrderState);
		if (tokenOrderStateHandler == null) {
			throw new AppException(OfflineOrderConst.TOKEN_ORDER_STATE_ERROR);
		}
		return tokenOrderStateHandler;

	}

	@PostConstruct
	public void init() {
		Map<String, TokenOrderStateHandler> TokenOrderStateHandlers = applicationContext
				.getBeansOfType(TokenOrderStateHandler.class);
		if (null == TokenOrderStateHandlers) {
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "未能注入handler");
		}
		Builder<TokenOrderState, TokenOrderStateHandler> buider = ImmutableMap.builder();

		TokenOrderStateHandlers.entrySet().forEach(a -> buider.put(a.getValue().getHandlerState(), a.getValue()));
		map = buider.build();
		System.out.println(map);
	}

}
