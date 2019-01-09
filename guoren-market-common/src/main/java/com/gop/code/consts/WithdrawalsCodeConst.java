package com.gop.code.consts;

/**
 * 提现
 * 
 * @author wangyang
 *
 */
public class WithdrawalsCodeConst {
	// 用户等级不够
	public static final String LESS_WITHDRAWAL_LEVEL_ERROR = "107115";
	// 每日转出金额操过限制
	public static final String MAX_DAILY_WITHDRAWAL_AMOUNT_ERROR = "107102";
	// 单笔最大转出金额
	public static final String SUPER_MAX_WITHDRAWAL_CURRENCY_AMOUNT = "107103";
	// 单笔最少转出金额
	public static final String LESS_MIN_WITHDRAWAL_CURRENCY_AMOUNT = "107104";

	// 申请提现数量不能超过
	public static final String SUPER_MAX_WITHDRAWAL_COIN_AMOUNT = "107105";
	// TODO
	// 申请提现数量不能低于
	public static final String LESS_MIN_WITHDRAWAL_COIN_AMOUNT = "107106";

	public static final String WITHDRAWAL_COIN_STATE_ERROR = "107107";

	public static final String WITHDRAWAL_CURRENCY_STATE_ERROR = "107108";

	public static final String WITHDRAWAL_CURRENCY_RECORD_NOT_EXIST = "108109";

	public static final String WITHDRAWAL_COIN_RECORD_NOT_EXIST = "108110";
	//单日累计提现数量不能超过
	public static final String WITHDRAWAL_COIN_ADILY_MAX_OVER_ERROR = "107110";
	public static final String WITHDRAWAL_CURRENCY_RECORD_HAS_EXIST = "108111";

	public static final String WITHDRAWAL_COIN_RECORD_HAS_EXIST = "108112";

	public static final String LESS_MIN_WITHDRAWAL_COIN_FEE = "108113";
	public static final String WITHDRAWAL_COIN_ADDRESS_ERROR = "108114";
	//重置谷歌验证码后24小时之内不允许提币
	public static final String RESET_GOOGLE_CODE_WITHDRAW_LIMIT = "108115";

	public static final String AVAILABLE_AMOUNT_NOT_ENOUGH = "108116";

	// v1.0.0 新文案
	// 申请转出数量需大于手续费
	public static final String BROKER_LESS_CLOUD_MIN_FEE = "108117";
  // 申请转出数量不能低于
  public static final String BROKER_AMOUNT_NOT_RESONABLE = "108118";
	// 申请提现数量需大于手续费
	public static final String USER_LESS_MIN_FEE = "108119";
}
