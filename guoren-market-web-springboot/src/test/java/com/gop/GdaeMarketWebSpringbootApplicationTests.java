//package com.gop;
//
//import com.alibaba.fastjson.JSONObject;
//import com.gop.domain.enums.TradeCoinFlag;
//import com.gop.domain.enums.TradeCoinType;
//import com.gop.match.api.APiMatchController;
//import com.gop.match.dto.BatchMatchOrderDto;
//import com.gop.match.dto.MatchOrderDto;
//import java.math.BigDecimal;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.stream.Collectors;
//import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.commons.lang.RandomStringUtils;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class GdaeMarketWebSpringbootApplicationTests {
//
//  private static final Comparator<Entry<String, Object>> keyComparator = new Comparator<Entry<String, Object>>() {
//    @Override
//    public int compare(Entry<String, Object> o1, Entry<String, Object> o2) {
//      return o1.getKey().compareTo(o2.getKey());
//    }
//  };
//
//  @Autowired
//  private APiMatchController aPiMatchController;
//
//  @Test
//  public void contextLoads() {
//  }
//
//  @Test
//  @SuppressWarnings("unchecked")
//  public void batchMatchOrder() {
//    String key = "eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9";
//    String businessNo = "100000116050600002";
//    String nonceStr = RandomStringUtils.randomNumeric(32);
//    long timestamp = System.currentTimeMillis() / 1000;
//
//    BatchMatchOrderDto batchMatchOrderDto = new BatchMatchOrderDto();
//    List<MatchOrderDto> matchOrderDtos = batchMatchOrderDto.getMatchOrderDtos();
//    matchOrderDtos.add(matchOrderDto("10000001", "ACT_KCASH", new BigDecimal("1"), new BigDecimal("0.87"), TradeCoinType.SELL));
//    matchOrderDtos.add(matchOrderDto("10000002", "ACT_KCASH", new BigDecimal("1"), new BigDecimal("0.87"), TradeCoinType.SELL));
//
//    Map<String, Object> jsonmap = (Map) JSONObject.toJSON(batchMatchOrderDto);
//    JSONObject jsonObject = new JSONObject();
//    jsonObject.put("businessNo", businessNo);
//    jsonObject.put("nonceStr", nonceStr);
//    jsonObject.put("timestamp", timestamp);
//    jsonObject.put("data", jsonmap);
//    String sign = generateSign(jsonmap, timestamp, nonceStr, key);
//    jsonObject.put("sign", sign);
//
//    aPiMatchController.batchMatchApi(jsonObject.toJSONString());
//
//
//  }
//
//
//  private MatchOrderDto matchOrderDto(String outOrderNo, String symbol, BigDecimal amount, BigDecimal price, TradeCoinType type) {
//    MatchOrderDto dto = new MatchOrderDto();
//    dto.setOutOrderNo(outOrderNo);
//    dto.setSymbol(symbol);
//    dto.setAmount(amount);
//    dto.setPrice(price);
//    dto.setTradeCoinType(type);
//    dto.setTradeCoinFlag(TradeCoinFlag.FIXED);
//    return dto;
//  }
//
//  private String generateSign(Map<String, Object> date, long timestamp, String nonceStr, String apiSecret) {
//    Map<String, Object> dataJson = new HashMap<>();
//    for (String key : date.keySet()) {
//      Object data = date.get(key);
//      if (data != null) {
//        dataJson.put(key, date.get(key));
//      }
//    }
//    dataJson.put("nonceStr", nonceStr);
//    dataJson.put("timestamp", timestamp);
//    dataJson.put("apiSecret", apiSecret);
//    String result = dataJson.entrySet().stream().sorted(keyComparator).map(entry -> entry.getKey() + "=" + entry.getValue().toString()).collect(Collectors.joining("&"));
//    return DigestUtils.md5Hex(result).toUpperCase();
//  }
//
//}
