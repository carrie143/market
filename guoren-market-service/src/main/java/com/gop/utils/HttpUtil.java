package com.gop.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类说明
 * @Author yangliguang
 * 2016年6月29日下午5:23:03
 */
public class HttpUtil{
	private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
	
	public static String get(String url){
		CloseableHttpResponse response = null;
		String returnStr = null;
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		try {
			response = httpclient.execute(httpGet);
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                if(entity != null){
                	returnStr = EntityUtils.toString(entity, "UTF-8") ;	
                }
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
		} catch (ClientProtocolException e) {
			log.error("客户端协议异常", e);
		} catch (IOException e) {
			log.error("IO异常", e);
		} finally {
		    try {
				response.close();
			} catch (IOException e) {
			}
		}
		
		return returnStr;
	}
	
	
	public static String post(String url, List<NameValuePair> params){
		return post(url, params, HTTP.DEF_CONTENT_CHARSET.toString());
	}
	public static String post(String url, List<NameValuePair> params, String encode){
		CloseableHttpResponse response = null;
		String returnStr = null;
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, encode));
			
			response = httpclient.execute(httpPost);
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                if(entity != null){
                	returnStr = EntityUtils.toString(entity, "UTF-8") ;	
                }
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
		} catch (UnsupportedEncodingException e) {
			log.error("不支持的编码", e);
		} catch (ClientProtocolException e) {
			log.error("客户端协议异常", e);
		} catch (IOException e) {
			log.error("IO异常", e);
		} finally {
		    try {
				response.close();
			} catch (IOException e) {
			}
		}
		return returnStr;
	}
}

