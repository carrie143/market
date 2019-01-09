package com.gop.utils;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil2
{
	
    public static String post(String url, String params, String encoding, int readTimeout)
    {

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
            conn.setRequestProperty("Charset", encoding);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            os = conn.getOutputStream();
            os.write(params.getBytes());
            os.flush();
            os.close();

            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
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
 

}
