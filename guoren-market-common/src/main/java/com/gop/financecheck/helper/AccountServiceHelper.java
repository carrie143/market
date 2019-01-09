package com.gop.financecheck.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.gop.config.CommonConstants;
import com.gop.financecheck.domain.ItemFinanceHistory;
import com.gop.financecheck.enums.AccountChange;
import com.gop.financecheck.enums.AccountClass;
import com.gop.financecheck.enums.AccountDirection;
import com.gop.financecheck.enums.AccountSubject;
import com.gop.financecheck.enums.BusinessSubject;

/**
 * 账户工具类
 * 
 * @Author kennyhuang
 */

public class AccountServiceHelper {

	/**
	 * 根据业务类型获取借贷方向
	 * 
	 */
	public static AccountDirection getAccountDirection(AccountClass accountClass, AccountChange accountChange) {
		AccountDirection direction = AccountDirection.DEBIT;

		if (AccountClass.ASSET.equals(accountClass) || AccountClass.EXPENSE.equals(accountClass)) {
			if (AccountChange.PLUS.equals(accountChange)) {
				direction = AccountDirection.DEBIT;
			} else {
				direction = AccountDirection.CREDIT;
			}
		} else {
			if (AccountChange.PLUS.equals(accountChange)) {
				direction = AccountDirection.CREDIT;
			} else {
				direction = AccountDirection.DEBIT;
			}
		}

		return direction;
	}

	/**
	 * 构造财务流水
	 */
	public static ItemFinanceHistory buildFinanceHistoryItem(String accountNo, AccountClass accountClass,
			AccountChange accountChange, Integer brokerId, String assetType, Integer userId,
			BusinessSubject businessSubject, String outTxNo, AccountSubject subject, BigDecimal amount,
			BigDecimal balanceMoment, Integer balanceVersion, Integer createTime, String memo) {

		return buildFinanceHistoryItem(accountNo, accountClass, accountChange, brokerId,
				assetType, userId, businessSubject.toString(), outTxNo, subject.getSubject1(),
				subject.getSubject2(), amount, balanceMoment, balanceVersion, createTime, memo);
	}

	/**
	 * 构造财务流水
	 */
	public static ItemFinanceHistory buildFinanceHistoryItem(String accountNo, AccountClass accountClass,
			AccountChange accountChange, Integer brokerId, String assetType, Integer userId, String businessSubject,
			String outTxNo, Integer subject1, Integer subject2, BigDecimal amount, BigDecimal balanceMoment,
			Integer balanceVersion, Integer createTime, String memo) {
		ItemFinanceHistory item = null;

		BigDecimal amountDebit = CommonConstants.BIG_ZERO;
		BigDecimal amountCredit = CommonConstants.BIG_ZERO;
		BigDecimal amountPlus = CommonConstants.BIG_ZERO;
		BigDecimal amountLess = CommonConstants.BIG_ZERO;

		AccountDirection direction = AccountServiceHelper.getAccountDirection(accountClass, accountChange);
		if (AccountDirection.DEBIT.equals(direction)) {
			if (AccountChange.PLUS.equals(accountChange)) {
				amountDebit = amount;
				amountPlus = amount;
			} else {
				amountDebit = amount;
				amountLess = amount;
			}
		} else {
			if (AccountChange.PLUS.equals(accountChange)) {
				amountCredit = amount;
				amountPlus = amount;
			} else {
				amountCredit = amount;
				amountLess = amount;
			}
		}

		item = new ItemFinanceHistory(accountNo, accountClass.toString(), brokerId, assetType, userId, businessSubject,
				outTxNo, subject1, subject2, amountDebit.abs(), amountCredit.abs(), amountPlus.abs(), amountLess.abs(), balanceMoment,
				balanceVersion, createTime, memo);

		return item;
	}

	/**
	 * 构造财务流水
	 */
	public static List<ItemFinanceHistory> buildFinanceHistoryList(String businessSubject, String assetType,
			BigDecimal amount, String outTxNo, Integer createTime, String fromAccountNo, AccountClass fromAccountClass,
			AccountChange fromAccountChange, Integer fromBrokerId, Integer fromUserId, Integer fromSubject1,
			Integer fromSubject2, String fromMemo, BigDecimal fromBalanceMoment, Integer fromBalanceVersion,
			String toAccountNo, AccountClass toAccountClass, AccountChange toAccountChange, Integer toBrokerId,
			Integer toUserId, Integer toSubject1, Integer toSubject2, String toMemo, BigDecimal toBalanceMoment,
			Integer toBalanceVersion) {

		// 获取借贷方向
		AccountDirection fromDirection = AccountServiceHelper.getAccountDirection(fromAccountClass, fromAccountChange);
		AccountDirection toDirection = AccountServiceHelper.getAccountDirection(toAccountClass, toAccountChange);
		if (fromDirection == null || fromDirection.equals(toDirection)) {
			String strError = String.format("from方向={}，to方向={}，方向错误", fromDirection, toDirection);
			throw new IllegalStateException(strError);
		}

		List<ItemFinanceHistory> list = new ArrayList<ItemFinanceHistory>();

		ItemFinanceHistory fromItem = AccountServiceHelper.buildFinanceHistoryItem(fromAccountNo, fromAccountClass,
				fromAccountChange, fromBrokerId, assetType, fromUserId, businessSubject, outTxNo, fromSubject1,
				fromSubject2, amount, fromBalanceMoment, fromBalanceVersion, createTime, fromMemo);
		list.add(fromItem);

		ItemFinanceHistory toItem = AccountServiceHelper.buildFinanceHistoryItem(toAccountNo, toAccountClass,
				toAccountChange, toBrokerId, assetType, toUserId, businessSubject, outTxNo, toSubject1, toSubject2,
				amount, toBalanceMoment, toBalanceVersion, createTime, toMemo);
		list.add(toItem);

		return list;
	}

}
