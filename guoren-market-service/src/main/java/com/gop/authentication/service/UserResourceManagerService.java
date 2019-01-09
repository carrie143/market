package com.gop.authentication.service;

import java.io.InputStream;

public interface UserResourceManagerService {
	/**
	 * 用户获得base64格式的图片
	 * 七牛的public为头像
	 * @param name
	 * @return
	 */
	public String getResourcesWithPublic(String hash);
	/**
	 * 用户获得base64格式的图片
	 * 七牛的private为身份证或者其他隐私照片
	 * @param hash
	 * @return
	 */
	public String getResourcesWithPrivate(String hash);


	/**
	 * 存储用户头像到七牛public
	 * @param inputStream
	 * @return 返回hash
	 */
	public String saveResourcesWithPublic(InputStream inputStream);
	
	/**
	 * 存储身份证或者其他隐私照片到七牛存储
	 * @param inputStream
	 * @return
	 */
	public String saveResourcesWithPrivate(InputStream inputStream);
	
	/**
	 * 用户获得图片流(非base64)
	 * 七牛的public为头像
	 * @return
	 */
	public InputStream getResourcesWithPublicStream(String hash);
	/**
	 * 用户获得图片流(非base64)
	 * 七牛的private为身份证或者其他隐私照片
	 * @param hash
	 * @return
	 */
	public InputStream getResourcesWithPrivateStream(String hash);

}
