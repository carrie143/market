package com.gop.code.consts;

/**
 * 用于消息的发送
 * 
 * @author wangyang
 *
 */
public class MessageConst {
	public static final String CASH_WITHDRAW_REFUSEM_MESSAGE = "201100";
	public static final String CASH_WITHDRAW_SUCCESS_MESSAGE = "201101";
	public static final String CASH_WITHDRAW_FAIL_MESSAGE = "201102";
	public static final String CASH_DEPOSIT_SUCCESS_MESSAGE = "201103";

	public static final String COIN_WITHDRAW_REFUSEM_MESSAGE = "201104";
	public static final String COIN_WITHDRAW_SUCCESS_MESSAGE = "201105";
	public static final String COIN_WITHDRAW_FAIL_MESSAGE = "201106";
	public static final String COIN_DEPOSIT_SUCCESS_MESSAGE = "201107";
	
	public static final String ID_VERIFY_SUCCESS_MESSAGE = "201108";
	public static final String ID_VERIFY_FAIL_MESSAGE = "201109";
	public static final String ADDRESS_VERIFY_SUCCESS_MESSAGE = "201110";
	public static final String ADDRESS_VERIFY_FAIL_MESSAGE = "201111";
	public static final String EMAIL_MSG_TEMPLATE = "201113";
	public static final String EMAIL_MSG_TEMPLATE_OPERATION="201114";
	
	//C2C交易
//	public static final String C2C_SELL_ADVERT_DEPLOY_SUCCESS_MESSAGE="202100";
//	public static final String C2C_BUY_HAS_PAID_MESSAGE="202101";
//	public static final String C2C_SELL_HAS_TRANSFER_MESSAGE="202102";
//	public static final String C2C_BUY_BEFORE_FIVE_MINUTES_OVERTIME_MESSAGE="202103";
//	public static final String C2C_BUY_HAS_OVERTIME_TO_BUY_MESSAGE="202104";
//	public static final String C2C_BUY_HAS_CANCEL_TO_BUY_MESSAGE="202105";
//	public static final String C2C_BUY_HAS_COMPLAIN_TO_BUY_MESSAGE="202106";
//	public static final String C2C_BUY_HAS_COMPLAIN_TO_SELL_MESSAGE="202107";
//	public static final String C2C_SELL_HAS_COMPLAIN_TO_BUY_MESSAGE="202108";
//	public static final String C2C_SELL_HAS_COMPLAIN_TO_SELL_MESSAGE="202109";
	// 信息发送内容
	public static final String C2C_SELL_ADVERT_DEPLOY_SUCCESS_MESSAGE="您成功創建了一條%s賣出廣告，請到“我的廣告”中查看。";
	public static final String C2C_BUY_HAS_PAID_MESSAGE="您售賣%s的訂單%s，交易金額￥%s，數量%s%s，買家已付款，請盡快確認打幣，如有問題請咨詢客服。";
	public static final String C2C_SELL_HAS_TRANSFER_MESSAGE="您的訂單號%s，交易金額為￥%s，數量%s%s的交易已完成，請及時查詢賬戶餘額。";
	public static final String C2C_BUY_BEFORE_FIVE_MINUTES_OVERTIME_MESSAGE="您購買%s的訂單%s會在五分鐘后關閉，交易金額為￥%s，數量%s%s，如已支付請盡快到“我的訂單”點擊確認打款。";
	public static final String C2C_BUY_HAS_OVERTIME_TO_BUY_MESSAGE="您購買%s的訂單%s，交易金額為￥%s,數量為%s%s，由於超時未支付已被系統取消。";
	public static final String C2C_BUY_HAS_CANCEL_TO_BUY_MESSAGE="您購買%s的訂單%s，交易金額為￥%s，買入的%s%s，已於%s被取消，請及時查看。";
	public static final String C2C_BUY_HAS_COMPLAIN_TO_BUY_MESSAGE="您購買%s的訂單%s，已於%s提起申訴，客服會在1-2個工作日與您溝通處理，請耐心等待。";
	public static final String C2C_BUY_HAS_COMPLAIN_TO_SELL_MESSAGE="您售賣%s的訂單%s，已於%s被買家提起申訴，客服會在1-2個工作日與您溝通處理，請耐心等待。";
	public static final String C2C_SELL_HAS_COMPLAIN_TO_BUY_MESSAGE="您購買%s的訂單%s，已於%s被賣家提起申訴，客服會在1-2個工作日與您溝通處理，請耐心等待。";
	public static final String C2C_SELL_HAS_COMPLAIN_TO_SELL_MESSAGE="您售賣%s的訂單%s，已於%s提起申訴，客服會在1-2個工作日與您溝通處理，請耐心等待。";
	
}
