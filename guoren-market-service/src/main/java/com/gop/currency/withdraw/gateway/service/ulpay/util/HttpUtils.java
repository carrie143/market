package com.gop.currency.withdraw.gateway.service.ulpay.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import javax.net.ssl.HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 2015/6/1.
 */
public class HttpUtils {
	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	public static final int CONNECTION_TIMEOUT = 15000;
	public static final int SOCKET_TIMEOUT = 60000;

	public static File getFileAndCreateDir(String fileFullPath) {
		File file = new File(fileFullPath);
		if (file.exists()) {
			logger.error("文件已经存在，文件名{}", fileFullPath);
			return null;
		}
		if (fileFullPath.endsWith(File.separator)) {
			logger.error("文件名错误，{}", fileFullPath);
			return null;
		}
		// 判断目标文件所在的目录是否存在
		if (!file.getParentFile().exists()) {
			// 如果目标文件所在的目录不存在，则创建父目录

			if (!file.getParentFile().mkdirs()) {
				logger.error("创建文件目录失败，文件名{}", fileFullPath);
				return null;
			}
		}
		return file;
	}

	public static boolean downloadFile(String url, String filePath) {
		boolean ret = false;
		File file = getFileAndCreateDir(filePath);
		if (file == null) {
			// 创建路径错误或文件已经存在
			logger.error("下载文件时，文件名错误或路径错误，{}", filePath);
			return ret;
		}
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse httpResponse = httpclient.execute(httpGet);

			StatusLine statusLine = httpResponse.getStatusLine();
			if (statusLine.getStatusCode() == 200) {

				FileOutputStream outputStream = new FileOutputStream(file);
				InputStream inputStream = httpResponse.getEntity().getContent();
				byte b[] = new byte[1024 * 1024];
				int j = 0;
				while ((j = inputStream.read(b)) != -1) {
					outputStream.write(b, 0, j);
				}
				outputStream.flush();
				outputStream.close();
				ret = true;
			}
		} catch (ClientProtocolException e) {
			logger.error("HttpClient ClientProtocolException异常", e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("HttpClient IO异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("HttpClient 异常", e);
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error("关闭httpclient连接异常", e);
				e.printStackTrace();
			}
		}
		return ret;
	}

	/**
	 * 根据指定URL外发POST请求
	 *
	 * @param obj
	 *            外发对象
	 * @param url
	 *            外发地址
	 * @param encoding
	 *            编码
	 * @param isBlank
	 *            是否允许为空值
	 * @return 返回字符串
	 * @throws Exception
	 */
	public static String sendPostMessage(Object obj, String url, String encoding, boolean isBlank) throws Exception {
		List<NameValuePair> formParams = StaticFunctions.getListNamValPar(obj, isBlank);

		UrlEncodedFormEntity uefEntity = null;
		try {
			uefEntity = new UrlEncodedFormEntity(formParams, encoding);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}

		return sendPostRequest(uefEntity, url, encoding);
	}

	/**
	 * 根据指定URL外发POST请求，入参为字符串
	 *
	 * @param keyValStr
	 *            外发字符串
	 * @param url
	 *            外发地址
	 * @param encoding
	 *            编码
	 * @return 返回字符串
	 * @throws Exception
	 */
	public static String sendPostMessage(String keyValStr, String url, String encoding) throws Exception {
		StringEntity strEntity = new StringEntity(keyValStr, encoding);
		return sendPostRequest(strEntity, url, encoding);
	}
	
	public static String sendGetMessage(String url, String encoding) throws Exception {
        return sendGetRequest(url, encoding);

    }

	/**
	 * @Title: createSSLInsecureClient
	 * @Description: 创建发送https请求
	 * @return
	 * @throws GeneralSecurityException
	 *             设定文件
	 */
	protected static CloseableHttpClient createSSLInsecureClient() throws GeneralSecurityException {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws java.security.cert.CertificateException {
					return true;
				}
			}).build();

			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier() {

				@Override
				public boolean verify(String arg0, javax.net.ssl.SSLSession arg1) {
					return true;
				}

			});
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (GeneralSecurityException e) {
			logger.error("", e);
			throw e;
		}
	}

	/**
	 * 外发POST请求
	 *
	 * @param httpEntity
	 *            外发对象
	 * @param url
	 *            外发地址
	 * @param encoding
	 *            编码
	 * @return 返回字符串
	 * @throws Exception
	 */
	private static String sendPostRequest(HttpEntity httpEntity, String url, String encoding) throws Exception {
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = null;
		if (url.startsWith("https")) {
			// 执行 Https 请求.
			try {
				httpclient = createSSLInsecureClient();
			} catch (GeneralSecurityException e) {
				logger.error("createSSLInsecureClient Error ==> ", e);
				throw e;
			}
		} else {
			httpclient = HttpClients.createDefault();
		}
		logger.info("HttpClient方法创建！");
		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		logger.info("Post方法创建！");
		String resMes = null;
		try {

			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT)
					.setConnectTimeout(CONNECTION_TIMEOUT).build();
			httppost.setConfig(requestConfig);
			httppost.setEntity(httpEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				logger.info("StatusLine ==> {}", response.getStatusLine());
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					resMes = EntityUtils.toString(entity, encoding);
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		} catch (SocketTimeoutException e) {
			// 捕获服务器响应超时
			logger.error(e.getMessage(), e);
			throw e;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error("Close HttpClient Error==> ", e);
			}
		}

		return resMes;
	}
	
	
	/**
     * 外发Get请求
     *
     * @param url
     *            外发地址
     * @param encoding
     *            编码
     * @return 返回字符串
     * @throws Exception
     */
    private static String sendGetRequest(String url, String encoding) throws Exception {
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = null;
        if (url.startsWith("https")) {
            // 执行 Https 请求.
            try {
                httpclient = createSSLInsecureClient();
            } catch (GeneralSecurityException e) {
                logger.error("createSSLInsecureClient Error ==> ", e);
                throw e;
            }
        } else {
            httpclient = HttpClients.createDefault();
        }
        logger.info("HttpClient方法创建！");
        // 创建httppost
        HttpGet httppost = new HttpGet(url);
        logger.info("GET方法创建！");
        String resMes = null;
        try {

            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT)
                    .setConnectTimeout(CONNECTION_TIMEOUT).build();
            httppost.setConfig(requestConfig);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                logger.info("StatusLine ==> {}", response.getStatusLine());
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    resMes = EntityUtils.toString(entity, encoding);
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        } catch (SocketTimeoutException e) {
            // 捕获服务器响应超时
            logger.error(e.getMessage(), e);
            throw e;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("Close HttpClient Error==> ", e);
            }
        }

        return resMes;
    }
}
