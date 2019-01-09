package com.gop.api.cloud.common;

import com.gop.api.cloud.config.ApiConfig;

/**
 * Created by Lxa on 2018/6/6.
 *
 * @author lixianan
 */
public class ApiErrorCode {

  public static final ApiErrorCode SUCCESS = new ApiErrorCode("SUCCESS", "成功");   //成功
  public static final ApiErrorCode FAIL = new ApiErrorCode("FAIL", "系统异常");   //成功
  public static final ApiErrorCode PARAM_ERROR = new ApiErrorCode("PARAM_ERROR", "参数错误");  //参数错误
  public static final ApiErrorCode SIGN_ERROR = new ApiErrorCode("SIGN_ERROR", "签名错误");
  public static final ApiErrorCode DUPLICATE_DATA = new ApiErrorCode("DUPLICATE_DATA", "数据重复");
  public static final ApiErrorCode EMPTY_RECORD = new ApiErrorCode("EMPTY_RECORD", "记录为空");
  public static final ApiErrorCode TOO_MANY_RECORDS = new ApiErrorCode("TOO_MANY_RECORDS", "记录过多");
  public static final ApiErrorCode REPEAT_MESSAGE = new ApiErrorCode("REPEAT_MESSAGE", "重复的消息");  //重复的消息
  public static final ApiErrorCode USER_NOT_EXIST = new ApiErrorCode("USER_NOT_EXIST", "用户信息不存在");
  public static final ApiErrorCode BROKER_ASSET_NOT_EXIST = new ApiErrorCode("BROKER_ASSET_NOT_EXIST", "券商没有分配该币种");
  public static final ApiErrorCode BROKER_ASSET_EXIST = new ApiErrorCode("BROKER_ASSET_EXIST", "券商已经分配该币种");
  public static final ApiErrorCode SYMBOL_FORMAT_ERROR = new ApiErrorCode("SYMBOL_FORMAT_ERROR", "币对格式错误");
  public static final ApiErrorCode INVALID_SYMBOL = new ApiErrorCode("INVALID_SYMBOL", "交易对未生效");
  public static final ApiErrorCode NOT_LISTED_SYMBOL = new ApiErrorCode("NOT_LISTED_SYMBOL", "symbol未启用");
  public static final ApiErrorCode ORDER_CREATE_ERROR = new ApiErrorCode("ORDER_CREATE_ERROR", "订单创建失败");
  public static final ApiErrorCode ORDER_NOT_EXIST = new ApiErrorCode("ORDER_NOT_EXIST", "订单信息不存在");
  public static final ApiErrorCode ORDER_STATE_ERROR = new ApiErrorCode("ORDER_STATE_ERROR", "订单状态错误");
  public static final ApiErrorCode ORDER_HAD_CANCELLED = new ApiErrorCode("ORDER_HAD_CANCELLED", "订单已撤销");
  public static final ApiErrorCode ORDER_HAD_PROCESSING = new ApiErrorCode("ORDER_HAD_PROCESSING", "订单处理中");
  public static final ApiErrorCode ORDER_HAD_TRADED = new ApiErrorCode("ORDER_HAD_TRADED", "订单已撮合");
  public static final ApiErrorCode NO_PERMISSION = new ApiErrorCode("NO_PERMISSION", "没有权限");  //没有权限
  public static final ApiErrorCode BROKER_NOT_EXIST = new ApiErrorCode("BROKER_NOT_EXIST", "券商未入驻");
  public static final ApiErrorCode ADDRESS_ASSIGN_ERROR = new ApiErrorCode("ADDRESS_ASSIGN_ERROR", "生成地址出错");

  //安全的操作
  public static final ApiErrorCode NO_LOGIN = new ApiErrorCode("NO_LOGIN", "用户未登陆");
  public static final ApiErrorCode TOKE_HAS_INVALID = new ApiErrorCode("TOKE_HAS_INVALID", "登陆会话超时");

  //谷歌验证码
  public static final ApiErrorCode MANAGER_CAN_NOT_RESET_GOOGLE_CODE = new ApiErrorCode("MANAGER_CAN_NOT_RESET_GOOGLE_CODE", "管理员不能重置谷歌验证码");
  public static final ApiErrorCode MANAGER_GOOGLE_CODE_HAS_SET = new ApiErrorCode("MANAGER_GOOGLE_CODE_HAS_SET", "谷歌验证码已经重置");
  public static final ApiErrorCode GOOGLE_CODE_ERROR = new ApiErrorCode("GOOGLE_CODE_ERROR", "谷歌验证码错误");
  //mq消息
  public static final ApiErrorCode MESSAGE_TO_MQ_FAIL = new ApiErrorCode("MESSAGE_TO_MQ_FAIL", "发送消息到mq失败");

