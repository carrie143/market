package com.gop.util;

import java.math.BigDecimal;

public class CheckStateUtil {

	public static void checkNotNull(Object obj, RuntimeException e) {
		if (null == obj)
			throw e;
	}

	public static void checkNotNull(Object obj, Exception e) throws Exception {
		if (null == obj)
			throw e;
	}

	public static void checkBiggerZero(BigDecimal obj, RuntimeException e) {
		if (obj.compareTo(BigDecimal.ZERO) < 0) {
			throw e;
		}
	}

	public static void checkState(boolean boo, RuntimeException e) {
		if (!boo) {
			throw e;
		}
	}

	public static void checkArgumentState(boolean boo, String message) {
		if(boo) throw new IllegalArgumentException(message);
	}
}
