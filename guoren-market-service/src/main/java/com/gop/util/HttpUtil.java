package com.gop.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HttpUtil
{
	
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	public static final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(2, TimeUnit.SECONDS)
			.writeTimeout(2, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS).build();

    public static String post(String url, String params, String encoding, int readTimeout)
    {

        System.out.println("url:" + url);
        if (null == url || "".equals(url))
        {
            return null;
        }

        if (!url.startsWith("http://"))
        {
            return null;
        }

        HttpURLConnection conn = null;
        OutputStream os = null;
        BufferedReader br = null;
        StringBuffer sbu = new StringBuffer();
        try
        {
            URL callUrl = new URL(url);
            conn = (HttpURLConnection)callUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(readTimeout);
            conn.setRequestProperty("Content-Length",
                String.valueOf(params.getBytes(encoding).length));
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            os = conn.getOutputStream();
            os.write(params.getBytes());
            os.flush();
            os.close();

            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
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
                {

                }
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

//	public static String get(String url) throws IOException {
//
//		Request request = new Request.Builder().url(url).get().build();
//		Response response = client.newCall(request).execute();
//		return response.body().string();
//
//	}

    
}
