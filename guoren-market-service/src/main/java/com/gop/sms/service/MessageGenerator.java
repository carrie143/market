package com.gop.sms.service;

import com.gop.common.Environment;

public interface MessageGenerator<T> {

	public String generatorMessage(Environment.EnvironmentEnum environmentEnum, T t);

}
