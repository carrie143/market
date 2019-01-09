/**
 * 使用URLConnection的通讯器
 *
 * @author xiezz
 * @version 1.1.2
 */
package com.gop.currency.withdraw.gateway.service.cibpay.comm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gop.currency.withdraw.gateway.service.cibpay.config.CIBConfig;


public class HttpsPostRequest extends IRequestService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static int BUFFER_SIZE = 2048;

    public Object sendPost(String url, Map<String, String> params) throws KeyManagementException, NoSuchAlgorithmException, IOException {

        //拼装参数Map为String，含URLEncode
        StringBuffer paramstr = new StringBuffer();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            paramstr.append(entry.getKey())
                    .append("=")
                    .append(URLEncoder.encode(entry.getValue(), "UTF-8"))
                    .append("&");
        }
        String param = paramstr.toString();
        logger.info(" 调用接口  ： " + url);
        logger.info(" 调用参数  ： " + paramstr);
        return this.sendPost(url, param.substring(0, param.length() - 1));
    }

    public Object sendPost(String url, String params) throws IOException, KeyManagementException, NoSuchAlgorithmException {

        URL connurl = new URL(url);
        HttpURLConnection conn = null;
        Object ret = null;
        OutputStream outStream = null;
        InputStream inStream = null;
        
        try {
            conn = (HttpURLConnection) connurl.openConnection();

            //测试环境忽略SSL证书验证
            if (CIBConfig.DEV_ENV && connurl.getProtocol().equals("https")) {
                ignoreSSLVerify((HttpsURLConnection) conn);
            }

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            outStream = conn.getOutputStream();
            outStream.write(params.getBytes("UTF-8"));
            outStream.flush();

            inStream = conn.getInputStream();
            byte[] bin = readInputStream(inStream);

            if ("application/octet-stream".equals(conn.getContentType())) {
                ret = bin;
            } else {
                ret = new String(bin, "UTF-8");
            }
        } finally {
            if (inStream != null) inStream.close();
            if (outStream != null) outStream.close();
            if (conn != null) conn.disconnect();
        }
        return ret;
    }

    private static byte[] readInputStream(InputStream inStream) throws IOException {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = null;
        byte[] buffer = new byte[BUFFER_SIZE];
        int len = 0;
        try {
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            data = outStream.toByteArray();
        } finally {
            if (outStream != null)
                outStream.close();
        }
        return data;
    }

    private static void ignoreSSLVerify(HttpsURLConnection conn) throws NoSuchAlgorithmException, KeyManagementException {

        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
            }
        }};

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());

        conn.setSSLSocketFactory(sc.getSocketFactory());

        conn.setHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
    }
}
