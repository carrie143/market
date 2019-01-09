package com.gop.currency.withdraw.gateway.service.ulpay.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 2015/5/22.
 */
public class StaticFunctions {
	private static final Logger logger = LoggerFactory.getLogger(StaticFunctions.class);

	final static DecimalFormat NF_YUAN = new DecimalFormat("#####################0.00");
	final static DecimalFormat NF_FEN = new DecimalFormat("#####################0");

	public static BigDecimal moneyYuan2Fen(BigDecimal yuan) {
		return yuan == null ? null : formatFen(yuan.multiply(new BigDecimal("100")));
	}

	public static String moneyYuan2FenStr(BigDecimal yuan) {
		return yuan == null ? null : NF_FEN.format(yuan.multiply(new BigDecimal("100")));
	}

	public static BigDecimal formatYuan(BigDecimal yuan) {
		return yuan == null ? null : new BigDecimal(NF_YUAN.format(yuan));
	}

	public static BigDecimal formatFen(BigDecimal fen) {
		return fen == null ? null : new BigDecimal(NF_FEN.format(fen));
	}

	/**
	 * 带毫秒
	 *
	 * @return
	 */
	public static String getNowDateTimeMStr() {
		return getDateTimeMStr(new Date());
	}

	/**
	 * 带毫秒
	 *
	 * @param date
	 * @return
	 */
	public static String getDateTimeMStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return format.format(date);
	}

	/**
	 * 不带毫秒
	 *
	 * @return
	 */
	public static String getNowDateTimeStr() {
		return getDateTimeStr(new Date());
	}

	/**
	 * 不带毫秒
	 *
	 * @param date
	 * @return
	 */
	public static String getDateTimeStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(date);
	}

	/**
	 * 不带毫秒
	 *
	 * @return
	 */
	public static String getNowDateStr() {
		return getDateStr(new Date());
	}

	/**
	 * 不带毫秒
	 *
	 * @param date
	 * @return
	 */
	public static String getDateStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(date);
	}

	public static Date getDateFromStr(String str, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * unicode转字符串
	 *
	 * @param str
	 * @return
	 */
	public static String unicode2Str(String str) {
		if (str == null)
			return null;
		StringBuffer sb = new StringBuffer();
		String[] arr = str.split("\\\\u");
		int len = arr.length;
		sb.append(arr[0]);
		for (int i = 1; i < len; i++) {
			String tmp = arr[i];
			if (tmp.length() == 4) {
				char c = (char) Integer.parseInt(tmp.substring(0, 4), 16);
				sb.append(c);
				sb.append(tmp.substring(4));
			} else {
				sb.append("\\u").append(tmp);
			}
		}
		return sb.toString();
	}

	/**
	 * 字符串转unicode
	 *
	 * @param str
	 * @return
	 */
	public static String str2Unicode(String str) {
		if (str == null)
			return null;
		StringBuffer sb = new StringBuffer();
		char[] charArr = str.toCharArray();
		for (char ch : charArr) {
			if (ch > 128) {
				sb.append("\\u" + Integer.toHexString(ch));
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	/**
	 * 计算两个字符串格式（yyyyMMdd）之间的天数差
	 *
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static int getDateIntervalDays(String fromDate, String toDate) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

		Date date1 = format.parse(fromDate);
		Date date2 = format.parse(toDate);

		return (int) ((date2.getTime() - date1.getTime()) / 24 * 60 * 60 * 1000);

	}

	/**
	 * 将BigDecimal转换成字符串
	 *
	 * @param amt
	 * @return
	 */
	public static String convertToMoneyStr(BigDecimal amt) {
		if (amt != null) {
			return NF_YUAN.format(amt);
		} else {
			return null;
		}
	}

	/**
	 * 将对象转换成List<NameValuePair>格式
	 *
	 * @param obj
	 *            转换对象
	 * @return List<NameValuePair>格式
	 */
	public static List<NameValuePair> getListNamValPar(Object obj, boolean isBlank) {
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();

		Field[] fieldArr = obj.getClass().getDeclaredFields();
		try {
			for (Field field : fieldArr) {
				field.setAccessible(true);
				if (isBlank) {
					if (field.get(obj) != null) {
						formParams.add(new BasicNameValuePair(field.getName(), field.get(obj).toString()));
					}
				} else {
					if (field.get(obj) != null && !"".equals(field.get(obj).toString().trim())) {
						formParams.add(new BasicNameValuePair(field.getName(), field.get(obj).toString()));
					}
				}
			}
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		}

		return formParams;
	}

	/**
	 * 将Name=Value&Name=Value格式的字符串赋值给指定对象
	 *
	 * @param obj
	 *            赋值对象
	 * @param str
	 *            Name=Value&格式的字符
	 * @return
	 */
	public static Object getObjFromNamValParStr(Object obj, String str) {
		Field[] fieldArr = obj.getClass().getDeclaredFields();
		String nvStrArr[] = str.split("&");

		Map<String, String> map = new HashMap<String, String>();

		for (String nvStr : nvStrArr) {
			map.put(nvStr.substring(0, nvStr.indexOf("=")), nvStr.substring(nvStr.indexOf("=") + 1));
		}

		try {
			for (Field field : fieldArr) {
				field.setAccessible(true);
				if (map.get(field.getName()) != null) {
					field.set(obj, map.get(field.getName()));
				}
			}
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		}
		return obj;
	}
}
