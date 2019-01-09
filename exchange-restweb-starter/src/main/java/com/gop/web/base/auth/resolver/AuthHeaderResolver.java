package com.gop.web.base.auth.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;


public class AuthHeaderResolver implements HandlerMethodArgumentResolver {
	private static final String AUTH_HEADER = "authorization";
	private static final String SIGN = "sign";
	private static final String ACCESSKEY = "accessKey";
	private static final String FILED_SEPARATOR = ",";
	private static final String VALUE_SEPARATOR = "=";
	@Override
	public boolean supportsParameter(MethodParameter parameter) {

		if (parameter.hasParameterAnnotation(AuthForHeader.class)) {
			return true;
		}
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String header = webRequest.getHeader(AUTH_HEADER);
		if (webRequest.getHeader(SIGN)!=null && webRequest.getHeader(ACCESSKEY)!=null){
			header = SIGN +VALUE_SEPARATOR +webRequest.getHeader(SIGN);
			header = header +FILED_SEPARATOR + ACCESSKEY + VALUE_SEPARATOR + webRequest.getHeader(ACCESSKEY);
		}
		if (!parameter.getParameterType().isAssignableFrom(AuthContext.class)) {
			return null;
		} else {
			return AuthContext.build(header, webRequest);
		}
	}
}
