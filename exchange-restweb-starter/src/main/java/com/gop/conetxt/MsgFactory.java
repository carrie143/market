package com.gop.conetxt;

import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class MsgFactory {

	private ResourceBundleMessageSource messageResource;

	private String[] baseNames;

	public String[] getBaseNames() {
		return baseNames;
	}

	public void setBaseNames(String[] baseNames) {
		this.baseNames = baseNames;
	}

	private synchronized void init() {
		if (null == messageResource) {
			messageResource = new ResourceBundleMessageSource();
			messageResource.addBasenames(baseNames);
		}
	}

	public String get(String code, Object[] args, Locale locale) {
		if (null == messageResource) {
			init();
		}
		return messageResource.getMessage(code, args, locale);
	}

}
