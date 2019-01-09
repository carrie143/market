package com.gop.sms.strategy.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gop.sms.config.SmsConfig;
import com.gop.sms.config.WeiwangSmsConfig;
import com.gop.sms.strategy.ServiceStrategy;
import com.gop.utils.HttpUtil;

public class WeiwangService implements ServiceStrategy {
	private static final Logger log = LoggerFactory.getLogger(WeiwangService.class);
	
	@Override
	public String sendSms(SmsConfig smsConfig, String phone, String message) {
		WeiwangSmsConfig config = null;
		String retStr = "";
		if(smsConfig instanceof WeiwangSmsConfig){
			config = (WeiwangSmsConfig)smsConfig;
			log.info("smsConfig={}", config);
			String returnStr = HttpUtil.post(config.getPostUrl(), prepareParams(config, phone, message), "UTF-8");
			log.info("retStr={}", returnStr);
			Element rootElement = loadXml(returnStr);
			String state = rootElement.element("State").getText();
			
			if(state != null && state.equals("0")){
				retStr = rootElement.element("MsgID").getText();
			}
		} else {
			log.error("smsConfig is not instanceof WeiwangSmsConfig, smsConfig={},phone={},message={}", smsConfig, phone, message );
		}
		
		return retStr;
	}
	
	private Element loadXml(String text){
		Document document = null;
		try {
			document = DocumentHelper.parseText(text);
		} catch (DocumentException e) {
			log.error("xml解析错误");
		}
		return document.getRootElement();  
	}
	
	private static List<NameValuePair> prepareParams(WeiwangSmsConfig config, String phone, String message){
		List<NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair("sname", config.getAccountId()));
		nvps.add(new BasicNameValuePair("spwd", config.getAccountPassword()));
		nvps.add(new BasicNameValuePair("scorpid", config.getCorpId()));
		nvps.add(new BasicNameValuePair("sprdid", config.getPrdId()));
		nvps.add(new BasicNameValuePair("sdst", phone));
		nvps.add(new BasicNameValuePair("smsg", message));
		return nvps;
	}

}
