package com.gop.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ZkGlobalConstants {
	// 服务在zk下的路径
	public static final String PARENT_NODE_PATH = "/trade_url";
	public static final String PRE_URL = "http://";
	public static final String SEPARATOR = "/";
	public static final String POST_URL = "/tradeServcie";

	public static String transfer2HttpUrl(String ipPort) {
		return ZkGlobalConstants.PRE_URL + ipPort + ZkGlobalConstants.POST_URL;

	}

	// 以交易对名为key，以主备服务器的url列表为value
	public static ConcurrentHashMap<String, List<String>> trademap = new ConcurrentHashMap<String, List<String>>();
}
