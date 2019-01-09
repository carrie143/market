package com.gop.util.ipaynow;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

/**
 * 报文加解密工具类
 */
public class EncryDecryUtils {


  // 对数据进行base64加密
  public static String base64Encrypt(String str) {
    if (str == null) {
      return null;
    }
    try {
      return Base64.encodeBase64String(str.getBytes("utf-8"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return null;
    }
  }


  // 对数据进行base64解密
  public static String base64Decrypt(String str) {
    if (str == null) {
      return null;
    }
    try {
      return new String(Base64.decodeBase64(str), "utf-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return null;
    }
  }


  // 把传入的数组进行base64加密
  public static String base64Encrypt(byte[] b) {
    if (b == null) {
      return null;
    }
    return Base64.encodeBase64String(b);
  }

  // 对返回的数据 先用base64解密 再用3Des解密
  public static String decryptFromBase64DES(String key, String data) {
    String result = null;
    try {
      result =
          new String(DESUtils.Union3DesDecrypt(key.getBytes(), Base64.decodeBase64(data)), "utf-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return result;
  }

  // 把数据 先用3des加密 再用base64加密
  public static String encryptFromDESBase64(String key, String data) {
    String result = null;
    byte[] b = DESUtils.Union3DesEncrypt(key.getBytes(), data.getBytes());
    result = base64Encrypt(b);
    return result;
  }


  public static String md5(String str) {
    if (str == null) {
      return null;
    }
    MessageDigest messageDigest = null;
    try {
      messageDigest = MessageDigest.getInstance("MD5");
      messageDigest.reset();
      messageDigest.update(str.getBytes("UTF-8"));
    } catch (NoSuchAlgorithmException e) {
      return str;
    } catch (UnsupportedEncodingException e) {
      return str;
    }
    byte[] byteArray = messageDigest.digest();
    StringBuffer md5StrBuff = new StringBuffer();
    int aa;

    for (int i = 0; i < byteArray.length; i++) {
      aa = byteArray[i];
      aa = aa & 0xff;
      if (Integer.toHexString(aa).length() == 1) {
        md5StrBuff.append("0").append(Integer.toHexString(aa));
      } else {
        md5StrBuff.append(Integer.toHexString(aa));
      }
    }
    return md5StrBuff.toString();
  }


}
