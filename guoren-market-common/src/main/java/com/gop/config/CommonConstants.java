package com.gop.config;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CommonConstants {
	// 常量
	public static final BigDecimal BIG_ZERO = new BigDecimal("0.00");
	public static final Long LONG_ZERO = 0L;

	// 分页常量
	public static final Integer PAGENO_MIN = 1;
	public static final Integer PAGENO_MAX = 50;
	public static final Integer PAGESIZE_MIN = 1;
	public static final Integer PAGESIZE_MAX = 30;

	public static final Integer UNIX_FIX_TIME = 28800;

	// 客服热线号码
	public static final String CUSTOMER_HOTLINE = "010-50917080";

	public static int getPageSize(int pageSize) {
		if (pageSize < PAGESIZE_MIN) {
			pageSize = PAGESIZE_MIN;
		}
		return pageSize > PAGENO_MAX ? PAGENO_MAX : pageSize;
	}

	public static int getPageNo(int pageNo) {
		if (pageNo < PAGENO_MIN) {
			pageNo = PAGENO_MIN;
		}
		return pageNo > PAGENO_MAX ? PAGENO_MAX : pageNo;
	}

}
