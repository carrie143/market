package com.gop.authentication.service.impl;

import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import com.gop.authentication.service.UserResourceManagerService;
import com.gop.code.consts.CommonCodeConst;
import com.gop.exception.AppException;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

@Service("MongoManagerServiceImpl")
public class MongoManagerServiceImpl implements UserResourceManagerService {
	@Autowired
	private GridFsTemplate gridFsTemplate;

	@Override
	public String getResourcesWithPublic(String hash) {

		throw new AppException(CommonCodeConst.SERVICE_ERROR);
	}

	@Override
	public String getResourcesWithPrivate(String hash) {

		return getResourcesWithPublic(hash);
	}

	@Override
	public String saveResourcesWithPublic(InputStream inputStream) {

		GridFSFile gridFSFile = gridFsTemplate.store(inputStream, UUID.randomUUID().toString());
		return gridFSFile.getMD5();
	}

	@Override
	public String saveResourcesWithPrivate(InputStream inputStream) {
		GridFSFile gridFSFile = gridFsTemplate.store(inputStream, UUID.randomUUID().toString());
		return gridFSFile.getMD5();
	}

	private GridFSDBFile getImageGrid(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("md5").is(name));
		GridFSDBFile gridFSFile = gridFsTemplate.findOne(query);
		return gridFSFile;
	}

	@Override
	public InputStream getResourcesWithPublicStream(String hash) {
		GridFSDBFile gridFSFile = getImageGrid(hash);
		if (null == gridFSFile) {
			return null;
		}
		return gridFSFile.getInputStream();
	}

	@Override
	public InputStream getResourcesWithPrivateStream(String hash) {
		return getResourcesWithPublicStream(hash);
	}

}
