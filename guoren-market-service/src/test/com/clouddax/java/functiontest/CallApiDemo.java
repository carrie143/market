package com.clouddax.java.functiontest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.gop.api.cloud.ApiRequest;
import com.gop.api.cloud.ApiResponse;
import com.gop.api.cloud.client.ApiCallProcessor;
import com.gop.api.cloud.client.HttpLayerException;
import com.gop.api.cloud.response.BrokerConfigAssetDto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

/**
 * Created by zhangjinyang on 2018/6/26.
 */
public class CallApiDemo {

  @Test
  public void testApiCall(){
    new ApiRequest(new User("A", "B"));
  }

  @Test
  public void objToJson(){
  }

  @Test
  public void parseJson(){

//    String s = "{\"code\":\"\",\"data\":[{\"id\":\"1\",\"name\":\"name\"},{\"id\":\"1\",\"name\":\"zhang\"}],\"msg\":\"\",\"ret\":true}\n";
//    ApiCallProcessor apiCallProcessor = new ApiCallProcessor(new TypeReference<ApiResponse<List<User>>>() {
//    });

//    Object o = apiCallProcessor.parseDemo(s);
//    ApiResponse<List<User>> apiResponse = JSON.parseObject(s, );
//    System.out.println(o);
    String s = "{\"ret\":true,\"code\":\"SUCCESS\",\"msg\":\"\",\"data\":[{\"brokerId\":\"1011859673132142593\",\"assetCode\":\"BTC\",\"name\":\"string\",\"withdrawFee\":35.00000000000000000000,\"status\":\"LISTED\",\"createDate\":\"2018-06-26T22:42:11.000+0000\"}]}";

    ApiCallProcessor<List<BrokerConfigAssetDto>> apiCallProcessor = new ApiCallProcessor(new TypeReference<ApiResponse<List<BrokerConfigAssetDto>>>() {});
    ApiResponse<List<BrokerConfigAssetDto>> listApiResponse = apiCallProcessor.parseDemo(s);
    System.out.println(listApiResponse.getData());
  }



}
