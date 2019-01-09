package com.gop.util;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;


public class HttpsUtil
{

    private static void initHttpsUrlConnection()
    {
        X509TrustManager[] managers = new X509TrustManager[] {new TrustAllCertManager()};
        try
        {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, managers, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        HostnameVerifier hv = new HostnameVerifier()
        {
            public boolean verify(String hostname, SSLSession session)
            {
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    public static String post(String url, String params, String encoding, int readTimeout)
    {
        if (null == url || "".equals(url))
        {
            return null;
        }

        if (!url.startsWith("https://"))
        {
            return null;
        }

        initHttpsUrlConnection();

        HttpsURLConnection sslConn = null;
        OutputStream os = null;
        BufferedReader br = null;
        StringBuffer sbu = new StringBuffer();
        try
        {
            URL callUrl = new URL(url);
            sslConn = (HttpsURLConnection)callUrl.openConnection();
            sslConn.setRequestMethod("POST");
            sslConn.setConnectTimeout(10000);
            sslConn.setReadTimeout(readTimeout);
            sslConn.setRequestProperty("Content-Length",
                String.valueOf(params.getBytes(encoding).length));
            sslConn.setDoInput(true);
            sslConn.setDoOutput(true);
            os = sslConn.getOutputStream();
            os.write(params.getBytes(encoding));
            os.flush();
            os.close();

            br = new BufferedReader(new InputStreamReader(sslConn.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null)
            {
                sbu.append(line);
            }
            br.close();
            return sbu.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            if (os != null)
            {
                try
                {
                    os.close();
                }
                catch (Exception e)
                {}
            }
            if (br != null)
            {
                try
                {
                    br.close();
                }
                catch (Exception e)
                {}
            }
        }
    }

    private static class TrustAllCertManager implements X509TrustManager
    {

        public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException
        {
            // nothing to do
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException
        {
            // nothing to do
        }

        public X509Certificate[] getAcceptedIssuers()
        {
            return null;
        }

    }

}
