package com.gop.util;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	public static final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(2, TimeUnit.SECONDS)
			.writeTimeout(2, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS).build();

	public static String post(String url, String msg) throws IOException {

		RequestBody body = RequestBody.create(JSON, msg);
		Request request = new Request.Builder().url(url).post(body).addHeader("Accept-Language", " zh-CN").build();
		Response response = client.newCall(request).execute();
		return response.body().string();

	}

	public static String get(String url) throws IOException {

		Request request = new Request.Builder().url(url).get().build();
		Response response = client.newCall(request).execute();
		return response.body().string();

	}

	

}
