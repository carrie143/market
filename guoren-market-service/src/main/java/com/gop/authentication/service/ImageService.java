package com.gop.authentication.service;

import java.io.InputStream;

import com.mongodb.gridfs.GridFSDBFile;

public interface ImageService {

	/**
	 * 保存图片
	 * 
	 * @param file
	 * @param userId
	 */
	public String saveImage(InputStream stream, String fileName, int uid, String contentType);
	
	/**
	 * 上传图片到私有空间
	 * @param stream
	 * @param fileName
	 * @param uid
	 * @param contentType
	 * @return
	 */
	public String saveImageToPrivate(InputStream stream, String fileName, int uid, String contentType);

	@Deprecated
	public String getPath(String name, Integer userId);

	public GridFSDBFile getImageGrid(String name);

	public GridFSDBFile getImageGridandUid(int uid, String name);

}
