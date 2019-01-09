package com.gop.web.base.controlleradvice;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.google.common.collect.ImmutableMap;
import com.gop.code.consts.CommonCodeConst;
import com.gop.conetxt.WebApiResponseFactory;
import com.gop.exception.AppException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class FaultBarrier {
	@Autowired
	@Qualifier("webApiResponseFactory")
	WebApiResponseFactory webApiResponseFactory;

	private static final ImmutableMap<Class<? extends Throwable>, String> EXCEPTION_MAPPINGS;
	static {
		final ImmutableMap.Builder<Class<? extends Throwable>, String> builder = ImmutableMap.builder();
		// SpringMVC中参数类型转换异常，常见于String找不到对应的ENUM而抛出的异常

		builder.put(UnsatisfiedServletRequestParameterException.class, CommonCodeConst.FIELD_ERROR);
		builder.put(IllegalArgumentException.class, CommonCodeConst.FIELD_ERROR);

		// HTTP Request Method不存在

		builder.put(NoHandlerFoundException.class, CommonCodeConst.INVALID_REQUEST);
		builder.put(MethodArgumentNotValidException.class, CommonCodeConst.FIELD_ERROR);
		builder.put(HttpRequestMethodNotSupportedException.class, CommonCodeConst.FIELD_ERROR);
		builder.put(MissingServletRequestParameterException.class, CommonCodeConst.FIELD_ERROR);
		builder.put(MissingPathVariableException.class, CommonCodeConst.FIELD_ERROR);
		builder.put(MethodArgumentTypeMismatchException.class, CommonCodeConst.FIELD_ERROR);
		// 要求有RequestBody的地方却传入了NULL
		builder.put(HttpMessageNotReadableException.class, CommonCodeConst.FIELD_ERROR);
		// 其他未被发现的异常
		builder.put(Exception.class, CommonCodeConst.FIELD_ERROR);
		EXCEPTION_MAPPINGS = builder.build();
	}

	@ExceptionHandler(AppException.class)
	@ResponseBody
	public Object exp(HttpServletRequest request, AppException ex) {

		Locale locale = LocaleContextHolder.getLocale();

		try {
			/**原来的代码逻辑里不会返回异常信息*/
//			if (StringUtils.isNotBlank(ex.getMessage())){
//				return webApiResponseFactory.get(ex.getErrCode(), ex.getAttach(), ex.getMessage());
//			}

			return webApiResponseFactory.get(ex.getErrCode(), ex.getFormat(), ex.getAttach(), locale);
		} catch (Exception e) {
			log.error("获取错误代码异常:", e);
			return webApiResponseFactory.get(CommonCodeConst.SERVICE_ERROR, null, null, locale);
		}

	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Object noHandlerexp(HttpServletRequest request, Exception ex) {

		String url = "";
		url += request.getServletPath();

		if (request.getQueryString() != null) {

			url += "?" + request.getQueryString();

		}

		Locale locale = LocaleContextHolder.getLocale();

		String code = EXCEPTION_MAPPINGS.get(ex.getClass());
		if (null != code) {

			return webApiResponseFactory.get(code, null, null, locale);
		}
		log.error("发现没有处理的异常url:{},e:{}", url, ex);
		log.error("stack", ex);

		return webApiResponseFactory.get(CommonCodeConst.SERVICE_ERROR, null, null, locale);

	}

}
