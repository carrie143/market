package com.gop.mapper;

import com.gop.domain.UserUploadResourceLog;

public interface UserUploadResourceLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserUploadResourceLog record);

    int insertSelective(UserUploadResourceLog record);

    UserUploadResourceLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserUploadResourceLog record);

    int updateByPrimaryKey(UserUploadResourceLog record);

	UserUploadResourceLog selectLogByTagAndUid(Integer uid, String tag);

}