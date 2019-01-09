package com.gop.domain.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Strings;

public enum ConfigAssetType {
	WITHDRAWMIN("withdrawmin"), WITHDRAWMAX("withdrawmax"), WITHDRAWMINFEE("withdrawminfee"),CLOUDFEE("cloudfee"),
	//1.5.1版本注销
//	WITHDRAWDAILYMAX("withdrawdailymax"),
	TRANSFERKEY("rabbitmqkey"),
	//1.5.1版本添加
	WITHDRAWLEVEL_0("withdrawlevel_0"),
	WITHDRAWLEVEL_1("withdrawlevel_1"),
	WITHDRAWLEVEL_2("withdrawlevel_2"),
	WITHDRAWLEVEL_3("withdrawlevel_3"),
	//1.5.5版本添加
	DEPOSITLEVEL_0("depositlevel_0"),
	DEPOSITLEVEL_1("depositlevel_1"),
	DEPOSITLEVEL_2("depositlevel_2"),
	DEPOSITLEVEL_3("depositlevel_3"),
	DEPOSITLEVEL_DEFAULT("depositlevel_default"),
	//1.5.9版本添加
	WITHDRAWPRECISION("withdrawprecision"),//提现精度,供前端查询使用
	ASSETCODECONFIRMNUM("assetcodeconfirmnum"),//币种的网络确认数
	ASSETCODELOGO("assetcodelogo")//币种图片地址
	;

	private String name;

	public String getName() {
		return name;
	}

	private ConfigAssetType(String name) {
		this.name = name;
	}

	private ConfigAssetType() {
	}

	public static ConfigAssetType getType(String name) {
		switch (name) {
			case "cloudfee":
				return CLOUDFEE;
		case "withdrawmin":
			return WITHDRAWMIN;
		case "withdrawmax":
			return WITHDRAWMAX;
		case "withdrawminfee":
			return WITHDRAWMINFEE;
//		case "withdrawdailymax":
//			return WITHDRAWDAILYMAX;
		case "withdrawlevel_0":
			return WITHDRAWLEVEL_0;
		case "withdrawlevel_1":
			return WITHDRAWLEVEL_1;
		case "withdrawlevel_2":
			return WITHDRAWLEVEL_2;
		case "withdrawlevel_3":
			return WITHDRAWLEVEL_3;
		case "depositlevel_0":
			return DEPOSITLEVEL_0;
		case "depositlevel_1":
			return DEPOSITLEVEL_1;
		case "depositlevel_2":
			return DEPOSITLEVEL_2;
		case "depositlevel_3":
			return DEPOSITLEVEL_3;
		case "depositlevel_default":
			return DEPOSITLEVEL_DEFAULT;
		case "withdrawprecision":
			return WITHDRAWPRECISION;
		case "assetcodeconfirmnum":
			return ASSETCODECONFIRMNUM;
		case "assetcodelogo":
			return ASSETCODELOGO;
		default:
			return null;
		}
	}

	public boolean validValue(String value) {
		switch (this.name) {
			case "cloudfee":
				return validWithdrawMinFee(value);
		case "withdrawmin":
			return validWithdrawMin(value);
		case "withdrawmax":
			return validWithdrawMax(value);
		case "withdrawminfee":
			return validWithdrawMinFee(value);
//		case "withdrawdailymax":
//			return validWithdrawDailyMax(value);
		case "withdrawlevel_0":
			return validWithdrawLevel(value);
		case "withdrawlevel_1":
			return validWithdrawLevel(value);
		case "withdrawlevel_2":
			return validWithdrawLevel(value);
		case "withdrawlevel_3":
			return validWithdrawLevel(value);
		case "depositlevel_0":
			return validWithdrawLevel(value);
		case "depositlevel_1":
			return validWithdrawLevel(value);
		case "depositlevel_2":
			return validWithdrawLevel(value);
		case "depositlevel_3":
			return validWithdrawLevel(value);
		case "depositlevel_default":
			return validWithdrawLevel(value);
		case "withdrawprecision":
			return validWithdrawPrecision(value);
		case "assetcodeconfirmnum":
			return validCodeonfirmum(value);
		case "assetcodelogo":
			return Strings.isNullOrEmpty(value);
		default:
			return false;
		}
	}
	
	private boolean validCodeonfirmum(String value) {
		String str = value;
		// 正则表达式规则:大于等于零正整数
		// String regEx = "^([01](\\.0+)?|0\\.[0-9]+)$";
		String regEx =  "^([0-9])|(\\d*[1-9]\\d*[0-9])$";
		
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		// 查找字符串中是否有匹配正则表达式的字符/字符串
		return matcher.matches();
	}
	private boolean validWithdrawPrecision(String value) {
		String str = value;
		// 正则表达式规则:大于等于零正整数
		// String regEx = "^([01](\\.0+)?|0\\.[0-9]+)$";
		String regEx =  "^([0-9])|(\\d*[1-9]\\d*[0-9])$";
		
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		// 查找字符串中是否有匹配正则表达式的字符/字符串
		return matcher.matches();
	}

	private boolean validWithdrawMin(String value) {
		String str = value;
		// 正则表达式规则大于零即可
		// String regEx = "^([01](\\.0+)?|0\\.[0-9]+)$";
		String regEx = "^([1-9]\\d*(\\.\\d*[1-9])?)|(0\\.\\d*[1-9])$";

		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		// 查找字符串中是否有匹配正则表达式的字符/字符串
		return matcher.matches();
	}

	private boolean validWithdrawMax(String value) {
		String str = value;
		// 正则表达式规则,大于零
		String regEx = "^([1-9]\\d*(\\.\\d*[1-9])?)|(0\\.\\d*[1-9])$";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		// 查找字符串中是否有匹配正则表达式的字符/字符串
		return matcher.matches();
	}

	private boolean validWithdrawMinFee(String value) {
		String str = value;
		// 正则表达式规则,大于等于零
		String regEx = "^([1-9]\\d*(\\.\\d*[1-9])?)|(0\\.\\d*[1-9])|(0)$";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		// 查找字符串中是否有匹配正则表达式的字符/字符串
		return matcher.matches();
	}

//	private boolean validWithdrawDailyMax(String value) {
//		String str = value;
//		// 正则表达式规则,大于零
//		String regEx = "^([1-9]\\d*(\\.\\d*[1-9])?)|(0\\.\\d*[1-9])$";
//		// 编译正则表达式
//		Pattern pattern = Pattern.compile(regEx);
//		// 忽略大小写的写法
//		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
//		Matcher matcher = pattern.matcher(str);
//		// 查找字符串中是否有匹配正则表达式的字符/字符串
//		return matcher.matches();
//	}
	
	private boolean validWithdrawLevel(String value) {
		String str = value;
		// 正则表达式规则,大于等于零或者等于-1
		String regEx = "^([0-9]\\d*(\\.\\d*[1-9])?)|(0\\.\\d*[1-9])|(-1)$";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		// 查找字符串中是否有匹配正则表达式的字符/字符串
		return matcher.matches();
	}
	private boolean validDepositLevel(String value) {
		String str = value;
		// 正则表达式规则,大于等于零或者等于-1
		String regEx = "^([0-9]\\d*(\\.\\d*[1-9])?)|(0\\.\\d*[1-9])|(-1)$";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		// 查找字符串中是否有匹配正则表达式的字符/字符串
		return matcher.matches();
	}

}
