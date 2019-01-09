package com.gop.api.cloud.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.gop.api.cloud.ApiRequest;
import com.gop.api.cloud.ApiResponse;
import com.gop.api.cloud.common.ApiErrorCode;
import com.gop.api.cloud.config.ApiConfig;
import com.gop.code.consts.OrderCodeConst;
import com.gop.code.consts.UserAssetCodeConst;
import com.gop.code.consts.WithdrawalsCodeConst;
import com.gop.exception.AppException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

/**
 * call api provided from other service.
 * T indicates the class of data field in ApiResponse,
 * V indicates the class of apiRequest for POST call.
 * Created by zhangjinyang on 2018/6/27.
 */
@Slf4j
public class ApiCallProcessor<T> {

  private CloseableHttpClient provider;
  private String callUrl;
  private String accessKey;
  private String secretkey;
  private TypeReference<T> typeReference;

  private static final Integer CONNECT_TIMEOUT = 3000;
  private static final Integer SOCKET_TIMEOUT = 10000;

  public ApiCallProcessor(ApiConfig apiConfig, TypeReference typeReference){

    RequestConfig defaultRequestConfig = RequestConfig.custom()
        .setSocketTimeout(SOCKET_TIMEOUT)
        .setConnectTimeout(CONNECT_TIMEOUT)
        .setConnectionRequestTimeout(CONNECT_TIMEOUT)
        .build();

    this.provider = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
    this.callUrl = apiConfig.getUrl();
    this.accessKey = apiConfig.getAccesskey();
    this.secretkey = apiConfig.getSecretkey();
    this.typeReference = typeReference;
  }

  public ApiCallProcessor(TypeReference typeReference){
    this.typeReference = typeReference;
  }

  public ApiResponse<T> call(ApiRequest apiRequest, String method, Map<String, Object> params)
      throws AppException, HttpLayerException {

    Header[] headers = buildHeaders(apiRequest.getData());
    if (HttpConstants.REQ_METHOD_GET.equals(method)){
      callUrl = appendParams(callUrl, params);
    }
    SimpleHttpClient simpleHttpClient = new SimpleHttpClient(provider, callUrl);
    String responseJson = simpleHttpClient
        .execute(method, apiRequest.getData() != null ? parseRequestBody(apiRequest.getData()) : null, headers);
    log.info("ResponseBody：" + JSONObject.toJSONString(responseJson));
    ApiResponse<T> tApiResponse = parseResult(responseJson);
    return tApiResponse;
  }

  private String parseRequestBody(Object data) {
    log.info("RequestBody：" + JSONObject.toJSONString(data));
    return JSONObject.toJSONString(data);
  }

  private String appendParams(String url, Map<String, Object> params) {

    StringBuilder builder = new StringBuilder(url);
    builder.append("?");
    Set<Entry<String, Object>> entries = params.entrySet();
    List<Entry<String, Object>> entryList = new ArrayList<>(entries);
    for (int i=0; i<entryList.size(); i++){

      Entry<String, Object> entry = entryList.get(i);
      if (i == (entryList.size()-1)){
        builder.append(entry.getKey() + "=" + entry.getValue().toString());
      }
      builder.append(entry.getKey() + "=" + entry.getValue().toString() + "&");

    }
    return builder.toString();

  }

  private Header[] buildHeaders(Object data){
    String signData = generateSign(parseRequestBody(data));
    log.info("加密Sign：" + signData);
    log.info("accessKey：" + accessKey);
    return new Header[]{new BasicHeader("sign", signData), new BasicHeader("accessKey",accessKey)};
  }

  private ApiResponse<T> parseResult(String resultJson) throws AppException, HttpLayerException {
    ApiResponse response = (ApiResponse)JSON.parseObject(resultJson, typeReference);
    if (!response.isRet()){
      if (ApiErrorCode.WITHDRAW_FEE_TOO_LOW.getErrorMsg().equals(response.getMsg())) {
        log.error("API调用错误：{},{}",response.getCode(),response.getMsg());
        throw new AppException(WithdrawalsCodeConst.BROKER_LESS_CLOUD_MIN_FEE,"提现手续费小于平台手续费");
      }
      if (ApiErrorCode.NOT_LISTED_SYMBOL.getErrorMsg().equals(response.getMsg())) {
        log.error("API调用错误：{},{}",response.getCode(),response.getMsg());
        throw new AppException(UserAssetCodeConst.INVALID_SYMBOL, "此交易对未开启");
      }
      if (ApiErrorCode.NOT_LISTED_ASSET.getErrorMsg().equals(response.getMsg())) {
        log.error("API调用错误：{},{}",response.getCode(),response.getMsg());
        throw new AppException(UserAssetCodeConst.INVALID_ASSET_CODE, "此币种对未开启");
      }
      if (ApiErrorCode.OVER_MAX_PRICE.getErrorMsg().equals(response.getMsg())) {
        log.error("API调用错误：{},{}",response.getCode(),response.getMsg());
        throw new AppException(OrderCodeConst.MAX_ORDER_PRICE_ERROR, "挂单价格超出最高限制");
      }
      if (ApiErrorCode.ADDRESS_ASSIGN_ERROR.getErrorMsg().equals(response.getMsg())) {
        log.error("API调用错误：{},{}",response.getCode(),response.getMsg());
        throw new AppException(UserAssetCodeConst.CREATE_DEPOSIT_ADDRESS_ERROR, "生成地址出错");
      }
      throw new HttpLayerException(Errors.RESPONSE_ERROR, response.getMsg());
    }
    return response;
  }

  private String generateSign(String data) {
    String result = data + secretkey;
    log.debug("认证拦截：加密明文：" + result);
    return DigestUtils.sha256Hex(result).toUpperCase();
  }

  public ApiResponse<T> parseDemo(String s) {
    ApiResponse response = (ApiResponse)JSON.parseObject(s, typeReference);
    return response;
  }
}
