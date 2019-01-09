package com.gop.web.base.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.gop.conetxt.WebApiResponseFactory;
import com.gop.interceptor.Void2WebapiResponseIntecptor;
import com.gop.web.base.auth.resolver.AuthHeaderResolver;
import com.gop.web.base.converter.WebapiResponseHttpConverter;

@Configuration
@ConditionalOnClass(WebApiResponseFactory.class)
public class MarketWebMvcConfig extends WebMvcConfigurerAdapter {
	@Autowired
	WebApiResponseFactory webApiResponseFactory;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

		argumentResolvers.add(new AuthHeaderResolver());
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

		WebapiResponseHttpConverter webapiResponseHttpConverter = new WebapiResponseHttpConverter();

		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setCharset(Charset.forName("UTF-8"));
		fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue, SerializerFeature.QuoteFieldNames,
				SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteEnumUsingName,
				SerializerFeature.WriteBigDecimalAsPlain);
		fastJsonConfig.setWriteContentLength(true);
		webapiResponseHttpConverter.setFastJsonConfig(fastJsonConfig);
		webapiResponseHttpConverter.setWebApiResponseFactory(webApiResponseFactory);
		List<MediaType> lists = new ArrayList<>();
		lists.add(MediaType.APPLICATION_JSON_UTF8);
		webapiResponseHttpConverter.setSupportedMediaTypes(lists);
		converters.add(webapiResponseHttpConverter);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		Void2WebapiResponseIntecptor conventerVoidIntecptor = new Void2WebapiResponseIntecptor();
		conventerVoidIntecptor.setWebApiResponseFactory(webApiResponseFactory);
		registry.addInterceptor(conventerVoidIntecptor);
	}

}
