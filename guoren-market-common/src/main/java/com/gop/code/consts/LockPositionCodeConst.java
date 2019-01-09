package com.gop.code.consts;

public class LockPositionCodeConst {
	// 锁仓金额大于可用金额
	public static final String AVAILABLE_ACOUNT_LESS = "119100";
	// 无可解锁资产(零资产解锁报错)
	public static final String NO_ACOUNT_UNLOCK_ERROR = "119101";
	// 不在可锁仓时间范围之内
	public static final String CURRENT_TIME_CAN_NOT_LOCK = "119102";
	// 当前时间无法进行清算
	public static final String CURRENT_TIME_CAN_NOT_CALCULATE = "119103";
	// 尚未清算当月奖励，无法进行发放
	public static final String NOT_CALCULATE_REWARD = "119104";
	//当月奖励已发放过，无法再次发放
	public static final String HAS_GRANTED_REWARD = "119105";
	//锁仓奖励发放账户余额不足，无法进行发放
	public static final String BALANCE_CAN_NOT_GRANT_REWARD = "119106";
}
