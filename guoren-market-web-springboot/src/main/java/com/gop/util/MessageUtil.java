package com.gop.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
/**
 * 发送手机验证码短信的工具类
 * 
 * @author yangdongqiong
 *
 */
public class MessageUtil {
	  public static String sendSMS(String strmobiles,String strContent) throws UnsupportedEncodingException  {
		  HttpClient client = new HttpClient();		
			String url= "http://api.esoftsms.com/msg/HttpBatchSendSM";//应用地址
			String account = "SDK0620";//账号
			String pswd = "79639528Yang";//密码
			String mobiles = strmobiles;//手机号码，多个号码使用","分割
			String content = URLEncoder.encode(strContent,"utf-8");;//短信内容		
			String result="";
			PostMethod post = new PostMethod(url);
			post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8"); 	  
			NameValuePair[] data = {
					new NameValuePair("account", account),
					new NameValuePair("pswd", pswd),
					new NameValuePair("mobile", mobiles),
					new NameValuePair("msg", content)
				};		
			try {
				post.setRequestBody(data);
				client.executeMethod(post);			
				result = new String(post.getResponseBodyAsString());
			} catch (IOException e) {		 
				e.printStackTrace();
			}
			post.releaseConnection();
			return result;
	  }
	  public static String getRandom() {
	        String num = "";
	        for (int i = 0; i < 6; i++) {
	            num = num + String.valueOf((int) Math.floor(Math.random() * 9 + 1));
	        }
	        return num;
	    }
}
