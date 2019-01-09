package com.gop.util;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;

public class StringUtil {

	/**
	 * 统计字符串中子字符串个数
	 * 
	 * @param text
	 * @param sub
	 * @return
	 */
	public static int dotCount(String text, String sub) {
		int count = 0;
		int start = 0;
		while (text.indexOf(sub, start) >= 0 && start < text.length()) {
			count++;
			start = text.indexOf(sub, start) + sub.length();
		}
		return count;
	}

	public static String getString(Set<Map.Entry<String, Object>> set) {
		Comparator<Entry<String, Object>> keyComparator = new Comparator<Entry<String, Object>>() {
			@Override
			public int compare(Entry<String, Object> o1, Entry<String, Object> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		};
		String result = set.stream().sorted(keyComparator).map(entry -> entry.getKey() + "=" + entry.getValue())
				.collect(Collectors.joining("&")).trim();
		return result;
	}

	public static boolean matchRegular(String a) {
		return a.matches("^[\u4e00-\u9fa5_a-zA-Z0-9]+$");
	}
	
	
}
