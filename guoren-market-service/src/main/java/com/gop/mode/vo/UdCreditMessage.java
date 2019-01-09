package com.gop.mode.vo;

public enum UdCreditMessage {
	SUCCESS("000000", "交易成功"), SERVER_ERROR("999999", "系统异常"), CREDIT_FAIL("000001", "验证失败"), BUSINESS_REQUEST_ERROR(
			"100001", "商户请求签名错误"), BUSINESS_REQUEST_NOTIN_WHITELIST_ERROR("200007",
					"商户请求地址未在白名单"), REQUEST_FORMAT_ERROR("100005", "请求报文格式错误"), PARAMETER_LEAK("100006",
							"参数缺失"), BUSINESS_NOT_EXIST("100003", "商户不存在"), BUSINESS_NOT_OPEN_AUTHORITY("200005",
									"商户未开通此产品权限"), PRODUCT_CONFIG_ERROR("200006", "产品配置异常"), PARAMETER_ERROR("100002",
											"参数有误"), OUTER_TXNO_REPEAT("100013", "外部订单号重复"), IN_PRODUCT_NO_NOT_EXIST(
													"100024", "传入产品编号不存在"), BUSINESS_FINANCE_ACCOUNT_ERROR("100035",
															"商户资金账户状态异常");
	private String code;
	private String desc;

	public static final String OK_CODE = "000000";

	private UdCreditMessage(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static UdCreditMessage getByCode(String code) {
		switch (code) {
		case "000000":
			return SUCCESS;
		case "999999":
			return SERVER_ERROR;
		case "000001":
			return CREDIT_FAIL;
		case "100001":
			return BUSINESS_REQUEST_ERROR;
		case "200007":
			return BUSINESS_REQUEST_NOTIN_WHITELIST_ERROR;
		case "100005":
			return REQUEST_FORMAT_ERROR;
		case "100006":
			return PARAMETER_LEAK;
		case "100003":
			return BUSINESS_NOT_EXIST;
		case "200005":
			return BUSINESS_NOT_OPEN_AUTHORITY;
		case "200006":
			return PRODUCT_CONFIG_ERROR;
		case "100002":
			return PARAMETER_ERROR;
		case "100013":
			return OUTER_TXNO_REPEAT;
		case "100024":
			return IN_PRODUCT_NO_NOT_EXIST;
		case "100035":
			return BUSINESS_FINANCE_ACCOUNT_ERROR;
		default:
			return SERVER_ERROR;
		}
	}

	public String getDesc() {
		return this.desc;
	}

}
