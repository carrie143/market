package com.gop.code.consts;

/**
 * C2C
 * 
 * @author sunhaotian 涉及C2C交易的
 *
 */
public class C2cCodeConst {
	/** 
	 * 代码号段划分
	 * 115000-115099 基本信息
	 * 115100-115199 广告
	 * 115200-115299 交易单与交易单申诉
	 * 115300-115399 金牌交易 
	 */
	// 今日对该用户鼓励次数已经50次
	public static final String ENCOURAGE_COUNT_MAX = "115000";
	// 不能鼓励本人
	public static final String ENCOURAGE_SELF = "115001";
	// Bank已修改
	public static final String CREATE_BANK_ERROR = "115002";
	// alipay已修改
	public static final String CREATE_ALIPAY_ERROR = "115003";
	
	
	// 该广告已被购买
	public static final String ADVERT_HAS_PURCHASED = "115100";
	// 该广告已被删除
	public static final String ADVERT_HAS_DELETED = "115101";
	//该广告不存在
	public static final String ADVERT_NOT_EXIST = "115102";
	//该广告已过期
	public static final String ADVERT_HAS_OVERDUE = "115103";
	// 广告单状态更新失败
	public static final String ADVERT_UPDATE_FAILED = "115104";
	//最大售出个数小于最小售出个数
	public static final String ADVERT_MAX_AMOUNT_IS_LESS_MIN_AMOUNT = "115105";
	//用户未设置昵称
	public static final String NO_SET_NICKNAME ="115106";
	//用户未设置手机号
	public static final String NO_SET_PHONE = "115107";
	//最大售出个数超过资产余额
	public static final String ADVERT_MAX_AMOUNT_IS_BIGGER_FINANCE = "115108";
	//交易价格小于0
	public static final String ADVERT_TRADE_PRICE_LESS_ZERO = "115109";
	//预计购买小于0
	public static final String ADVERT_BUY_PRICE_LESS_ZERO = "115110";
	//最小售出个数小于0
	public static final String ADVERT_MIN_AMOUNT_LESS_ZERO = "115111";
	//最大售出个数小于0
	public static final String ADVERT_MAX_AMOUNT_LESS_ZERO = "115112";
	//查询用户资产异常
	public static final String QUERY_USER_FINANCE_EXCEPTION = "115113";
	//该广告不属于本人
	public static final String ADVERT_NOT_BELONG_TO_USER = "115114";
	//用户未绑定支付宝信息
	public static final String NO_SET_ALIPAY_INFO = "115115";
	//用户未绑定银行卡信息
	public static final String NO_SET_BANK_INFO = "115116";
	//无效的交易币种类型
	public static final String ADVERT_ASSET_CODE_INVALID = "115117";
	//无效的国家类型
	public static final String ADVERT_COUNTRY_INVALID = "115118";
	//无效的货币类型
	public static final String ADVERT_CURRENCY_INVALID = "115119";
	
	
	// 交易单
	// 交易单不存在
	public static final String INVALID_TRANSORDER = "115200";
	// 状态不匹配
	public static final String TRANSORDER_STATUS_MATCH_ERROR = "115201";
	// 禁止自买自卖
	public static final String SELF_TRADE_FORBIDDEN = "115202";
	// 日取消订单过多,禁止买卖
	public static final String FORBID_TRADE_BY_CANCEL_ORDER_MORETHAN_3_DAILY = "115203";
	// 订单已被创建
	public static final String ORDER_CREATED = "115204";
	// 交易单与操作人不匹配
	public static final String USER_MATCH_ORDER_ERROR = "115205";
	// 订单广告信息异常
	public static final String ADVERTID_IN_ORDER_FAULT = "115206";
	// 订单支付超时
	public static final String OVERTIME_ERROR = "115207";
	// 申诉单
	// 申诉单不存在
	public static final String INVALID_COMPLAINT = "115208";
	// 申诉单重复申诉
	public static final String COMPLAINT_CREATED = "115209";
	// 申诉单用户匹配不一致
	public static final String USER_MATCH_COMPLAINT_ERROR = "115210";
	// 申诉单中交易单不存在
	public static final String ORDER_IN_COMPLAINT_FAULT = "115211";
	// 申诉单中的交易单不存在
	public static final String ORDER_IN_COMPLAINT_INEXISTENCE = "115212";
	// 申诉单交易单号不存在
	public static final String TRANSORDER_STATUS_NO_COMPLAINT = "115213";
	// 交易单创建失败
	public static final String TRANSORDER_CREATE_FAILED = "115214";
	// 申诉单创建失败
	public static final String COMPLAINT_UPDATE_FAILED = "115215";
	// 订单数字与广告中的数字不匹配
	public static final String NUMBER_MATCH_ERROR_IN_ADVERT = "115216";
	// 订单更新异常
	public static final String TRANSORDER_UPDATE_ERROR = "115217";
	// 订单创建异常
	public static final String TRANSORDER_CREATE_ERROR = "115218";
	// 订单操作记录异常
	public static final String TRANSORDERLOG_RECORD_ERROR = "115219";
	// 订单取消失败
	public static final String TRANSORDER_CANCEL_FAILED = "115220";
	// 有未支付订单
	public static final String TRANSORDER_HAVE_AN_UNPAY = "115221";
}
