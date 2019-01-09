package com.gop.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DES {
    /**
     * 已知密钥的情况下加密
     */
    public static String encode(String str, String key) throws Exception {
        SecureRandom sr = new SecureRandom();
        byte[] rawKey = Base64.decode(key);

        DESKeySpec dks = new DESKeySpec(rawKey);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(dks);

        javax.crypto.Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);

        byte data[] = str.getBytes("UTF8");
        byte encryptedData[] = cipher.doFinal(data);
        return new String(Base64.encode(encryptedData));
    }
}
