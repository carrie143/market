package com.gop.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;
public class TdCommonUtil {
	private static String clsPath = "WEB-INF/classes/";
	private static String home = null;

	/**
	 * 获取项目根目录
	 */
	public static String getWebHome() {
		if (StringUtils.isEmpty(home)) {
			try {
				String path = TdCommonUtil.class.getClassLoader().getResource("").getPath();
				if (path.contains(clsPath)) {
					path = path.replace(clsPath, "");
				}
				home = URLDecoder.decode(path, Charset.defaultCharset().name());
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("获取应用根目录失败 ,error=> " + e);
			}
		}
		return home;
	}

}
