package com.gop.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.RandomStringUtils;

import com.google.common.base.Joiner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderUtil {

	private static final Map<String, AtomicInteger> RANDOM_CODE = new ConcurrentHashMap<String, AtomicInteger>();

	private static final String UNIQUE_CODE = getuniqueCode();
	
	//c2c生成序列位数
	public static final int  C2C_CODE_DIGIT = 15;

	/**
	 * 4位服务码 0000 - 转账服务 0001 - 买卖服务 0002 - 支付服务 -
	 * C2C服务0004
	 */
	public static String TRANSFER_SERVICE = "0000";
	
	public static String ORDER_SERVICE = "0001";
	
	public static String PAY_SERVICE = "0002";
	
	public static String C2C_SERVICE = "0004";

	/**
	 * 4位业务码 1001 - 果仁转出 1002 - 人民币转出 1003 - 快速提现 2001 - 果仁转入 2002 - 人民币转入 3001
	 * - 果仁买入委托 3002 - 果仁卖出委托 4001 - 果仁买入成交 4002 - 果仁卖出成交
	 * c2c售出广告 5001 c2c买入广告5002 c2c交易单 5003
	 */
	public static String TRANSFER_OUT_COIN = "1001";

	public static String TRANSFER_OUT_CURRENCY = "1002";

	public static String TRANSFER_IN_COIN = "2001";
	
	public static String TRANSFER_IN_CURRENCY = "2002";

	public static String MATCH_ORDER = "3001";

	public static String TRADE_CURRENCY_BUY = "4001";
	
	public static String TRADE_CURRENCY_SELL = "4002";
	
	public static String C2C_ADVERT_SELL = "5001";
	
	public static String C2C_ADVERT_BUY = "5002";
	
	public static String C2C_TRANS_ORDER = "5003";
	
	/**
	 * 2位角色码 00 - 商户/企业用户/机构用户 01 - 普通个人用户
	 */
	public static String BUSINESS = "00";
	
	public static String INDIVIDUAL = "01";

	/**
	 * 20160722 + 0000 + 1001 + xxxx + 00 + 130911 + yyyy 普通用户果仁转帐订单（转出）
	 * 
	 * @param serviceNumber
	 *            4位服务码
	 * @param moduleNumber
	 *            4位业务码
	 * @param role
	 *            2位角色码
	 * @return
	 */
//	public static String generateCode(String serviceNumber, String moduleNumber, String role) {
//		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
//		String seconds = new SimpleDateFormat("HHmmss").format(new Date());
//		return Joiner.on("").join(date, serviceNumber, moduleNumber, UNIQUE_CODE, role, seconds, getCode(moduleNumber));
//	}

	// public static UserType getUserType(String orderNo) {
	// String UserTypeCode = orderNo.substring(20, 22);
	// switch (UserTypeCode) {
	// case "01":
	// return UserType.INDIVIDUAL;
	// case "00":
	//
	// return UserType.BUSINESS;
	//
	// default:
	//
	// throw new IllegalArgumentException("用户不存在");
	// }
	//
	// }
	
	public static String generateCode(String serviceNumber, String moduleNumber) {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String seconds = new SimpleDateFormat("HHmmss").format(new Date());
		return Joiner.on("").join(date, serviceNumber, moduleNumber, UNIQUE_CODE, seconds, getCode(moduleNumber));
	}

	private static Integer getCode(String businessCode) {
		AtomicInteger code = RANDOM_CODE.get(businessCode);
		if (null == code || code.get() == 9999) {
			code = new AtomicInteger(1000);
		}
		Integer resultCode = code.getAndIncrement();
		RANDOM_CODE.put(businessCode, code);
		return resultCode;
	}


	public static String getuniqueCode() {

		String ipCode = null;

		String ipNumber = IpUtil.getIp().replace(".", "");
		if (null == ipNumber) {
			String randomNumeric = RandomStringUtils.randomNumeric(4);
			log.error("获取ip地址失败随机一个字符:" + randomNumeric);
			return randomNumeric;
		}
		ipCode = ipNumber.substring(ipNumber.length() - 4, ipNumber.length());
		log.info("获取到本机ip地址后四位是：" + ipCode);

		return ipCode;

	}
	
	public static String generateC2cCode() {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String seconds = new SimpleDateFormat("HHmmssSSS").format(new Date());
		String c2cCode = Joiner.on("").join(date,seconds);
		return c2cCode.substring(c2cCode.length()-OrderUtil.C2C_CODE_DIGIT);
		
	} 
//	public static void main(String[] args) {
//		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
//		String seconds = new SimpleDateFormat("HHmmssSSS").format(new Date());
//		String c2cCode = Joiner.on("").join(date,seconds);
//		System.out.println(c2cCode.substring(c2cCode.length()-OrderUtil.C2C_CODE_DIGIT));
//	}

}
