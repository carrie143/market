package com.gop.common.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.gop.common.GetCountyAndCityByIPService;

import lombok.extern.slf4j.Slf4j;

@Service("getCountyAndCityByIPServiceImpl")
public class GetCountyAndCityByIPServiceImpl implements GetCountyAndCityByIPService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public Map<String, String> getCountyAndCityByIp(String ip) {
		ConcurrentHashMap<String,String> map = new ConcurrentHashMap<String,String>();
		map.put("country", "Unknow");
		map.put("city", "Unknow");
		String ipUrl =  "http://ip-api.com/json/" + ip;
		JSONObject ipInformationJson=restTemplate.getForObject(ipUrl, JSONObject.class);
		if (null != ipInformationJson) {
			String status = ipInformationJson.getString("status");
			if (status.equals("success")) {
				map.put("country", ipInformationJson.getString("country"));
				map.put("city", ipInformationJson.getString("city"));
			}
		}
		return map;
	}
}
