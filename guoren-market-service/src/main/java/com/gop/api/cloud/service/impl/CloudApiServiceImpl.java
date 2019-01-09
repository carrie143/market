package com.gop.api.cloud.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;

import com.gop.api.cloud.ApiConstants;
import com.gop.api.cloud.ApiRequest;
import com.gop.api.cloud.ApiResponse;
import com.gop.api.cloud.client.ApiCallProcessor;
import com.gop.api.cloud.client.HttpConstants;
import com.gop.api.cloud.client.HttpLayerException;
import com.gop.api.cloud.config.ApiConfig;
import com.gop.api.cloud.request.AssetRequest;
import com.gop.api.cloud.request.BrokerAssetAccountRequest;
import com.gop.api.cloud.request.BrokerAssetRequest;
import com.gop.api.cloud.request.BrokerWithdrawRequest;
import com.gop.api.cloud.request.CancelOrderReq;
import com.gop.api.cloud.request.CreateUserReq;
import com.gop.api.cloud.request.DepositQueryRequest;
import com.gop.api.cloud.request.KlineQueryReq;
import com.gop.api.cloud.request.MatchOrderPageQueryReq;
import com.gop.api.cloud.request.MatchOrderReq;
import com.gop.api.cloud.request.MatchRecordPageQueryReq;
import com.gop.api.cloud.request.ModifyWhitelistReq;
import com.gop.api.cloud.request.SettleQueryRequest;
import com.gop.api.cloud.request.SymbolFeeAddReq;
import com.gop.api.cloud.request.SymbolQueryReq;
import com.gop.api.cloud.request.SymbolScaleReq;
import com.gop.api.cloud.request.TradeOrderQueryReq;
import com.gop.api.cloud.request.UnVerifiedCountRequest;
import com.gop.api.cloud.request.WithdrawCoinRequest;
import com.gop.api.cloud.request.WithdrawConfirmRequest;
import com.gop.api.cloud.request.WithdrawQueryRequest;
import com.gop.api.cloud.request.WithdrawTotalAmountRequest;
import com.gop.api.cloud.response.AssetDto;
import com.gop.api.cloud.response.BrokerAssetDto;
import com.gop.api.cloud.response.BrokerConfigAssetDto;
import com.gop.api.cloud.response.BrokerPageModel;
import com.gop.api.cloud.response.BrokerSymbolFeeData;
import com.gop.api.cloud.response.DepositDetailDto;
import com.gop.api.cloud.response.KlineQueryPages;
import com.gop.api.cloud.response.MatchOrderDetail;
import com.gop.api.cloud.response.MatchRecordDto;
import com.gop.api.cloud.response.SettleRecordDto;
import com.gop.api.cloud.response.SymbolData;
import com.gop.api.cloud.response.SymbolScale;
import com.gop.api.cloud.response.TradeOrderDto;
import com.gop.api.cloud.response.TransferInAddressDto;
import com.gop.api.cloud.response.UserData;
import com.gop.api.cloud.response.WithdrawCoinDetailDto;
import com.gop.api.cloud.service.CloudApiService;
import com.gop.code.consts.ApiCloudErrorCodeConst;
import com.gop.coin.transfer.service.WithdrawCoinQueryService;
import com.gop.exception.AppException;
import com.gop.match.dto.SymbolDto;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by YAO on 2018/6/26.
 */
@Service
@Slf4j
@EnableConfigurationProperties(ApiConfig.class)
public class CloudApiServiceImpl implements CloudApiService {

  @Value("${cloud.api.url}")
  private String baseUrl;

  @Autowired
  private ApiConfig apiConfig;

  @Autowired
  WithdrawCoinQueryService withdrawCoinQueryService;

  @Override
  public List<AssetDto> getUserAccountAssets(BrokerAssetAccountRequest request) {
    String url = baseUrl + ApiConstants.ASSET_ACCOUNTS;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<List<AssetDto>> apiCallProcessor = new ApiCallProcessor(apiConfigLocal,
        new TypeReference<ApiResponse<List<AssetDto>>>() {
        });
    try {
      return apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null)
          .getData();
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new AppException("api接口调用错误");
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }

