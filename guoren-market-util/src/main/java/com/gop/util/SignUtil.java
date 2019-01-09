package com.gop.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;

import com.alibaba.fastjson.JSONObject;

public class SignUtil {
	private static final Comparator<Entry<String, Object>> keyComparator = new Comparator<Entry<String, Object>>() {
		@Override
		public int compare(Entry<String, Object> o1, Entry<String, Object> o2) {
			return o1.getKey().compareTo(o2.getKey());
		}
	};

	public static String generateSign(Map<String, Object> date, long timestamp, String nonceStr, String apiSecret) {
		Map<String, Object> dataJson = new HashMap<>();
		Set<String> keys = date.keySet();
		Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Object data = date.get(key);

			if (data != null) {
					dataJson.put(key, date.get(key));
			
			}

		}
		dataJson.put("nonceStr", nonceStr);
		dataJson.put("timestamp", timestamp);
		dataJson.put("apiSecret", apiSecret);
		String result = dataJson.entrySet().stream().sorted(keyComparator)
				.map(entry -> entry.getKey() + "=" + entry.getValue().toString()).collect(Collectors.joining("&"));
		
		String sign = DigestUtils.md5Hex(result).toUpperCase();
		return sign;
	}

	public static String defaultGenerateNonceStr() {
		return RandomStringUtils.randomNumeric(32);
	}

	public static boolean checkSign(String response, String apiSecret) {
		JSONObject responseJson = JSONObject.parseObject(response);
		JSONObject dataJson = responseJson.getJSONObject("data");
		long timestamp = responseJson.getLongValue("timestamp");
		String nonceStr = responseJson.getString("nonceStr");
		String responseSign = responseJson.getString("sign");
		String sign = generateSign(dataJson, timestamp, nonceStr, apiSecret);
		return responseSign.equals(sign) ? true : false;
	}
}
