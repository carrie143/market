package com.gop.sms.strategy.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gop.sms.config.BaiwuSmsConfig;
import com.gop.sms.config.SmsConfig;
import com.gop.sms.strategy.ServiceStrategy;
import com.gop.utils.HttpUtil;
import com.gop.utils.MD5Util;

public class BaiwuService implements ServiceStrategy {
	private static final Logger log = LoggerFactory.getLogger(BaiwuService.class);

	@Override
	public String sendSms(SmsConfig smsConfig, String phone, String message) {
		BaiwuSmsConfig config = null;
		String retStr = "";
		if (smsConfig instanceof BaiwuSmsConfig) {
			config = (BaiwuSmsConfig) smsConfig;
			log.info("smsConfig={}", config);

			retStr = HttpUtil.post(config.getPostUrl(), prepareParams(config, phone, message), "gbk");
			log.info("retStr={}", retStr);
			if (retStr.contains("0#")) {
				retStr = retStr.substring(2);
			} else {
				retStr = "";
			}
		} else {
			log.error("smsConfig is not instanceof BaiwuSmsConfig, smsConfig={},phone={},message={}", smsConfig, phone,
					message);
		}

		return retStr;
	}

	private static List<NameValuePair> prepareParams(BaiwuSmsConfig config, String phone, String message) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("id", config.getAccountId()));
		nvps.add(new BasicNameValuePair("MD5_td_code", MD5Util.genMD5Code(config.getAccountPassword())));
		nvps.add(new BasicNameValuePair("msg_id", config.getMsgId()));
		nvps.add(new BasicNameValuePair("ext", config.getExt()));
		nvps.add(new BasicNameValuePair("mobile", phone));
		nvps.add(new BasicNameValuePair("msg_content", message));
		return nvps;
	}

}
