package com.gop.authentication.service.impl;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import com.gop.authentication.service.ImageService;
import com.gop.code.consts.SecurityCodeConst;
import com.gop.exception.AppException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

@Service("imageService")
public class ImageServiceImpl implements ImageService {

	@Autowired
	private GridFsTemplate gridFsTemplate;
	

	/**
	 * 保存图片
	 * 
	 * @param file
	 */
	@Override
	public String saveImage(InputStream stream, String fileName, int uid, String contentType) {

		DBObject metaData = new BasicDBObject();
		metaData.put("id", uid);

		Query query = new Query();
		query.addCriteria(Criteria.where("metadata.id").is(uid));
		query.limit(20);
		List list = gridFsTemplate.find(query);
		if (list != null && list.size() >= 20) {
			throw new AppException(SecurityCodeConst.UPLOAD_TOO_MANY);
		}
		GridFSFile gridFSFile = null;

		gridFSFile = gridFsTemplate.store(stream, fileName, contentType, metaData);

		return gridFSFile.getMD5();
	}

	@Override
	@Deprecated
	public String getPath(String name, Integer userId) {
		return "";
	}

	@Override
	public GridFSDBFile getImageGrid(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("md5").is(name));
		GridFSDBFile gridFSFile = gridFsTemplate.findOne(query);
		return gridFSFile;
	}

	@Override
	public GridFSDBFile getImageGridandUid(int uid, String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("md5").is(name));
		query.addCriteria(Criteria.where("metadata.id").is(uid));
		GridFSDBFile gridFSFile = gridFsTemplate.findOne(query);
		return gridFSFile;
	}

	@Override
	public String saveImageToPrivate(InputStream stream, String fileName, int uid, String contentType) {
		// TODO Auto-generated method stub
		return null;
	}

}