  @Override
  public List<BrokerConfigAssetDto> getConfigAssetList(AssetRequest request) {
    String url = baseUrl + ApiConstants.BROKER_CONFIG_ASSET_LIST;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<List<BrokerConfigAssetDto>> apiCallProcessor = new ApiCallProcessor<>(
        apiConfigLocal, new TypeReference<ApiResponse<List<BrokerConfigAssetDto>>>() {
    });
    try {
      return apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null)
          .getData();
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new AppException("api接口调用错误", e.getMessage());
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }

  @Override
  public UserData activateNewUser(CreateUserReq req) {
    String url = baseUrl + ApiConstants.ACTIVATE_USER;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<UserData> apiCallProcessor = new ApiCallProcessor<>(apiConfigLocal,
        new TypeReference<ApiResponse<UserData>>() {
        });
    try {
      return apiCallProcessor.call(new ApiRequest(req), HttpConstants.REQ_METHOD_POST, null)
          .getData();
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new AppException("api接口调用错误", e.getMessage());
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }


  @Override
  public PageInfo<WithdrawCoinDetailDto> withdrawCoinDetail(
      WithdrawQueryRequest request) {
    String url = baseUrl + ApiConstants.WITHDRAW_COIN_DETAIL;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<PageInfo<WithdrawCoinDetailDto>> apiCallProcessor = new ApiCallProcessor<>(
        apiConfigLocal, new TypeReference<ApiResponse<PageInfo<WithdrawCoinDetailDto>>>() {
    });
    try {
      return apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null)
          .getData();
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new AppException("api接口调用错误", e.getMessage());
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }

  @Override
  public BigDecimal queryBalance(AssetRequest request) {
    String url = baseUrl + ApiConstants.QUERY_BALANCE;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<BigDecimal> apiCallProcessor = new ApiCallProcessor<>(apiConfigLocal,
        new TypeReference<ApiResponse<BigDecimal>>() {
        });
    try {
      return apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null)
          .getData();
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new AppException("api接口调用错误", e.getMessage());
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }

  @Override
  public void withdrawConfirm(WithdrawConfirmRequest request) {
    String url = baseUrl + ApiConstants.WITHDRAW_CONFIRM;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<Void> apiCallProcessor = new ApiCallProcessor<>(apiConfigLocal,
        new TypeReference<ApiResponse<Void>>() {
        });
    try {
      apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null).getData();
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new AppException("api接口调用错误", e.getMessage());
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }

  @Override
  public void userWithdrawCoin(WithdrawCoinRequest request) throws Exception{
    String url = baseUrl + ApiConstants.WITHDRAW_APPLY;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<Void> apiCallProcessor = new ApiCallProcessor<>(apiConfigLocal,
        new TypeReference<ApiResponse<Void>>() {
        });
    try {
      apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null).getData();
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new Exception("api接口调用错误", e);
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }

  @Override
  public PageInfo<DepositDetailDto> depositeCoinDetail(DepositQueryRequest request) {
    String url = baseUrl + ApiConstants.DEPOSITE_COIN_DETAILS;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<PageInfo<DepositDetailDto>> apiCallProcessor = new ApiCallProcessor<>(
        apiConfigLocal, new TypeReference<ApiResponse<PageInfo<DepositDetailDto>>>() {
    });
    try {
      return apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null)
          .getData();
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new AppException("api接口调用错误", e.getMessage());
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }

  @Override
  public List<TransferInAddressDto> transferInAddressQuery(AssetRequest request) {
    String url = baseUrl + ApiConstants.TRANSFER_IN_ADDRESS_QUERY;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<List<TransferInAddressDto>> apiCallProcessor = new ApiCallProcessor<>(
        apiConfigLocal, new TypeReference<ApiResponse<List<TransferInAddressDto>>>() {
    });
    try {
      return apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null)
          .getData();
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new AppException("api接口调用错误", e.getMessage());
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }

  @Override
  public void matchOrder(MatchOrderReq request) {
    String url = baseUrl + ApiConstants.MATCH_ORDER;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<Void> apiCallProcessor = new ApiCallProcessor<>(apiConfigLocal,
        new TypeReference<ApiResponse<Void>>() {
        });
    try {
      apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null).getData();
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new AppException("api接口调用错误", e.getMessage());
    } catch (AppException a){
    log.error("api业务错误", a);
    throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }

  @Override
  public void matchOrderCancel(CancelOrderReq request) {
    String url = baseUrl + ApiConstants.MATCH_ORDER_CANCEL;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<Void> apiCallProcessor = new ApiCallProcessor<>(apiConfigLocal,
        new TypeReference<ApiResponse<Void>>() {
        });
    try {
      apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null).getData();
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new AppException("api接口调用错误", e.getMessage());
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }

  @Override
  public PageInfo<MatchOrderDetail> matchOrderHistory(
      MatchOrderPageQueryReq request) {
    String url = baseUrl + ApiConstants.MATCH_ORDER_HISTORY;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<PageInfo<MatchOrderDetail>> apiCallProcessor = new ApiCallProcessor<>(
        apiConfigLocal, new TypeReference<ApiResponse<PageInfo<MatchOrderDetail>>>() {
    });
    try {
      return apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null)
          .getData();
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new AppException("api接口调用错误", e.getMessage());
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }

  @Override
  public PageInfo<MatchOrderDetail> matchOrderCurrentDetail(
      MatchOrderPageQueryReq request) {
    String url = baseUrl + ApiConstants.MATCH_ORDER_DETAIL;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<PageInfo<MatchOrderDetail>> apiCallProcessor = new ApiCallProcessor<>(
        apiConfigLocal, new TypeReference<ApiResponse<PageInfo<MatchOrderDetail>>>() {
    });
    try {
      return apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null)
          .getData();
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new AppException("api接口调用错误", e.getMessage());
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }

  @Override
  public PageInfo<MatchRecordDto> matchRecord(MatchRecordPageQueryReq request) {
    String url = baseUrl + ApiConstants.MATCH_ORDER_RECORD;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<PageInfo<MatchRecordDto>> apiCallProcessor = new ApiCallProcessor<>(
        apiConfigLocal,
        new TypeReference<ApiResponse<PageInfo<MatchRecordDto>>>() {
        });
    try {
      return apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null)
          .getData();
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new AppException("api接口调用错误", e.getMessage());
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }

  @Override
  public PageInfo<TradeOrderDto> queryUserOrderRecord(TradeOrderQueryReq request) {
    String url = baseUrl + ApiConstants.USER_ORDER_RECORD;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<PageInfo<TradeOrderDto>> apiCallProcessor = new ApiCallProcessor<>(
        apiConfigLocal,
        new TypeReference<ApiResponse<PageInfo<TradeOrderDto>>>() {
        });
    try {
      return apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null)
          .getData();
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new AppException("api接口调用错误", e.getMessage());
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }

  @Override
  public BrokerSymbolFeeData updateSymbolFee(SymbolFeeAddReq request) {
    String url = baseUrl + ApiConstants.UPDATE_SYMBOL_FEE;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<BrokerSymbolFeeData> apiCallProcessor = new ApiCallProcessor<>(apiConfigLocal,
        new TypeReference<ApiResponse<BrokerSymbolFeeData>>() {
        });
    try {
      return apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null)
          .getData();
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new AppException("api接口调用错误", e.getMessage());
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }

  @Override
  public List<SymbolData> getSymbols(SymbolQueryReq request) {
    String url = baseUrl + ApiConstants.QUERY_SYMBOLS;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<List<SymbolData>> apiCallProcessor = new ApiCallProcessor<>(apiConfigLocal,
        new TypeReference<ApiResponse<List<SymbolData>>>() {
        });
    try {
      return apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null)
          .getData();
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new AppException("api接口调用错误", e.getMessage());
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage(),"");
    }
  }

  @Override
  public Map<String, Integer> countWithdrawByStatus(UnVerifiedCountRequest request) {
    String url = baseUrl + ApiConstants.MATCH_ORDER_RECORD;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<Map<String, Integer>> apiCallProcessor = new ApiCallProcessor<>(apiConfigLocal,
        new TypeReference<ApiResponse<Map<String, Integer>>>() {
        });
    try {
      return apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null)
          .getData();
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new AppException("api接口调用错误", e.getMessage());
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }

  @Override
  public Map<String, BigDecimal> queryWithdrawTotal(WithdrawTotalAmountRequest request) {
    String url = baseUrl + ApiConstants.QUERY_WITHDRAW_TOTAL;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<Map<String, BigDecimal>> apiCallProcessor = new ApiCallProcessor<>(
        apiConfigLocal,
        new TypeReference<ApiResponse<Map<String, BigDecimal>>>() {
        });
    try {
      return apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null)
          .getData();
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new AppException("api接口调用错误", e.getMessage());
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }


  @Override
  public PageInfo<SettleRecordDto> getSettleRecord(SettleQueryRequest request) {
    String url = baseUrl + ApiConstants.SETTLE_RECORD;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<PageInfo<SettleRecordDto>> apiCallProcessor = new ApiCallProcessor<>(
        apiConfigLocal, new TypeReference<ApiResponse<PageInfo<SettleRecordDto>>>() {
    });
    try {
      return apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null)
          .getData();
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new AppException("api接口调用错误", e.getMessage());
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }

  @Override
  public void brokerWithdraw(BrokerWithdrawRequest request) {
    String url = baseUrl + ApiConstants.WITHDRAW_BROKER;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<Void> apiCallProcessor = new ApiCallProcessor<>(apiConfigLocal,
        new TypeReference<ApiResponse<Void>>() {
        });
    try {
      apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null);
    } catch (HttpLayerException e) {
      log.error("api网络错误", e);
      throw new AppException("api接口调用错误", e.getMessage());
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }
  @Override
  public BrokerPageModel<BrokerAssetDto> queryBrokerFinance(BrokerAssetRequest request) {
    String url = baseUrl + ApiConstants.QUERY_BROKER_ASSET;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<BrokerPageModel<BrokerAssetDto>> apiCallProcessor = new ApiCallProcessor<>(
        apiConfigLocal, new TypeReference<ApiResponse<BrokerPageModel<BrokerAssetDto>>>() {
    });
    try {
      return apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null)
                             .getData();
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new AppException("api接口调用错误", e.getMessage());
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }

  @Override
  public KlineQueryPages klineQueryPages(KlineQueryReq request) {
    String url = baseUrl + ApiConstants.KLINE;
    log.info("请求url：" + url);
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<KlineQueryPages> apiCallProcessor = new ApiCallProcessor<>(
        apiConfigLocal, new TypeReference<ApiResponse<KlineQueryPages>>() {
    });
    try {
      return apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null)
          .getData();
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new AppException("api接口调用错误", e.getMessage());
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }

  @Override
  public void updateWhiteList(ModifyWhitelistReq request) {
    String url = baseUrl + ApiConstants.WHITELIST;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<Void> apiCallProcessor = new ApiCallProcessor<>(
        apiConfigLocal, new TypeReference<ApiResponse<Void>>() {
    });
    try {
      apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null);
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new AppException("api接口调用错误", e.getMessage());
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }

  @Override
  public void callback(String data) {
  }

  @Override
  public List<SymbolScale> getSymbolScale(SymbolScaleReq request) {
    String url = baseUrl + ApiConstants.GET_SYMBOL_SCALE;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey(apiConfig.getAccesskey());
    apiConfigLocal.setSecretkey(apiConfig.getSecretkey());
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<List<SymbolScale>> apiCallProcessor = new ApiCallProcessor<>(
        apiConfigLocal, new TypeReference<ApiResponse<List<SymbolScale>>>() {
    });
    try {
      return apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null)
                             .getData();
    } catch (HttpLayerException e) {
      log.error("api调用错误", e);
      throw new AppException("api接口调用错误", e.getMessage());
    }catch (AppException a){
      log.error("api业务错误", a);
      throw new AppException(a.getErrCode(), a.getCodeMessage());
    }
  }
}
