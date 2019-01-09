package com.gop.interceptor;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.gop.code.consts.CommonCodeConst;
import com.gop.conetxt.WebApiResponseFactory;
import com.gop.web.base.model.WebApiResponse;

public class Void2WebapiResponseIntecptor extends HandlerInterceptorAdapter {

	private WebApiResponseFactory webApiResponseFactory;

	public WebApiResponseFactory getWebApiResponseFactory() {
		return webApiResponseFactory;
	}

	public void setWebApiResponseFactory(WebApiResponseFactory webApiResponseFactory) {
		this.webApiResponseFactory = webApiResponseFactory;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (!response.isCommitted() ) {
			if (handler instanceof HandlerMethod) {
				HandlerMethod handlerMethod = (HandlerMethod) handler;
				if (handlerMethod.getMethod().getReturnType().isAssignableFrom(void.class)) {
					Locale locale = LocaleContextHolder.getLocale();
					WebApiResponse webApiResponse = webApiResponseFactory.get(CommonCodeConst.SERIVCE_SUCCESS, null,
							null, locale);
					response.setHeader("Content-type", "application/json;charset=utf-8");
					response.setCharacterEncoding("utf-8");
					JSON.writeJSONString(response.getWriter(), webApiResponse);
				}
			}
		}

		super.afterCompletion(request, response, handler, ex);
	}

}
