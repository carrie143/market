package com.gop.sms.strategy.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gop.sms.config.LingkaiSmsConfig;
import com.gop.sms.config.SmsConfig;
import com.gop.sms.strategy.ServiceStrategy;
import com.gop.utils.HttpUtil;

public class LingkaiService implements ServiceStrategy {
	private static final Logger log = LoggerFactory.getLogger(LingkaiService.class);

	@Override
	public String sendSms(SmsConfig smsConfig, String phone, String message) {
		LingkaiSmsConfig config = null;
		String retStr = "";
		if(smsConfig instanceof LingkaiSmsConfig){
			config = (LingkaiSmsConfig)smsConfig;
			log.info("smsConfig={}", config);
			String returnStr = HttpUtil.post(config.getPostUrl(), prepareParams(config, phone, message), "gb2312");
			log.info("retStr={}", returnStr);
			int num = NumberUtils.toInt(returnStr, 0);
			if(num > 0){
				retStr = returnStr;
			}
		} else {
			log.error("smsConfig is not instanceof LingkaiSmsConfig, smsConfig={},phone={},message={}", smsConfig, phone, message );
		}
		
		return retStr;
	}
	
	private static List<NameValuePair> prepareParams(LingkaiSmsConfig config, String phone, String message){
		List<NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair("CorpID", config.getAccountId()));
		nvps.add(new BasicNameValuePair("Pwd", config.getAccountPassword()));
		nvps.add(new BasicNameValuePair("Cell", config.getCell()));
		nvps.add(new BasicNameValuePair("SendTime", config.getSendTime()));
		nvps.add(new BasicNameValuePair("Mobile", phone));
		nvps.add(new BasicNameValuePair("Content", message));
		return nvps;
	}

}
