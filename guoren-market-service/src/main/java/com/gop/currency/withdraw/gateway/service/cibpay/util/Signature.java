/**
 * 签名&验签类
 *
 * @author xiezz
 * @version 1.1.1
 */
package com.gop.currency.withdraw.gateway.service.cibpay.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.gop.currency.withdraw.gateway.service.cibpay.config.CIBConfig;

public class Signature {

    private static char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 生成签名MAC字符串
     * @param        参数列表（包含mac参数）
     * @return MAC字符串
     */
    public static String generateMAC(Map<String, String> params) {

        if (params.containsKey("sign_type") && params.get("sign_type").equals("RSA")) {
            try {
                PrivateKey privKey = readPrivateKey(CIBConfig.APPSVR_CLIENT_PFX, CIBConfig.APPSVR_CLIENT_PFX_PWD);
                System.out.println(privKey);
                java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");

                signature.initSign(privKey);
                signature.update(generateParamStr(params).getBytes());
                byte[] signed = signature.sign();

                // 计算base64encode(signed)，无换行。如无法使用，请自行替换为其它BASE64类库。
                @SuppressWarnings("restriction")
                String mac = new sun.misc.BASE64Encoder().encode(signed)
                        .replaceAll(System.getProperty("line.separator"), "");

                return mac;
            } catch (Exception e) {
                // 读取证书等出错
                return "SIGNATURE_RSA_CERT_ERROR";
            }
        } else {		/* 默认为SHA1算法 */
            return encryptBySHA(generateParamStr(params) + "&" + CIBConfig.CIB_MERCHANT_TOKEN);
        }
    }

    /**
     * 验证服务器返回的信息中签名的正确性
     * @param params    参数列表（包含mac参数）
     * @return true-验签通过，false-验签失败
     */
    public static boolean verifyMAC(Map<String, String> params) {

        if (!params.containsKey("mac")) return false;
        String mac = params.get("mac");
        if (params.containsKey("sign_type") && params.get("sign_type").equals("RSA")) {
            if (mac == null) return false;

            try {
                PublicKey pubKey = readPublicKey(CIBConfig.APPSVR_SERVER_CERT);
                java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");

                signature.initVerify(pubKey);
                signature.update(generateParamStr(params).getBytes());

                // 计算base64decode(mac)。如无法使用，请自行替换为其它BASE64类库。
                @SuppressWarnings("restriction")
                byte[] bmac = new sun.misc.BASE64Decoder().decodeBuffer(mac);

                return signature.verify(bmac);
            } catch (Exception e) {
                return false;
            }
        } else {		/* 默认为SHA1算法 */
            if (mac != null && mac.equals(generateMAC(params))) return true;
            else return false;
        }
    }

    /**
     * 生成用于MAC计算的参数字符串。<br>
     * @return 模式为key=value&key=value
     */
    private static String generateParamStr(Map<String, String> params) {
        // 取所有非空字段内容（除mac以外），塞入列表
        List<String> paramList = new ArrayList<String>();
        for (String key : params.keySet()) {
            if ("mac".equals(key)) {
                continue;
            }
            String val = params.get(key);
            paramList.add(key + "=" + val);
        }
        // 防护
        if (paramList.size() == 0) {
            return null;
        }
        // 对列表进行排序
        Collections.sort(paramList);
        // 以&符分割拼装成字符串
        StringBuilder sb = new StringBuilder();
        sb.append(paramList.get(0));
        for (int i = 1; i < paramList.size(); i++) {
            sb.append("&").append(paramList.get(i));
        }
        return sb.toString();
    }

    /**
     * 将byte数组转换为16进制格式的字符串
     * @param bytes 待转换数组
     * @return 16进制格式的字符串
     */
    private static String bytesToHexStr(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexChar[(bytes[i] & 0xf0) >>> 4]);
            sb.append(hexChar[bytes[i] & 0x0f]);
        }
        return sb.toString();
    }


    /**
     * SHA摘要算法，输入内容将被UTF-8编码
     * @param content 输入明文
     * @return 内容摘要，40位16进制字符串
     */
    private static String encryptBySHA(String content) {
        if (content == null) return null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] output = md.digest(content.getBytes("UTF-8"));
            return bytesToHexStr(output);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取PFX文件中的商户私钥，实际使用中可缓存该函数返回值
     * @param certPath    证书文件路径
     * @param password    密码
     * @return 私钥
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws IOException
     * @throws UnrecoverableKeyException
     */
    private static PrivateKey readPrivateKey(String certPath, String password)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
            IOException, UnrecoverableKeyException {

        KeyStore keyStore = KeyStore.getInstance("pkcs12");
        InputStream is = null;
        String preString = Signature.class.getClassLoader().getResource("").getPath();
        File file = new File(preString+certPath);
        
        
        if (file.exists()) {
            is = new FileInputStream(certPath);
        } else {
        	
        	System.out.println("文件 : "+preString+certPath + "不存在");
            Class<? extends Signature> clz = Signature.class;
            ClassLoader cl = clz.getClassLoader();

            URL url = cl.getResource(certPath);
            if (url == null) throw new FileNotFoundException();

            is = cl.getResourceAsStream(certPath);
        }
        keyStore.load(is, password == null ? null : password.toCharArray());
        if (is != null) is.close();

        return (PrivateKey) keyStore.getKey(keyStore.aliases().nextElement(), password == null ? null : password.toCharArray());
    }

    /**
     * 读取CRT文件中的银行公钥，实际使用中可缓存该函数返回值
     * @param certPath    证书文件路径
     * @return 公钥
     * @throws CertificateException
     * @throws IOException
     */
    private static PublicKey readPublicKey(String certPath)
            throws CertificateException, IOException {

        File file = new File(certPath);
        InputStream is = null;
        if (file.exists()) {
            is = new FileInputStream(certPath);
        } else {
            Class<? extends Signature> clz = Signature.class;
            ClassLoader cl = clz.getClassLoader();

            URL url = cl.getResource(certPath);
            if (url == null) throw new FileNotFoundException();

            is = cl.getResourceAsStream(certPath);
        }
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(is);
        if (is != null) is.close();
        return cert.getPublicKey();
    }

    public static Map<String, String> jsonToMap(String jsonStr) {
        Map<String, String> map = new HashMap<String, String>();
        JSONObject json = JSONObject.parseObject(jsonStr);
        Iterator<String> keys = json.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String value = json.get(key).toString();
            map.put(key, value);
        }
        return map;
    }
}
