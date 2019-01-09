package com.gop.sms.service.impl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FreeMarkTemplete {

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	private final String DEFAULT_ENCODING = "UTF-8";

	private final String DEFAULT_TEMPLETE_LOADERPATH = "classpath:/templates";

	public String getText(String encoding, String templateLoaderPath, Map<String, Object> variables,
			String tempalteNamem, Locale locale) {
		freeMarkerConfigurer.setDefaultEncoding(encoding);
		freeMarkerConfigurer.setTemplateLoaderPath(templateLoaderPath);
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		Template template;
		String htmlText = null;
		try {
			template = configuration.getTemplate(tempalteNamem, locale, encoding, true, true);
			htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, variables);
		} catch (Exception e) {
			log.error("模板加载异常|", e);
		}

		return htmlText;
	}

	public String getText(String tempalteNamem, Map<String, Object> variables, Locale locale) {
		freeMarkerConfigurer.setDefaultEncoding(DEFAULT_ENCODING);
		freeMarkerConfigurer.setTemplateLoaderPath(DEFAULT_TEMPLETE_LOADERPATH);
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		Template template;
		String htmlText = null;
		try {
			template = configuration.getTemplate(tempalteNamem, locale, DEFAULT_ENCODING, true, true);
			htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, variables);
		} catch (Exception e) {
			log.error("模板加载异常|", e);
		}

		return htmlText;
	}

}
