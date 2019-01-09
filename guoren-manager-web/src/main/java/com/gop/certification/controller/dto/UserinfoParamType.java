package com.gop.certification.controller.dto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;

public enum UserinfoParamType {
	UID("uid"), EMAIL("email");
	@Getter
	private String name;

	private UserinfoParamType() {
	}

	private UserinfoParamType(String name) {
		this.name = name;
	}

	public boolean validateValue(String value) {
		switch (this.name) {
		case "uid":
			return numValide(value);
		case "email":
			return emailValide(value);
		default:
			return false;
		}
	}

	private boolean numValide(String value) {
		String str = value;
		// 正则表达式规则
		String regEx = "^(\\d*)$";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		// 查找字符串中是否有匹配正则表达式的字符/字符串
		return matcher.matches();
	}

	private boolean emailValide(String value) {
		String str = value;
		// 正则表达式规则
		String regEx = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		// 查找字符串中是否有匹配正则表达式的字符/字符串
		return matcher.matches();
	}

}
