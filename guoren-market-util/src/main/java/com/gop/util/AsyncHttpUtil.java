package com.gop.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.javatuples.Pair;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;

public class AsyncHttpUtil {

	private static CloseableHttpAsyncClient httpclient;

	public static void asyncPost(String url, String str, FutureCallback<HttpResponse> callBack)
			throws UnsupportedEncodingException {
		Preconditions.checkNotNull(str, "str can not be null");
		CloseableHttpAsyncClient httpclient = getHttpClient();
		HttpPost httpPost = new HttpPost(url);

		httpPost.setEntity(new StringEntity(str, "UTF-8"));
		httpclient.execute(httpPost, callBack);
	}

	public static String syncPost(String url, String str) {

		Preconditions.checkNotNull(str, "str can not be null");
		String result = null;
		try {
			CloseableHttpAsyncClient httpclient = getHttpClient();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new StringEntity(str,"UTF-8"));
			DefautFutureCallBack futureCallback = new DefautFutureCallBack();
			httpclient.execute(httpPost, futureCallback);
			result = futureCallback.await().get().toString();
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage());
		}
		return result;
	}

	public static void asyncpostJson(String url, JSONObject json, FutureCallback<HttpResponse> callBack)
			throws UnsupportedEncodingException {
		Preconditions.checkNotNull(json, "json can not be null");
	}

	private static class DefautFutureCallBack implements FutureCallback<HttpResponse> {
		private Pair<Boolean, Object> pair;
		private CountDownLatch latch = new CountDownLatch(1);

		@Override
		public void cancelled() {
			System.out.println("cancle");
			pair = new Pair<Boolean, Object>(false, new RuntimeException("http cancled!"));
			latch.countDown();
		}

		@Override
		public void completed(HttpResponse response) {
			try {
				String result = IOUtils.toString(response.getEntity().getContent());
				pair = new Pair<Boolean, Object>(true, result);
				latch.countDown();
			} catch (Exception e) {
				pair.setAt1(e);
			}
		}

		@Override
		public void failed(Exception expection) {

			pair = new Pair<Boolean, Object>(false, expection);
			latch.countDown();
		}

		public DefautFutureCallBack await() {
			try {
				latch.await();
			} catch (InterruptedException e) {
				pair = new Pair<Boolean, Object>(false, e);
			}
			return this;
		}

		public Object get() throws Exception {
			if (pair.getValue0().equals(true))
				return pair.getValue1();
			else
				throw (Exception) pair.getValue1();
		}

	}

	private static CloseableHttpAsyncClient getHttpClient() {
		if (null == httpclient || !httpclient.isRunning()) {
			init();
		}
		return httpclient;
	}

	private static void init() {
		if (null == httpclient) {
			httpclient = HttpAsyncClients.createMinimal();
			httpclient.start();
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				public void run() {
					try {
						System.out.println("kai");
						httpclient.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}));
		}
	}

	
}
