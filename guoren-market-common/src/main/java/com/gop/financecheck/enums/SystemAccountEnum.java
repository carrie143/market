package com.gop.financecheck.enums;

/**
 * 资产类型常量.
 *
 * @author kennyhuang
 * @date 2016-12-14
 */

public enum SystemAccountEnum {
	UNKNOWN("", 0, AccountClass.UNKNOWN, ""), 
	SYS_ACCOUNT_NO_DEPOSIT_WITHDRAW("10002", 10002, AccountClass.ASSET, "BUSINESS"),    // 系统充值提现总账户：资产类账户
	SYS_ACCOUNT_NO_BAD_DEBIT("10003", 10003, AccountClass.EXPENSE, "BUSINESS"),         // 系统坏帐总账户：费用类账户
	SYS_ACCOUNT_NO_EXPENSE("10004", 10004, AccountClass.EXPENSE, "BUSINESS"),           // 系统支出总账户：费用类账户
	SYS_ACCOUNT_NO_INCOME("10005", 10005, AccountClass.INCOME, "BUSINESS"),             // 系统收入总账户：收入类账户
	SYS_ACCOUNT_NO_GUORENBAO_HOT("10006", 10006, AccountClass.ASSET, "BUSINESS"),       // 果仁宝热钱包总账户：资产类账户
	SYS_ACCOUNT_NO_GUORENBAO_COLD("10007", 10007, AccountClass.ASSET, "BUSINESS"),      // 果仁宝冷钱包总账户：资产类账户
	SYS_ACCOUNT_NO_EXCHANGE_HOT("10008", 10008, AccountClass.ASSET, "BUSINESS"),        // 果仁市场热钱包总账户：资产类账户
	SYS_ACCOUNT_NO_EXCHANGE_COLD("10009", 10009, AccountClass.ASSET, "BUSINESS");       // 果仁市场冷钱包总账户：资产类账户

	private final String accountNo;
	private final Integer userId;
	private final AccountClass accountClass;
	private final String accountType;

	SystemAccountEnum(String accountNo, Integer userId, AccountClass accountClass, String accountType) {
		this.accountNo = accountNo;
		this.userId = userId;
		this.accountClass = accountClass;
		this.accountType = accountType;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public Integer getUserId() {
		return userId;
	}

	public AccountClass getAccountClass() {
		return accountClass;
	}

	public String getAccountType() {
		return accountType;
	}
	
}
