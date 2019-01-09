package com.gop.api.cloud;

public final class ApiConstants {
  // 资产
  public static final String ASSET_ACCOUNTS = "asset/accounts";
  public static final String BROKER_CONFIG_ASSET_LIST = "coin/broker-configAsset-list";
  public static final String QUERY_BROKER_ASSET = "coin/brokerAsset-query";
  public static final String GET_SYMBOL_SCALE =  "symbol/get-scale";
  // 费用
  public static final String QUERY_SYMBOLS =  "symbol/symbols/coin";
  public static final String UPDATE_SYMBOL_FEE =  "symbol/save-update-fee";
  public static final String GET_SYMBOL_FEE =  "symbol/get-fee";
  // 用户
  public static final String ACTIVATE_USER = "user/create-user";
  public static final String WHITELIST = "user/modify-whitelist";
  // 交易
  public static final String MATCH_ORDER_RECORD = "match/match-order/match-record";
  public static final String MATCH_ORDER_DETAIL = "match/match-order/current" ;
  public static final String MATCH_ORDER_HISTORY = "match/match-order/history";
  public static final String MATCH_ORDER_CANCEL = "match/match-order/cancel";
  public static final String MATCH_ORDER = "match/order";
  public static final String USER_ORDER_RECORD = "trade/userTradeRecord";
  // 充值提现
  public static final String TRANSFER_IN_ADDRESS_QUERY = "coin-transfer/in-address-query";
  public static final String DEPOSITE_COIN_DETAILS = "deposit/deposit-coin-details";
  public static final String WITHDRAW_APPLY = "withdraw/withdraw-coin-user";
  public static final String WITHDRAW_CONFIRM = "withdraw/confirm";
  public static final String QUERY_BALANCE = "withdraw/query-balance";
  public static final String WITHDRAW_COIN_DETAIL = "withdraw/withdraw-coin-details";
  public static final String COUNT_UNVERIFIED = "withdraw/unverifiedCount";
  public static final String WITHDRAW_BROKER = "withdraw/withdraw-coin-broker";
  public static final String QUERY_WITHDRAW_TOTAL = "withdraw/queryWithdrawTotal";
  // 结算
  public static final String SETTLE_RECORD = "settle/settle-record";
  public static final String KLINE = "data/kline/kline-pages";
}
