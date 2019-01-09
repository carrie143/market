package com.gop.mapper;

import com.gop.domain.EmailLog;

public interface EmailLogMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(EmailLog record);

	int insertSelective(EmailLog record);

	EmailLog selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(EmailLog record);

	int updateByPrimaryKey(EmailLog record);
}