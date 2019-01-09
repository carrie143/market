/**
 * 
 */
package com.gop.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期时间工具类
 * 
 * @author xiezz
 * @version 1.0.0
 * @date 2015-08-06
 */
public class DateTimeUtil {

	public static String getFormatString(String dateString) {
		String regex1 = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";
		String regex2 = "^((((1[6-9]|[2-9]\\d)\\d{2})/(0?[13578]|1[02])/(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})/(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";
		if (dateString.matches(regex1)) {
			return "yyyy-MM-dd HH:mm:ss";
		} else if (dateString.matches(regex2)) {
			return "yyyy/MM/dd HH:mm:ss";
		} else {
			throw new IllegalStateException("无效日期格式");
		}
	}

	
	public static String getDate() {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		return formatter.format(new Date());
	}

	public static String getDateTime() {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(new Date());
	}

	public static Date getFormatDate(String format, String dateString) {
		// yyyy-MM-dd HH:mm:ss
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = simpleDateFormat.parse(dateString);
		} catch (Exception e) {
			throw new IllegalArgumentException("日期格式不正确");
		}
		return date;
	}

	public static Integer getUnixTimeStamp() {
		Long timestamp = System.currentTimeMillis() / 1000L;
		return Integer.valueOf(timestamp.toString());
	}
}
