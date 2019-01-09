package com.gop.api.cloud.service;

import com.github.pagehelper.PageInfo;
import com.gop.api.cloud.request.AssetRequest;
import com.gop.api.cloud.request.BrokerAssetAccountRequest;
import com.gop.api.cloud.request.BrokerAssetRequest;
import com.gop.api.cloud.request.BrokerWithdrawRequest;
import com.gop.api.cloud.request.CancelOrderReq;
import com.gop.api.cloud.request.DepositCallbackDto;
import com.gop.api.cloud.request.DepositQueryRequest;
import com.gop.api.cloud.request.KlineQueryReq;
import com.gop.api.cloud.request.MatchOrderPageQueryReq;
import com.gop.api.cloud.request.MatchOrderReq;
import com.gop.api.cloud.request.MatchRecordPageQueryReq;
import com.gop.api.cloud.request.CreateUserReq;
import com.gop.api.cloud.request.ModifyWhitelistReq;
import com.gop.api.cloud.request.SettleQueryRequest;
import com.gop.api.cloud.request.SymbolFeeAddReq;
import com.gop.api.cloud.request.SymbolQueryReq;
import com.gop.api.cloud.request.SymbolScaleReq;
import com.gop.api.cloud.request.TradeOrderQueryReq;
import com.gop.api.cloud.request.UnVerifiedCountRequest;
import com.gop.api.cloud.request.WithdrawCallbackDto;
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

import com.gop.exception.AppException;
import com.gop.match.dto.SymbolDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by YAO on 2018/6/26.
 */
public interface CloudApiService {

  /**
   * 查询用户资产列表
   * @param request
   * @return
   */
  List<AssetDto> getUserAccountAssets(BrokerAssetAccountRequest request);

  /**
   * 获取云平台已配置资产列表
   * @param request
   * @return
   */
  List<BrokerConfigAssetDto> getConfigAssetList(AssetRequest request);

  /**
   *用户激活调用券商接口
   * @return
   */
  UserData activateNewUser(CreateUserReq req);

  /**
   * 查询提现记录
   * @param request
   * @return
   */
  PageInfo<WithdrawCoinDetailDto> withdrawCoinDetail(WithdrawQueryRequest request);

  /**
   * 查余额
   * @param request
   * @return
   */
  BigDecimal queryBalance(AssetRequest request);

  /**
   * 提现确认提交
   * @param request
   */
  void withdrawConfirm(WithdrawConfirmRequest request);

  /**
   * 用户提现申请，生成订单
   * @param request
   */
  void userWithdrawCoin(WithdrawCoinRequest request) throws Exception;

  /**
   * 充值查询
   * @param depositQueryRequest
   * @return
   */
  PageInfo<DepositDetailDto> depositeCoinDetail(DepositQueryRequest depositQueryRequest);

  /**
   * 转入地址获取
   * @param assetRequest
   * @return
   */
  List<TransferInAddressDto> transferInAddressQuery(AssetRequest assetRequest);

  /**
   * 撮合订单
   * @param matchOrderReq
   */
  void matchOrder(MatchOrderReq matchOrderReq);

  /**
   * 撮合订单取消
   * @param cancelOrderReq
   */
  void matchOrderCancel(CancelOrderReq cancelOrderReq);

  /**
   * 撮合订单历史查询
   * @param matchOrderPageQueryReq
   * @return
   */
  PageInfo<MatchOrderDetail> matchOrderHistory(MatchOrderPageQueryReq matchOrderPageQueryReq);

  /**
   * 当前撮合订单详情
   * @param matchOrderPageQueryReq
   * @return
   */
  PageInfo<MatchOrderDetail> matchOrderCurrentDetail(MatchOrderPageQueryReq matchOrderPageQueryReq);

  /**
   * 撮合记录
   * @param matchRecordPageQueryReq
   * @return
   */
  PageInfo<MatchRecordDto> matchRecord(MatchRecordPageQueryReq matchRecordPageQueryReq);

  /**
   * 查询用户交易记录
   * @param request
   * @return
   */
  PageInfo<TradeOrderDto> queryUserOrderRecord(TradeOrderQueryReq request);

  /**
   * 添加/更新交易对费用
   * @param symbol
   * @return
   */
  BrokerSymbolFeeData updateSymbolFee(SymbolFeeAddReq symbol);

  /**
   * 获取交易对
   * @param request
   * @return
   */
  List<SymbolData> getSymbols(SymbolQueryReq request);

  /**
   * 统计提现订单数量
   * @param request
   * @return
   */
  Map<String,Integer> countWithdrawByStatus(UnVerifiedCountRequest request);

  /**
   * 查询已提现总数
   * @return
   */
  Map<String,BigDecimal> queryWithdrawTotal(WithdrawTotalAmountRequest request);

  /**
   * 结算记录查询
   * @param request
   * @return
   */
  PageInfo<SettleRecordDto> getSettleRecord(SettleQueryRequest request);

  /**
   * 券商提现申请
   * @param request
   */
  void brokerWithdraw(BrokerWithdrawRequest request);

  /**
   * 查询券商资产
   * @param request
   * @return
   */
  BrokerPageModel<BrokerAssetDto> queryBrokerFinance(BrokerAssetRequest request);

  /**
   * k线数据
   */
  KlineQueryPages klineQueryPages(KlineQueryReq request);

  /**
   * 白名单
   * @param req
   */
  void updateWhiteList(ModifyWhitelistReq req);

  /**
   * 回调
   * @param data
   */
  void callback(String data);

  /**
   * 获取精度
   * @param request
   * @return
   */
  List<SymbolScale> getSymbolScale(SymbolScaleReq request);
}
