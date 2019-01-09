package com.gop.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * 日期帮助类
 * 
 * @author yuxiaojian
 *
 */
public class DateUtils {
	public static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 小写的mm表示的是分钟
		return sdf.format(date);
	}

	public static String formatDate(Date date, String pattern) {
		String str = null;
		SimpleDateFormat sdf = null;

		try {
			sdf = new SimpleDateFormat(pattern);// 小写的mm表示的是分钟
			str = sdf.format(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (str == null) {
			str = formatDate(date);
		}

		return str;
	}

	/**
	 * 两个日期相差的天数
	 * 
	 * @param start
	 * @param end
	 * @return Integer
	 */
	public static long subtract(Date start, Date end) {
		Long days = 0l;

		long diff = end.getTime() - start.getTime();// 这样得到的差值是微秒级别

		days = diff / (1000 * 60 * 60 * 24);
		//
		// long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
		//
		// long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 *
		// 60))/(1000* 60);

		return days;

	}

	/**
	 * 根据TimeUnit，计算两个日期相差的时间
	 * 
	 * @param start
	 * @param end
	 * @param timeUnit
	 * @return Integer
	 */
	public static long subtract(Date start, Date end, TimeUnit timeUnit) {
		Long periods = 0l;

		if (start == null || end == null || start.compareTo(end) > 0) {
			throw new IllegalArgumentException("日期不能为空,且开始日期必须小于结束日期");
		}

		if (timeUnit == null) {
			throw new IllegalArgumentException("时间单位不能为空");
		}

		long diff = end.getTime() - start.getTime();// 这样得到的差值是毫秒级别

		switch (timeUnit) {
		case DAYS:
			periods = diff / (1000 * 60 * 60 * 24);
			break;
		case HOURS:
			periods = diff / (1000 * 60 * 60);
			break;
		case MINUTES:
			periods = diff / (1000 * 60);
			break;
		case SECONDS:
			periods = diff / 1000;
			break;
		default:
			break;
		}

		return periods;
	}

	/**
	 * 两个日期相差的小时
	 * 
	 * @param start
	 * @param end
	 * @return Integer
	 */
	public static long subtract(Date start, Date end, CalendarPeroid cp) {
		Long periods = 0l;

		long diff = end.getTime() - start.getTime();// 这样得到的差值是毫秒级别

		switch (cp) {
		case YEAR:
			break;
		case MONTH:
			break;
		case WEEK:
			periods = diff / (1000 * 60 * 60 * 24 * 7);
			break;
		case DAY:
			periods = diff / (1000 * 60 * 60 * 24);
			break;
		case HOUR:
			periods = diff / (1000 * 60 * 60);
			break;
		case MINUTE:
			periods = diff / (1000 * 60);
			break;
		case SECOND:
			periods = diff / 1000;
			break;
		default:
			break;
		}

		return periods;

	}

	/**
	 * 以yyyy-MM-dd HH:mm:ss 解析为日期
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date parseDate(String strDate) throws ParseException {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 小写的mm表示的是分钟
		date = sdf.parse(strDate);
		return date;
	}

	public static Date add(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

	public static Date add(Date oldDate, int calendarUnit, int amount) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(oldDate);
		calendar.add(calendarUnit, amount); // 把日期往后增加一天.整数往后推,负数往前移动
		return calendar.getTime(); // 这个时间就是日期往后推一天的结果
	}

	public enum CalendarPeroid {
		/**
		 * 年
		 */
		YEAR,

		/**
		 * 月
		 */
		MONTH,

		/**
		 * 周
		 */
		WEEK,

		/**
		 * 日
		 */
		DAY,

		/**
		 * 小时
		 */
		HOUR,

		/**
		 * 分钟
		 */
		MINUTE,

		/**
		 * 秒
		 */
		SECOND
	}

}