  //用户信息之类
  public static final ApiErrorCode NO_REGISTER = new ApiErrorCode("NO_REGISTER", "未注册");
  public static final ApiErrorCode LOGIN_PASSWORD_ERROR = new ApiErrorCode("LOGIN_PASSWORD_ERROR", "登录密码错误");
  public static final ApiErrorCode ROLE_EXIST = new ApiErrorCode("ROLE_EXIST", "角色已存在");
  public static final ApiErrorCode ROLE_CAN_NOT_DELETE = new ApiErrorCode("ROLE_CAN_NOT_DELETE", "角色不能删除");

  //资产相关
  public static final ApiErrorCode ADDRESS_STATUS_ERROR = new ApiErrorCode("ADDRESS_STATUS_ERROR", "地址状态错误");
  public static final ApiErrorCode ACCOUNT_STATUS_ERROR = new ApiErrorCode("ACCOUNT_STATUS_ERROR", "账号状态异常");
  public static final ApiErrorCode WITHDRAW_FEE_TOO_LOW = new ApiErrorCode("WITHDRAW_FEE_TOO_LOW", "提现手续费小于平台手续费");
  public static final ApiErrorCode WITHDRAW_TOO_LOW = new ApiErrorCode("WITHDRAW_TOO_LOW", "提现金额小于平台手续费");
  public static final ApiErrorCode COIN_ASSERT_LESS = new ApiErrorCode("COIN_ASSERT_LESS", "可用资金余额不足");
  public static final ApiErrorCode COIN_LOCK_ASSERT_LESS = new ApiErrorCode("COIN_LOCK_ASSERT_LESS", "锁定资金余额不足");
  public static final ApiErrorCode ADDRESS_DELETE_ERROR = new ApiErrorCode("ADDRESS_DELETE_ERROR", "删除地址错误");
  public static final ApiErrorCode WITHDRAW_ORDER_STAUTS_ERROR = new ApiErrorCode("WITHDRAW_ORDER_STAUTS_ERROR", "提现订单状态异常");
  public static final ApiErrorCode WITHDRAW_ORDER_STAUTS_UPDATE_ERROR = new ApiErrorCode("WITHDRAW_ORDER_STAUTS_UPDATE_ERROR", "提现订单状态更新异常");
  public static final ApiErrorCode DEPOSIT_ORDER_STAUTS_ERROR = new ApiErrorCode("DEPOSIT_ORDER_STAUTS_ERROR", "充值订单状态异常");
  public static final ApiErrorCode DEPOSIT_ORDER_NOT_EXIST = new ApiErrorCode("DEPOSIT_ORDER_NOT_EXIST", "充值订单不存在");
  public static final ApiErrorCode COIN_DEPOSIT_ADDRESS_INVALID = new ApiErrorCode("COIN_DEPOSIT_ADDRESS_INVALID", "充值地址不存在");
  public static final ApiErrorCode DEPOSIT_ORDER_INSERT_ERROR = new ApiErrorCode("DEPOSIT_ORDER_INSERT_ERROR", "充值订单新增异常");
  public static final ApiErrorCode DEPOSIT_ORDER_STAUTS_UPDATE_ERROR = new ApiErrorCode("DEPOSIT_ORDER_STAUTS_UPDATE_ERROR", "充值订单状态更新异常");
  public static final ApiErrorCode USER_TOTAL_LIMIT = new ApiErrorCode("USER_TOTAL_LIMIT", "用户注册总数超限");
  public static final ApiErrorCode WITHDRAW_ASSETCODE_NOT_SUPPORT = new ApiErrorCode("WITHDRAW_ASSETCODE_NOT_SUPPORT", "提现币种未配置");
  public static final ApiErrorCode UPDATE_FINANCE_ERROR = new ApiErrorCode("UPDATE_FINANCE_ERROR", "更新资产失败");
  public static final ApiErrorCode BATCH_UPDATE_FINANCEDETAIL_ERROR = new ApiErrorCode("BATCH_INSERT_FINANCEDETAIL_ERROR", "批量更新资产失败");
  public static final ApiErrorCode NOT_LISTED_ASSET = new ApiErrorCode("NOT_LISTED_ASSET", "请求币种存在未开启的币种");
  //交易类
  public static final ApiErrorCode OVER_MAX_PRICE = new ApiErrorCode("OVER_MAX_PRICE", "价格过大");
  private final String errorCode;
  private final String errorMsg;

  private ApiErrorCode(String errorCode, String errorMsg) {
    this.errorCode = errorCode;
    this.errorMsg = errorMsg;
  }

  public String getApiErrorCode() {
    return errorCode;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

}
