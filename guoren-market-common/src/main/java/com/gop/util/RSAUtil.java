package com.gop.util;


import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSONObject;
import com.gop.exception.RsaException;


public class RSAUtil
{
    public static final String KEY_ALGORTHM = "RSA";//

    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    public static final String PUBLIC_KEY = "RSAPublicKey";// 公钥

    public static final String PRIVATE_KEY = "RSAPrivateKey";// 私钥

    /**
     * 取得公钥，并转化为String类型
     * 
     * @param keyMap
     * @return
     * @throws Exception
     */

    private static Map<String, String> CreateKey()
        throws Exception
    {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORTHM);
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        Map<String, String> keyMap = new HashMap<String, String>(2);
        keyMap.put(PUBLIC_KEY, Base64.encodeBase64String(publicKey.getEncoded()));
        keyMap.put(PRIVATE_KEY, Base64.encodeBase64String(privateKey.getEncoded()));
        return keyMap;
    }

    public static String addSignRSA(JSONObject reqObj, String rsa_private)
    {
        if (reqObj == null)
        {
            return "";
        }
        // 生成待签名串
        String sign_src = genSignData(reqObj);
        try
        {
            return sign(rsa_private, sign_src);
        }
        catch (Exception e)
        {
            return "";
        }
    }

    public static String sign(String prikeyvalue, String sign_str)
    {
        try
        {
            /*
             * PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
             * Base64.getBytesBASE64(prikeyvalue));
             */
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                Base64.decodeBase64(prikeyvalue));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey myprikey = keyf.generatePrivate(priPKCS8);
            // 用私钥对信息生成数字签名
            java.security.Signature signet = java.security.Signature.getInstance("MD5withRSA");
            signet.initSign(myprikey);
            signet.update(sign_str.getBytes("UTF-8"));
            byte[] signed = signet.sign(); // 对信息的数字签名
            return new String(Base64.encodeBase64(signed));
        }
        catch (java.lang.Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String genSignData(JSONObject jsonObject)
    {
        StringBuffer content = new StringBuffer();

        // 按照key做首字母升序排列
        List<String> keys = new ArrayList<String>(jsonObject.keySet());
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++ )
        {
            String key = (String)keys.get(i);
            if ("sign".equals(key))
            {
                continue;
            }
            String value = jsonObject.getString(key);
            // 空串不参与签名
            if (isnull(value))
            {
                continue;
            }
            content.append((i == 0 ? "" : "&") + key + "=" + value);

        }
        String signSrc = content.toString();
        if (signSrc.startsWith("&"))
        {
            signSrc = signSrc.replaceFirst("&", "");
        }
        return signSrc;
    }

    public static boolean isnull(String str)
    {
        if (null == str || str.equalsIgnoreCase("null") || str.equals(""))
        {
            return true;
        }
        else
            return false;
    }

    /**
     * 用私钥加密
     * 
     * @param data
     *            加密数据
     * @param key
     *            密钥
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(byte[] data, String key)
        throws Exception
    {
        // 解密密钥
        byte[] keyBytes = Base64.decodeBase64(key);
        // 取私钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return Base64.encodeBase64String(cipher.doFinal(data));
    }

    /**
     * 用私钥解密
     * 
     * @param data
     *            加密数据
     * @param key
     *            密钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key)
        throws Exception
    {
        // 对私钥解密
        byte[] keyBytes = Base64.decodeBase64(key);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 用公钥加密
     * 
     * @param data
     *            加密数据
     * @param key
     *            密钥
     * @return
     * @throws RsaException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws Exception
     */
    public static String encryptByPublicKey(byte[] data, String key)
        throws RsaException
    {
        // 对公钥解密
        byte[] keyBytes = Base64.decodeBase64(key);
        String sgin = "";
        try
        {
            // 取公钥
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
            Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

            // 对数据解密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            sgin = Base64.encodeBase64String(cipher.doFinal(data));
        }
        catch (Exception e)
        {

            throw new RsaException("encrypt error");
        }
        return sgin;
    }

    /**
     * 用公钥解密
     * 
     * @param data
     *            加密数据
     * @param key
     *            密钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(String data, String key)
        throws Exception
    {
        // 对私钥解密
        byte[] keyBytes = Base64.decodeBase64(key);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(Base64.decodeBase64(data));

    }

    /**
     * 用私钥对信息生成数字签名
     * 
     * @param data
     *            //加密数据
     * @param privateKey
     *            //私钥
     * @return
     * @throws RsaException
     * @throws Exception
     */
    public static String signJson(String data, String privateKey)
        throws RsaException
    {
        String sgin = "";
        // 解密私钥
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        try
        {
            // 指定加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
            // 取私钥匙对象
            PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            // 用私钥对信息生成数字签名
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(privateKey2);
            signature.update(data.getBytes());
            sgin = Base64.encodeBase64String(signature.sign());
        }
        catch (Exception e)
        {
            throw new RsaException("sgin error");
        }
        return sgin;
    }

    /**
     * 校验数字签名
     * 
     * @param data
     *            加密数据
     * @param publicKey
     *            公钥
     * @param sign
     *            数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(String data, String publicKey, String sign)
        throws Exception
    {
        // 解密公钥
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        // 指定加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        // 取公钥匙对象
        PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey2);
        signature.update(data.getBytes("UTF-8"));
        // 验证签名是否正常
        return signature.verify(Base64.decodeBase64(sign));

    }

}
