package com.gop.authentication.service.impl;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gop.authentication.service.UserResourceManagerService;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service("ImageQiniuServiceImpl")
public class ImageQiniuServiceImpl implements UserResourceManagerService {
	// @Value(value="${accessKey}")
	private String accessKey = "svjcwQGlJnd3rGXimvW5YZ6aiihnGqdWJxxk90tD";
	// @Value(value="${secretKey}")
	private String secretKey = "3zZSGwGCPlFRxOdvW2eWCMAo-tq9fiwT2lnVJOe_";
	// @Value(value="${Zone.zone1()}")
	private Zone zone = Zone.zone1();
	// @Value(value="${privateBucket}")
	private String privateBucket = "qiniu-image-upload-private-1";
	// @Value(value="${publicBucket}")
	private String publicBucket = "qiniu-image-upload-public-1";
	// @Value(value="${publicDomainOfBucket}")
	private String publicDomainOfBucket = "ov65fan4t.bkt.clouddn.com";
	// @Value(value="${privateDomainOfBucket}")
	private String privateDomainOfBucket = "ov8091njh.bkt.clouddn.com";

	@Autowired
	@Qualifier("MongoManagerServiceImpl")
	private UserResourceManagerService MongoManagerService;
	
	
	

	@Override
	public String getResourcesWithPublic(String hash) {

		// 从七牛拿公共图片然后返回base64
		try {
			String fileName = hash;
			String domainOfBucket = publicDomainOfBucket;
			String finalUrl = null;
			InputStream in = null;
			String encodedFileName = URLEncoder.encode(fileName, "utf-8");
			finalUrl ="http://" +  String.format("%s/%s", domainOfBucket, encodedFileName);

			/* 将网络资源地址传给,即赋值给url */

			URL url = new URL(finalUrl);
			/* 此为联系获得网络资源的固定格式用法，以便后面的in变量获得url截取网络资源的输入流 */
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			in = connection.getInputStream();
			// 将inputstream转换成base64
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int len = -1;
			while ((len = in.read(buffer)) != -1) {
				swapStream.write(buffer, 0, len);
			}
			return Base64.encodeBase64String(swapStream.toString().getBytes());
		} catch (Exception e) {
			// TODO: handle exception
		}
		// 如果七牛没有图片的话,从mongoDB拿图片
		return MongoManagerService.getResourcesWithPublic(hash);
	}

	@Override
	public String getResourcesWithPrivate(String hash) {

		try {
			InputStream in = null;
			String finalUrl = null;
			String fileName = hash;
			String domainOfBucket = privateDomainOfBucket;
			String encodedFileName = URLEncoder.encode(fileName, "utf-8");
			String publicUrl ="http://" +  String.format("%s/%s", domainOfBucket, encodedFileName);
			// String accessKey = accessKey;
			// String secretKey = secretKey;
			Auth auth = Auth.create(accessKey, secretKey);
			long expireInSeconds = 3600;// 1小时，可以自定义链接过期时间
			finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
			/* 将网络资源地址传给,即赋值给url */
			URL url = new URL(finalUrl);
			/* 此为联系获得网络资源的固定格式用法，以便后面的in变量获得url截取网络资源的输入流 */
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			in = connection.getInputStream();
			// 将inputstream转换成base64
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = in.read(buffer)) != -1) {
				swapStream.write(buffer, 0, len);
			}
			return Base64.encodeBase64String(swapStream.toString().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 如果七牛没有图片的话,从mongoDB拿图片
		// 注意:此处mongoDB的内部实现是调用的public公共存储的方法
		return MongoManagerService.getResourcesWithPrivate(hash);
	}

