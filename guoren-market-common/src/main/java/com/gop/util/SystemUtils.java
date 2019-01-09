package com.gop.util;

import java.util.Date;

public class SystemUtils {

	public static Integer getSystemTimeNowSecond() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	public static Long getSystemTimeNowMillis() {
		return System.currentTimeMillis();
	}

	public static Date getSystemDateNow(long timeSecond) {
		return new Date(timeSecond * 1000);
	}
}
