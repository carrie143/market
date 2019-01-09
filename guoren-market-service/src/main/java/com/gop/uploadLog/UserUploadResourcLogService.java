package com.gop.uploadLog;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.UserUploadResourceLog;

public interface UserUploadResourcLogService {
	/**
	 * 记录用户上传图片日志
	 * @param log
	 */
	public int loggingUserUpload(UserUploadResourceLog log);
	/**
	 * 通过uid与tag查询log,返回boolean
	 * @param uid
	 * @param tag
	 * @return
	 */
	public Boolean checkIsUser(@Param("uid")Integer uid, @Param("tag")String tag);
	/**
	 * 通过uid与tag查询log,返回log对象
	 * @param uid
	 * @param name
	 * @return
	 */
	public UserUploadResourceLog queryLogByTagAndUid(Integer uid, String name);
	
}
