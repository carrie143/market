package com.gop.currency.withdraw.gateway.service.cibpay.config;

import java.util.ResourceBundle;

public class CIBConfig {

	public final static ResourceBundle resourceBundle = ResourceBundle.getBundle("key/conf");

	// 商户appid
	public final static String APP_ID = resourceBundle.getString("appId");

	// 商户令牌
	public final static String CIB_MERCHANT_TOKEN = resourceBundle.getString("cib_merchant_token");

	// 收付直通车服务器证书
	public final static String APPSVR_SERVER_CERT = resourceBundle.getString("appsvr_server_cert");

	// 商户客户端证书路径
	public final static String APPSVR_CLIENT_PFX = resourceBundle.getString("appsvr_client_pfx");

	//
//	public final static String APPSVR_CLIENT_PFX_ALIAS = resourceBundle.getString("appsvr_client_pfx_alias");

	// 证书密钥
	public final static String APPSVR_CLIENT_PFX_PWD = resourceBundle.getString("appsvr_client_pfx_pwd");

	// 二级商户名称
	public final static String CIB_SUB_MERCHANT_NAME = resourceBundle.getString("cib_sub_merchant_name");

	// 是否开发环境
	public final static boolean DEV_ENV = Boolean.parseBoolean(resourceBundle.getString("devEnv"));

	// 是否需要校验签名

	public final static boolean NEED_CHECK_SIGN = Boolean.parseBoolean(resourceBundle.getString("need_check_sign"));

	// #收付直通车服务器证书，RSA算法验签使用
	// public final static String EPAY_CERT =
	// resourceBundle.getString("epay_cert");

	public final static String GP_API = resourceBundle.getString("gp_api");

	public final static String PY_API = resourceBundle.getString("py_api");

	public final static String EP_API = resourceBundle.getString("ep_api");

	public static String httpsRequestClassName = "com.gop.currency.withdraw.gateway.service.cibpay.comm.HttpsPostRequest";

	// 通讯失败时返回的报文
	public static String TXN_ERROR_RESULT = "{\"errcode\":\"EPAY_29001\",\"errmsg\":\"[EPAY_29001]通讯错误或超时，交易未决\"}";
	// 系统异常时返回的报文
	public static String SYS_ERROR_RESULT = "{\"errcode\":\"EPAY_29099\",\"errmsg\":\"[EPAY_29099]未知错误，请检查是否为最新版本SDK或是否配置错误\"}";
	// 对账文件下载，写入文件异常返回报文
	public static String FILE_ERROR_RESULT = "{\"errcode\":\"EPAY_29002\",\"errmsg\":\"[EPAY_29002]写入对账文件失败\"}";
	// 验签失败
	public static String SIGN_ERROR_RESULT = "{\"errcode\":\"EPAY_29098\",\"errmsg\":\"[EPAY_29098]应答消息验签失败，交易未决\"}";
	// 对账文件下载，下载成功返回报文
	public static String SUCCESS_RESULT = "{\"errcode\":\"EPAY_00000\",\"errmsg\":\"[EPAY_00000]下载成功\"}";

//	public static void main(String[] args) {
//		System.out.println(Boolean.parseBoolean("true"));
//		System.out.println(APPSVR_CLIENT_PFX);
//	}

}