	@Override
	public String saveResourcesWithPublic(InputStream inputStream) {
		// 存储到七牛公共存储
		// 构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(zone);
		// ...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		// 默认不指定key的情况下，以文件内容的hash值作为文件名,如果上传key则会以key值来作为文件名
		String key = null;

		// byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
		// ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(publicBucket);
		try {
			Response response = uploadManager.put(inputStream, key, upToken, null, null);
			// 解析上传成功的结果
			// DefaultPutRet putRet = new Gson().fromJson(response.bodyString(),
			// DefaultPutRet.class);
			DefaultPutRet putRet = JSONObject.parseObject(response.bodyString(), DefaultPutRet.class);
			// JSONObject json = new JSONObject();
			// 返回字符串为七牛存储后文件名,即图片的hash
			return putRet.hash;
		} catch (QiniuException ex) {
			Response r = ex.response;
			System.err.println(r.toString());
			try {
				System.err.println(r.bodyString());
			} catch (QiniuException ex2) {
				// ignore
			}
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public String saveResourcesWithPrivate(InputStream inputStream) {
		// 构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(zone); // ...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		// 默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = null;
		// byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8"); //
		// ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(privateBucket);
		try {
			Response response = uploadManager.put(inputStream, key, upToken, null, null);
			// 解析上传成功的结果
			// DefaultPutRet putRet = new Gson().fromJson(response.bodyString(),
			// DefaultPutRet.class);
			DefaultPutRet putRet = JSONObject.parseObject(response.bodyString(), DefaultPutRet.class);
			// JSONObject json = new JSONObject();
			// 返回字符串为七牛存储后文件名,即图片的hash
			return putRet.hash;
		} catch (QiniuException ex) {
			Response r = ex.response;
			System.err.println(r.toString());
			try {
				System.err.println(r.bodyString());

			} catch (QiniuException ex2) {
				// ignore } } return null;
			}
		}
		return null;

	}

	@Override
	public InputStream getResourcesWithPublicStream(String hash) {
		// 从七牛拿公共图片然后返回base64
		try {
			String fileName = hash;
			String domainOfBucket = publicDomainOfBucket;
			String finalUrl = null;
			InputStream in = null;
			String encodedFileName = URLEncoder.encode(fileName, "utf-8");
			finalUrl ="http://" +  String.format("%s/%s", domainOfBucket, encodedFileName);

			/* 将网络资源地址传给,即赋值给url */

			URL url = new URL(finalUrl);
			/* 此为联系获得网络资源的固定格式用法，以便后面的in变量获得url截取网络资源的输入流 */
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			in = connection.getInputStream();
			// 将inputstream转换成base64
			// ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			// byte[] buffer = new byte[4096];
			// int len = -1;
			// while ((len = in.read(buffer)) != -1) {
			// swapStream.write(buffer, 0, len);
			// }
			// return Base64.encodeBase64String(swapStream.toString().getBytes());
			// 返回流
			return in;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		// 如果七牛没有图片的话,从mongoDB拿图片
		return MongoManagerService.getResourcesWithPublicStream(hash);
	}

	@Override
	public InputStream getResourcesWithPrivateStream(String hash) {
		try {
			InputStream in = null;
			String finalUrl = null;
			String fileName = hash;
			String domainOfBucket = privateDomainOfBucket;
			String encodedFileName = URLEncoder.encode(fileName, "utf-8");
			String publicUrl = "http://" + String.format("%s/%s", domainOfBucket, encodedFileName);
			// String accessKey = accessKey;
			// String secretKey = secretKey;
			Auth auth = Auth.create(accessKey, secretKey);
			long expireInSeconds = 3600;// 1小时，可以自定义链接过期时间
			finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
			/* 将网络资源地址传给,即赋值给url */
			if (finalUrl == null) {
				return null;
				// throw new RuntimeException("finalurl异常");
			}
			URL url = new URL(finalUrl);
			/* 此为联系获得网络资源的固定格式用法，以便后面的in变量获得url截取网络资源的输入流 */
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				in = connection.getInputStream();
			// 将inputstream转换成base64
			// ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			// byte[] buffer = new byte[4096];
			// int len = -1;
			// while ((len = in.read(buffer)) != -1) {
			// swapStream.write(buffer, 0, len);
			// }
			// return Base64.encodeBase64String(swapStream.toString().getBytes());
			return in;
		} catch (Exception e) {
			log.error("读取七牛发生异常",e);
		}
		// 如果七牛没有图片的话,从mongoDB拿图片
		// 注意:此处mongoDB的内部实现是调用的public公共存储的方法
		return MongoManagerService.getResourcesWithPrivateStream(hash);
	}

	// 测试代码
//	public static void main(String[] args) {
//		ImageQiniuServiceImpl impl = new ImageQiniuServiceImpl();
//		InputStream inStream = impl.getResourcesWithPrivateStream("Fg1AYqbTCv2Fc3TnzS0aSP1231jt");
//
//		if (inStream != null) {
//			System.out.println("有返回");
//		}
//		byte[] data = new byte[1024];
//		int len = 0;
//		FileOutputStream fileOutputStream = null;
//		try {
//			fileOutputStream = new FileOutputStream("/Users/apple/Desktop/test.png");
//			while ((len = inStream.read(data)) != -1) {
//				fileOutputStream.write(data, 0, len);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

}

//// 测试方法 // public static void main(String[] args) throws Exception { //
// ImageQiniuServiceImpl impl = new ImageQiniuServiceImpl(); // FileInputStream
// stream = new FileInputStream(new File("/Users/apple/Desktop", "123.png")); //
// int uid = 987; // // String string = impl.saveImage(stream, null, uid, null);
//// stream.close(); // System.out.println("test-->"+string); // // }
//
// @Override public String saveImage(InputStream stream, String fileName, int
// uid, String contentType) { // 构造一个带指定Zone对象的配置类 Configuration cfg = new
// Configuration(zone); // ...其他参数参考类注释 UploadManager uploadManager = new
// UploadManager(cfg); // 默认不指定key的情况下，以文件内容的hash值作为文件名,如果上传key则会以key值来作为文件名
// String key = null;
//
//// byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8"); //
// ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
// Auth auth = Auth.create(accessKey, secretKey); String upToken =
// auth.uploadToken(publicBucket); try { Response response =
// uploadManager.put(stream, key, upToken, null, null); // 解析上传成功的结果
//
//// DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), //
// DefaultPutRet.class); DefaultPutRet putRet =
// JSONObject.parseObject(response.bodyString(), DefaultPutRet.class); //
// System.out.println("key-->"+putRet.key); //
// System.out.println("hash-->"+putRet.hash);
//
//// JSONObject json = new JSONObject(); return putRet.hash;
//
// } catch (QiniuException ex) { Response r = ex.response;
// System.err.println(r.toString()); try { System.err.println(r.bodyString()); }
// catch (QiniuException ex2) { // ignore } }finally { if (stream != null) { try
// { stream.close(); } catch (Exception e) { e.printStackTrace(); } } } return
// null;
//
// }
//
// @Override public String getPath(String name, Integer userId) { // TODO
// Auto-generated method stub return null; }
//
// @Override public GridFSDBFile getImageGrid(String name) { // TODO
// Auto-generated method stub return null; }
//
// @Override public GridFSDBFile getImageGridandUid(int uid, String name) { //
// TODO Auto-generated method stub return null; }
//
// @Override public String saveImageToPrivate(InputStream stream, String
// fileName, int uid, String contentType) { // 构造一个带指定Zone对象的配置类 Configuration
// cfg = new Configuration(zone); // ...其他参数参考类注释 UploadManager uploadManager =
// new UploadManager(cfg); // 默认不指定key的情况下，以文件内容的hash值作为文件名 String key = null;
//
//// byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8"); //
// ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
// Auth auth = Auth.create(accessKey, secretKey); String upToken =
// auth.uploadToken(privateBucket); try { Response response =
// uploadManager.put(stream, key, upToken, null, null); // 解析上传成功的结果
//
//// DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), //
// DefaultPutRet.class); DefaultPutRet putRet =
// JSONObject.parseObject(response.bodyString(), DefaultPutRet.class);
// System.out.println("key-->"+putRet.key);
// System.out.println("hash-->"+putRet.hash);
//
//
//// JSONObject json = new JSONObject(); return putRet.hash;
//
// } catch (QiniuException ex) { Response r = ex.response;
// System.err.println(r.toString()); try { System.err.println(r.bodyString()); }
// catch (QiniuException ex2) { // ignore } } return null; }
