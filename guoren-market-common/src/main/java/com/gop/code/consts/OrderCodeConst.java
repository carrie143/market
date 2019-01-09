package com.gop.code.consts;

/**
 * 撮合下订单
 * 
 * @author wangyang
 *
 */
public class OrderCodeConst {
	// 挂单价格超出最高挂单价
	public static final String MAX_ORDER_PRICE_ERROR = "101100";

	// 挂单价格低于最低挂单价
	public static final String MIN_ORDER_PRICE_ERROR = "101101";

	public static final String ORDER_NOT_EXISTED = "101102";

	public static final String ORDER_HAD_TRADED = "101103";

	public static final String ORDER_HAD_CANCELLED = "101104";
	public static final String ORDER_HAD_PROCESSING= "101116";
	// 挂单gop数量超出最高量
	public static final String MAX_ORDER_COIN_ERROR = "101105";

	// 挂单gop数量低于最低量
	public static final String MIN_ORDER_COIN_ERROR = "101106";

	public static final String UNHANDLE_ORDER_TYPE = "101107";

	public static final String ORDER_SUPER_DADILY_MAX_PRICE = "101108";
	
	public static final String ORDER_LESS_DADILY_MIN_PRICE = "101110";

	public static final String HARDEN_FORBID = "101109";
	
	public static final String LIMIT_FOARBID = "101111";

	public static final String MIN_ORDER_AMOUNT_ERROR = "101114";

	public static final String MAX_ORDER_AMOUNT_ERROR = "101115";
	
	public static final String PFRCISION_ERROR="101116";
	
	public static final String ORDER_STATUS_ERROR="101117";
}
