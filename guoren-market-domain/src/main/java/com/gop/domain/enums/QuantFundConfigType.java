package com.gop.domain.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;

public enum QuantFundConfigType {
	TICKETSTATUS("ticketstatus"), // 门票开关
	TICKETASSETCODE("ticketassetcode"), // 门票所需币种
	TICKETASSETAMOUNT("ticketassetamount"), // 门票所需币种数量
	TICKETBEGINDATE("ticketbegindate"), // 门票开放购买时间
	TICKETENDDATE("ticketenddate"), // 门票售卖关闭时间
	TICKETUID("ticketuid"), // 门票代收UID
	FUNDBEGINDATE("fundbegindate"), // 基金开始时间
	FUNDENDDATE("fundenddate"), // 基金结束时间
	FUNDUID("funduid"), // 基金代收UID
	FUNDINCOMEASSETCODE("fundincomeassetcode"), // 基金收益币种
	FUNDINCOMERATE("fundincomerate"), // 基金收益率
	FUNDLIQUIDATIONDATE("fundliquidationdate"), // 基金清算日
	KYCASSETCODELIMITED("kycassetcodelimited"), // 认证用户基金购买限额
	UNKYCASSETCODELIMITED("unkycassetcodelimited");// 未认证用户基金购买限额
	
	@Getter
	private String name;

	QuantFundConfigType(String name) {
		this.name = name;
	}

	QuantFundConfigType() {
	}

	public static QuantFundConfigType getType(String name) {
		switch (name) {
		case "ticketstatus":
			return TICKETSTATUS;
		case "ticketassetcode":
			return TICKETASSETCODE;
		case "ticketassetamount":
			return TICKETASSETAMOUNT;
		case "ticketbegindate":
			return TICKETBEGINDATE;
		case "ticketenddate":
			return TICKETENDDATE;
		case "fundbegindate":
			return FUNDBEGINDATE;
		case "ticketuid":
			return TICKETUID;
		case "fundenddate":
			return FUNDENDDATE;
		case "fundliquidationdate":
			return FUNDLIQUIDATIONDATE;
		case "funduid":
			return FUNDUID;
		case "fundincomeassetcode":
			return FUNDINCOMEASSETCODE;
		case "fundincomerate":
			return FUNDINCOMERATE;
		case "kycassetcodelimited":
			return KYCASSETCODELIMITED;
		case "unkycassetcodelimited":
			return UNKYCASSETCODELIMITED;
		default:
			return null;
		}
	}

	public boolean validateValue(String value) {
		switch (this.name) {
		case "ticketstatus":
			return validateStatus(value);
		case "ticketassetamount":
			;
		case "ticketbegindate":
			;
		case "ticketenddate":
			;
		case "fundbegindate":
			;
		case "fundliquidationdate":
			;
		case "fundenddate":
			return validateInt(value);
		case "kycassetcodelimited":
			;
		case "unkycassetcodelimited":
			;
		case "fundincomerate":
			return validateNum(value);
		case "fundincomeassetcode":
			;
		case "ticketassetcode":
			;
		case "ticketuid":
			;
		case "funduid":
			return true;
		default:
			return false;
		}
	}

	private boolean validateStatus(String value) {
		return "on".equalsIgnoreCase(value) || "off".equalsIgnoreCase(value);
	}

	private boolean validateNum(String value) {
		String str = value;
		// 正则表达式规则,大于零浮点数
		// String regEx = "^([1-9]\\d*(\\.\\d*[1-9])?)|(0\\.\\d*[1-9])$";
		String regEx = "^[1-9]+\\d*$";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		// 查找字符串中是否有匹配正则表达式的字符/字符串
		return matcher.matches();
	}

	private boolean validateInt(String value) {
		String str = value;
		// 正则表达式规则,大于零整数
		String regEx = "^[1-9]+\\d*$";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		// 查找字符串中是否有匹配正则表达式的字符/字符串
		return matcher.matches();
	}
}
