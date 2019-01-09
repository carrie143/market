package com.gop.currency.withdraw.gateway.service.cibpay.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gop.currency.withdraw.gateway.service.cibpay.comm.IRequestService;
import com.gop.currency.withdraw.gateway.service.cibpay.config.CIBConfig;

//import com.gop.cib.comm.IRequestService;
//import com.gop.cib.config.Configure;
//import com.gop.config.CIBConfig;
//import com.gop.util.Signature;

public class CIBUtils {
	private static Logger log = LoggerFactory.getLogger(CIBUtils.class);
	private static char hexChar[] = "0123456789ABCDEF".toCharArray();
	
	/**
	 * 生成签名MAC
	 * @param params
	 * @return
	 */
	public static String generateMAC(Map<String, String> params){
		//生成用于MAC计算的参数字符串
		String paramStr = generateParamStr(params);
		//拼接商户令牌
		paramStr = paramStr +"&"+ CIBConfig.CIB_MERCHANT_TOKEN;
		
		return encryptBySHA(paramStr);
	}

	/**
	 * 生成用于MAC计算的参数字符串。<br>
	 * 
	 * @return 模式为key=value&key=value
	 */
	private static String generateParamStr(Map<String, String> params) {
		// 取所有非空字段内容（除mac以外），塞入列表
		List<String> paramList = new ArrayList<String>();
		for (String key : params.keySet()) {
			if ("mac".equals(key)) {
				continue;
			}
			String val = params.get(key);
			paramList.add(key + "=" + val);
		}
		// 防护
		if (paramList.size() == 0) {
			return null;
		}
		// 对列表进行排序
		Collections.sort(paramList);
		// 以&符分割拼装成字符串
		StringBuilder sb = new StringBuilder();
		sb.append(paramList.get(0));
		for (int i = 1; i < paramList.size(); i++) {
			sb.append("&").append(paramList.get(i));
		}
		return sb.toString();
	}

	/**
	 * 将byte数组转换为16进制格式的字符串
	 * 
	 * @param bytes待转换数组
	 * @return 16进制格式的字符串
	 */
	private static String bytesToHexStr(byte[] bytes) {
		StringBuffer sb = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexChar[(bytes[i] & 0xf0) >>> 4]);
			sb.append(hexChar[bytes[i] & 0x0f]);
		}
		return sb.toString();
	}

	/**
	 * SHA摘要算法，输入内容将被UTF-8编码
	 * 
	 * @param content输入明文
	 * @return 内容摘要，40位16进制字符串
	 */
	private static String encryptBySHA(String content) {
		if (content == null){
			return null;
		}
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] output = md.digest(content.getBytes("UTF-8"));
			return bytesToHexStr(output);
		} catch (NoSuchAlgorithmException e) {
			log.error("无法加载SHA算法。", e);
		} catch (UnsupportedEncodingException e) {
			log.error("无法将输入字符串转换到utf-8编码。", e);
		}
		return null;
	}
	
	public static String txn(String url, Map<String, String> params) {

        String json = null;
        try {
            IRequestService requestor = IRequestService.getInstance();
            json = (String) requestor.sendPost(url, params);
            if (json.indexOf("errcode") == -1 && CIBConfig.NEED_CHECK_SIGN && !Signature.verifyMAC(Signature.jsonToMap(json))) {
                return CIBConfig.SIGN_ERROR_RESULT;
            }
        } catch (IOException e) {
            json = CIBConfig.TXN_ERROR_RESULT;
        } catch (Exception e) {
            json = CIBConfig.SYS_ERROR_RESULT;
        }
        return json;
    }
}
