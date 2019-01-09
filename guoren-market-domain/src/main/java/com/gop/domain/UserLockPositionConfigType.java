package com.gop.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;

public enum UserLockPositionConfigType {
	LOCKDAY("lockday"), FOUNDATIONUID("foundationuid"), REWARDGRANTRATE("rewardgrantrate");

	@Getter
	private String name;

	private UserLockPositionConfigType() {
	}

	private UserLockPositionConfigType(String name) {
		this.name = name;
	}

	public static UserLockPositionConfigType getType(String name) {
		switch (name) {
		case "lockday":
			return LOCKDAY;
		case "foundationuid":
			return FOUNDATIONUID;
		case "rewardgrantrate":
			return REWARDGRANTRATE;
		default:
			return null;
		}
	}

	public boolean validateValue(String value) {
		switch (this.name) {
		case "lockday":
			return validateLockDay(value);
		case "foundationuid":
			return validateFoundationUid(value);
		case "rewardgrantrate":
			return validateRewardGrantRate(value);
		default:
			return false;
		}
	}

	private boolean validateLockDay(String value) {
		String str = value;
		// 正则表达式规则:大于零小于28
		String regEx = "^([1-9]|[1][0-9]|[2][0-8])$";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		// 查找字符串中是否有匹配正则表达式的字符/字符串
		return matcher.matches();
	}

	private boolean validateFoundationUid(String value) {
		String str = value;
		// 正则表达式规则:大于零
		String regEx = "^[1-9]\\d*$";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		// 查找字符串中是否有匹配正则表达式的字符/字符串
		return matcher.matches();
	}

	private boolean validateRewardGrantRate(String value) {
		String str = value;
	    // 正则表达式规则大于等于零小于等于1
	    String regEx = "^([1]|0\\.[0-9]+)$";
	    // 编译正则表达式
	    Pattern pattern = Pattern.compile(regEx);
	    // 忽略大小写的写法
	    // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(str);
	    // 查找字符串中是否有匹配正则表达式的字符/字符串
	    return matcher.matches();
	}
}
