package com.gop.conetxt;

import java.util.Locale;

import com.google.common.base.Strings;
import com.gop.common.Environment;

import lombok.Data;


@Data
public class EnvironmentContxt implements Environment {

	private ApplicationConfig applicationConfig;

	private MsgFactory msgFactory;

	public String[] messages;

	public EnvironmentEnum getSystemEnvironMent() {
		if (applicationConfig == null) {
			return EnvironmentEnum.CHINA;
		}
		String environment = applicationConfig.getEnvironment();
		if (Strings.isNullOrEmpty(environment)) {
			return EnvironmentEnum.CHINA;
		} else {
			if (environment.equals(EnvironmentEnum.US.getExplian())) {
				return EnvironmentEnum.US;
			} else {
				return EnvironmentEnum.CHINA;
			}
		}
	}

	public String getMsg(String code, String... args) {
		if (msgFactory == null) {
			init();
		}
		EnvironmentEnum environmentEnum = getSystemEnvironMent();
		if (environmentEnum.equals(EnvironmentEnum.CHINA)) {
			return msgFactory.get(code, args, Locale.CHINA);
		} else {
			return msgFactory.get(code, args, Locale.US);
		}

	}

	private synchronized void init() {
		if (msgFactory == null) {
			if (null == messages || messages.length == 0) {
				messages = new String[1];
				messages[0] = "message";
			}
			msgFactory = new MsgFactory();
			msgFactory.setBaseNames(messages);

		}
	}

}
