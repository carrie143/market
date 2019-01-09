//package com.gop.notify.listener;
//
//import java.io.UnsupportedEncodingException;
//import java.nio.charset.Charset;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.commons.io.IOUtils;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.apache.http.HttpResponse;
//import org.apache.http.concurrent.FutureCallback;
////import org.assertj.core.util.Strings;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.google.common.base.Strings;
//import com.gop.consumer.BaseListener;
//import com.gop.domain.BrokerNotify;
//import com.gop.domain.User;
//import com.gop.domain.UserApiKey;
//import com.gop.notify.dto.ApiResponseDto;
//import com.gop.notify.dto.NotifyDto;
//import com.gop.notify.dto.enums.NotifyType;
//import com.gop.notify.service.BrokerNotifyService;
//import com.gop.notify.service.UserApiKeyService;
//import com.gop.user.facade.UserFacade;
//import com.gop.util.AsyncHttpUtil;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Component("notifyListener")
//public class NotifyConsumer extends BaseListener {
//
//	@Autowired
//	private BrokerNotifyService brokerNotifyService;
//
//	@Autowired
//	private UserApiKeyService userApiKeyService;
//	@Autowired
//	UserFacade userFacade;
//
//	private static Comparator<Entry<String, Object>> keyComparator = new Comparator<Entry<String, Object>>() {
//		@Override
//		public int compare(Entry<String, Object> o1, Entry<String, Object> o2) {
//			return o1.getKey().compareTo(o2.getKey());
//		}
//	};
//
//	@Override
//	public void onMessage(JSONObject json) {
//		NotifyDto notifyDto = json.toJavaObject(json, NotifyDto.class);
//		String code = notifyDto.getCode();
//		NotifyType notify = notifyDto.getNotifyType();
//		UserApiKey userApikey = userApiKeyService.getByUid(notifyDto.getUserId());
//		if (userApikey == null) {
//			return;
//		}
//		User user = userFacade.getUser(notifyDto.getUserId());
//
//		Integer brokerId = user.getBrokerId();
//		BrokerNotify brokerNotify = brokerNotifyService.getBrokerNotify(brokerId);
//		if (brokerNotify == null) {
//			return;
//		}
//		String url = "";
//		switch (notify) {
//		case MATCH_ORDER:
//			url = brokerNotify.getTradeCallback();
//			break;
//		case TRANSFERCOIN_IN:
//			url = brokerNotify.getCoinTransferin();
//			break;
//		case TRANSFERCOIN_OUT:
//			url = brokerNotify.getCoinTransferout();
//			break;
//		case TRANSFERCURRENCY_OUT:
//			url = brokerNotify.getCurrencyTransferout();
//			break;
//		default:
//			break;
//		}
//
//		if (Strings.isNullOrEmpty(url)) {
//			log.info("未找到回掉url：{}", notifyDto);
//			return;
//		}
//
//		String key = userApikey.getPassphrase();
//		String nonceStr = RandomStringUtils.randomNumeric(32);
//		int timestamp = (int) (System.currentTimeMillis() / 1000L);
//		Object data = notifyDto.getData();
//		String sign = "";
//		if (data != null) {
//			JSONObject dataMap = (JSONObject) JSONObject.toJSON(data);
//			sign = generateSign(dataMap, timestamp, nonceStr, key);
//		} else {
//			JSONObject jsonObject = new JSONObject();
//			sign = generateSign(jsonObject, timestamp, nonceStr, key);
//		}
//
//		ApiResponseDto apiResponseDto = new ApiResponseDto();
//		apiResponseDto.setCode(code);
//		apiResponseDto.setData(data);
//		apiResponseDto.setNonceStr(nonceStr);
//		apiResponseDto.setTimestamp(timestamp);
//		apiResponseDto.setSignature(sign);
//		apiResponseDto.setUserNo(userApikey.getUserNo());
//		log.info("开始发送回掉:{}", JSONObject.toJSONString(apiResponseDto));
//		try {
//			AsyncHttpUtil.asyncPost(url,
//					JSONObject.toJSONString(apiResponseDto, SerializerFeature.WriteMapNullValue,
//							SerializerFeature.QuoteFieldNames, SerializerFeature.WriteNullListAsEmpty,
//							SerializerFeature.WriteEnumUsingName),
//					new FutureCallback<HttpResponse>() {
//
//						@Override
//						public void failed(Exception e) {
//							log.info("回调异常" + e);
//						}
//
//						@Override
//						public void completed(HttpResponse response) {
//							String str = null;
//							try {
//								str = IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8"));
//							} catch (Exception e) {
//								log.error("读取出错!");
//							}
//							log.info("回调完成:状态:{},回调结果:{}", response.getStatusLine(), str);
//
//						}
//
//						@Override
//						public void cancelled() {
//							log.info("回调取消" + apiResponseDto);
//
//						}
//
//					});
//		} catch (UnsupportedEncodingException e) {
//			log.error("回调发生异常:{}", e);
//		}
//	}
//
//	public static String generateSign(Map<String, Object> date, long timestamp, String nonceStr, String apiSecret) {
//		Map<String, Object> dataJson = new HashMap<>();
//		Set<String> keys = date.keySet();
//		Iterator<String> iterator = keys.iterator();
//		while (iterator.hasNext()) {
//			String key = iterator.next();
//			Object data = date.get(key);
//
//			if (data != null) {
//				dataJson.put(key, date.get(key));
//
//			}
//
//		}
//		dataJson.put("nonceStr", nonceStr);
//		dataJson.put("timestamp", timestamp);
//		dataJson.put("apiSecret", apiSecret);
//		String result = dataJson.entrySet().stream().sorted(keyComparator)
//				.map(entry -> entry.getKey() + "=" + entry.getValue().toString()).collect(Collectors.joining("&"));
//		log.info("restul,{}", result);
//		String sign = DigestUtils.md5Hex(result).toUpperCase();
//		return sign;
//	}
//
//}
