package com.gop.common.impl;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.gop.common.UdCreditService;
import com.gop.exception.ServiceErrorExceptionWithCode;
import com.gop.mode.vo.BodyResult;
import com.gop.mode.vo.UdCreditMessage;
import com.gop.mode.vo.UdCreditResult;
import com.gop.util.MD5Util;

import lombok.extern.slf4j.Slf4j;

@Service("udCreditServiceImpl")
@Slf4j
public class UdCreditServiceImpl implements UdCreditService {
	private static String fformatStr = "/dsp-front/4.1/dsp-front/default/pubkey/%s/product_code/%s/out_order_id/%s/signature/%s";

	public Boolean CheckUdCredit(String url, String pubkey, String secretkey, String serviceCode, String outOrderId,
			Map<String, String> parameter) {
		UdCreditResult udCreditResult;
		try {
			udCreditResult = apiCall(url, pubkey, secretkey, serviceCode, outOrderId, parameter);
		} catch (Exception e) {
			log.info("apiCall 异常 ");
			throw ServiceErrorExceptionWithCode.COMMON_SERVICE_ERROR;
		}

		String retCode = udCreditResult.getHeader().getRet_code();

		if (UdCreditMessage.OK_CODE.equals(retCode)) {
			// 对body内容进行解析
			BodyResult body = udCreditResult.getBody();
			String status = body.getStatus();
			// 认证结果状态码，1-认证一致，2-认证不一致，3-无结果（在公安数据库中查询不到此条数据）
			// 1 2 3结果状态码为udcredit接口规定的固定的结果，就先直接写死了
			if ("1".equals(status)) {
				log.info("认证一致");
				return true;
			} else if ("2".equals(status)) {
				log.info("认证不一致");
				return false;
			} else if ("3".equals(status)) {
				log.info("无结果");
				return false;
			} else {
				log.info("status返回状态1 2 3都不是,status= " + status);
				throw new IllegalStateException("status返回状态1 2 3都不是,status= " + status);
			}

		} else {

			UdCreditMessage udCreditMessage = UdCreditMessage.getByCode(retCode);
			log.info("retCode=" + retCode + ",msg=" + udCreditMessage.getDesc());
			throw new IllegalStateException("retCode=" + retCode);
		}

	}

	private static UdCreditResult apiCall(String url, String pubkey, String secretkey, String serviceCode,
			String outOrderId, Map<String, String> parameter) throws Exception {
		try {
			if (parameter == null || parameter.isEmpty())
				throw new Exception("error ! the parameter Map can't be null.");
			StringBuffer bodySb = new StringBuffer("{");
			for (Map.Entry<String, String> entry : parameter.entrySet()) {
				bodySb.append("'").append(entry.getKey()).append("':'").append(entry.getValue()).append("',");
			}
			String bodyStr = bodySb.substring(0, bodySb.length() - 1) + "}";
			// String signature = md5(bodyStr + "|" + secretkey);
			String signature = MD5Util.genMD5Code(bodyStr + "|" + secretkey);

			url += String.format(fformatStr, pubkey, serviceCode, outOrderId, signature);
			System.out.println("requestUrl=>" + url);
			System.out.println("request parameter body=>" + bodyStr);

			UdCreditResult result = makePostRequest(url, bodyStr);

			return result;
		} catch (Exception e) {
			log.error("apiCall 异常", e);
			throw ServiceErrorExceptionWithCode.COMMON_SERVICE_ERROR;
		}
	}

	static UdCreditResult makePostRequest(String url, String jsonData) {
		try {
			RestTemplate rest = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			headers.setContentType(type);
			headers.add("Accept", MediaType.APPLICATION_JSON.toString());

			HttpEntity<String> formEntity = new HttpEntity<String>(jsonData, headers);

			UdCreditResult result = rest.postForObject(url, formEntity, UdCreditResult.class);

			if (result != null) {

				log.info("header=" + result.getHeader() + ",body=" + result.getBody());
				return result;
			} else {
				log.info("调用udcredit接口返回的结果为null");
				throw ServiceErrorExceptionWithCode.COMMON_SERVICE_ERROR;
			}

		} catch (RestClientException e) {
			log.error("调用udcredit接口发生异常", e);
			throw ServiceErrorExceptionWithCode.COMMON_SERVICE_ERROR;
		}
	}

	// public static void main(String[] args) throws Exception {
	// Map<String, String> body = new HashMap<String, String>();
	// // 身份证需要时再填写，不上传
	// body.put("id_no", "640303198706026175");
	// body.put("id_name", "刘泽");
	// String url = "https://api4.udcredit.com";
	// String pubkey = "b52a29d4-3741-4747-b6df-73e432244319";
	// String secretkey = "7ce48255-34f5-4206-874e-98082a2d3b98";
	// String serviceCode = "O1001S0201";
	// String outOrderId = "63542638524337726";
	// // System.out.println(apiCall(url, pubkey, secretkey, serviceCode,
	// // outOrderId, body));
	// UdCreditServiceImpl u1 = new UdCreditServiceImpl();
	// Boolean flag = u1.CheckUdCredit(url, pubkey, secretkey, serviceCode,
	// outOrderId, body);
	// log.info(flag + "");
	// }
}
