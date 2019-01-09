package com.gop.financecheck.enums;


/**
 * 资产类型常量.
 *
 * @author kennyhuang
 * @date 2016-12-14
 */

public enum AccountSubject {
	UNKNOWN(0, 0), 
	// 充值
	DEPOSIT_COMMON(1001, 1001001),                                           // 充值
	DEPOSIT_COIN(1001, 1001002),                                             // 数字钱包充值
	DEPOSIT_YINHANG(1001, 1001003),                                          // 银行充值
	DEPOSIT_ZHIFUBAO(1001, 1001004),                                         // 支付宝
	DEPOSIT_OFFLINE(1001, 1001005),                                          // 离线充值
	DEPOSIT_CARD(1001, 1001006),                                             // 卡充值(电信，联通卡)
	
	
	// 提现
	WITHDRAW_COMMON(1002, 1002001),                                          // 提现
	WITHDRAW_COIN(1002, 1002002),                                            // 数字钱包提现
	WITHDRAW_SUPERPAY(1002, 1002003),                                        // 超级代付
	WITHDRAW_ULPAY(1003, 1002004),                                           // 合众支付
	WITHDRAW_OFFLINE(1002, 1002005),                                         // 离线支付
	WITHDRAW_QDBPAY(1002, 1002006),                                          // 钱袋宝支付
	WITHDRAW_ONWAY(1002, 1002008),                                           // 在途资产
	WITHDRAW_CARD(1002, 1002009),                                           // 卡提现(储备账户)
	
	// 交易
	MATCH_IN(1003, 1003001),                                                 // 买入：GOP，CNY增加
	MATCH_OUT(1003, 1003002),                                                // 卖出：GOP，CNY减少
	
	// 手续费
	FEE_WITHDRAW_INCOME(1004, 1004001),                                      // 提现手续费收入		
	FEE_WITHDRAW_SPEND(1004, 1004002),                                       //	提现手续费支出		
	FEE_COIN_NET_INCOME(1004, 1004003),                                      //	数字币网络交易手续费收入		
	FEE_COIN_NET_SPEND(1004, 1004004),                                       //	数字币网络交易手续费支出		
	FEE_TRANSFER_INCOME(1004, 1004005),                                      //	账户间转账手续费收入		
	FEE_TRANSFER_SPEND(1004, 1004006),                                       //	账户间转账手续费支出		
	FEE_DEPOSIT_THIRD_INCOME(1004, 1004007),                                 //	第三方渠道充值手续费收入		
	FEE_DEPOSIT_THIRD_SPEND(1004, 1004008),                                  //	第三方渠道充值手续费支出		
	FEE_WITHDRAW_THIRD_INCOME(1004, 1004009),                                //	第三方渠道提现手续费收入		
	FEE_WITHDRAW_THIRD_SPEND(1004, 1004010),                                 //	第三方渠道提现手续费支出		
	FEE_MATCH_INCOME(1004, 1004011),                                         //	交易手续费收入		
	FEE_MATCH_SPEND(1004, 1004012),                                          //	交易手续费支出
	
	// 系统转账
	SYSTEM_TRANSFER_DEPOSIT_IN(1005, 1005001),                               // 提现资金转入		
	SYSTEM_TRANSFER_DEPOSIT_OUT(1005, 1005002),                              // 提现资金转出		
	SYSTEM_TRANSFER_GUORENBAO_IN(1005, 1005003),                             // 果仁宝资金转入		
	SYSTEM_TRANSFER_GUORENBAO_OUT(1005, 1005004),                            // 果仁宝资金转出		
	SYSTEM_TRANSFER_HOT_IN(1005, 1005005),                                   // 热钱包转出		
	SYSTEM_TRANSFER_HOT_OUT(1005, 1005006),                                  // 热钱包转入		
	SYSTEM_TRANSFER_COLD_IN(1005, 1005007),                                  // 冷钱包转出		
	SYSTEM_TRANSFER_COLD_OUT(1005, 1005008),                                 // 冷钱包转入		
	SYSTEM_TRANSFER_ASSET_PLUS(1005, 1005009),                               // 资产增加		
	SYSTEM_TRANSFER_ASSET_LESS(1005, 1005010),                               // 资产减少		
	SYSTEM_TRANSFER_EXPENSE_PLUS(1005, 1005011),                             // 费用增加		
	SYSTEM_TRANSFER_EXPENSE_LESS(1005, 1005012),                             // 费用减少		
	SYSTEM_TRANSFER_LIABILITY_PLUS(1005, 1005013),                           // 负债增加		
	SYSTEM_TRANSFER_LIABILITY_LESS(1005, 1005014),                           // 负债减少		
	SYSTEM_TRANSFER_EQUITY_PLUS(1005, 1005015),                              // 权益增加		
	SYSTEM_TRANSFER_EQUITY_LESS(1005, 1005016),                              // 权益减少		
	SYSTEM_TRANSFER_INCOME_PLUS(1005, 1005017),                              // 收入增加		
	SYSTEM_TRANSFER_INCOME_LESS(1005, 1005018),                              // 收入减少
	
	// 坏帐
	BAD_DEBT_DEPOSIT_PLUS(1006, 1006001),                                    // 充值坏帐增加		
	BAD_DEBT_DEPOSIT_LESS(1006, 1006002),                                    // 充值坏帐减少		
	BAD_DEBT_WITHDRAW_PLUS(1006, 1006003),                                   // 提现坏帐增加		
	BAD_DEBT_WITHDRAW_LESS(1006, 1006004),                                   // 提现坏帐减少		
	BAD_DEBT_MATCH_PLUS(1006, 1006005),                                      // 交易坏帐增加		
	BAD_DEBT_MATCH_LESS(1006, 1006006),                                      // 交易坏帐减少		
	BAD_DEBT_OTHER_PLUS(1006, 1006007),                                      // 其它坏帐增加		
	BAD_DEBT_OTHER_LESS(1006, 1006008),                                      // 其它坏帐减少
	
	// 资产修正
	FIX_ASSET_PLUS(1007, 1007001),                                           // 补偿增加
	FIX_ASSET_LESS(1007, 1007002),                                           // 补偿减少
	
	// 利息
	INTEREST_INCOME(1008, 1008001),                                          // 利息收入		
	INTEREST_SPEND(1008, 1008002),                                           // 利息支出

	// 用户转账
	USER_TRANSFER_IN(1009, 1009001),                                          // 转入		
	USER_TRANSFER_OUT(1009, 1009002),                                         // 转出

	// 提现返回
	WITHDRAW_RETURN(1010, 1010001),                                          // 提现返回(果仁，提现返回)
	
	// 手续费返回
	FEE_WITHDRAW_RETURN(1011, 1011001),                                      // 提现手续费退回		

	// 最大值
	MAX(9999, 9999999); 
	
	private final Integer subject1;
	private final Integer subject2;

	AccountSubject(Integer subject1, Integer subject2) {
		this.subject1 = subject1;
		this.subject2 = subject2;
	}

	public Integer getSubject1() {
		return subject1;
	}

	public Integer getSubject2() {
		return subject2;
	}

	
}
