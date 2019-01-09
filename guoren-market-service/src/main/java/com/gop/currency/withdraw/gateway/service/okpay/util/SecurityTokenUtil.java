package com.gop.currency.withdraw.gateway.service.okpay.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SecurityTokenUtil {

	public static String getSecurityToken(String pwd) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd:HH");
		Date date = new Date(System.currentTimeMillis() - 8 * 60 * 60 * 1000);

		String data = pwd + ":" + df.format(date);
		System.out.println(data);
		return Encrypt(data);

	}

	public static String Encrypt(String strSrc) {
		MessageDigest md = null;
		String strDes = null;
		String encName = "SHA-256";
		byte[] bt = strSrc.getBytes();
		try {
			md = MessageDigest.getInstance(encName);
			md.update(bt);
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		return strDes;
	}

	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}
}
