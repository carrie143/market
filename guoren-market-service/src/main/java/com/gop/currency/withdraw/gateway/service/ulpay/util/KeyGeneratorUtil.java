package com.gop.currency.withdraw.gateway.service.ulpay.util;

import java.util.Random;

import com.gop.util.DateTimeUtil;

public class KeyGeneratorUtil {
	
	public synchronized static String generatorReqSn(){
		Random r = new Random();
		String uniq = "000000"+r.nextInt(999999);
		uniq = uniq.substring(uniq.length()-6);
		return DateTimeUtil.getDateTime()+uniq;
	}	

}
