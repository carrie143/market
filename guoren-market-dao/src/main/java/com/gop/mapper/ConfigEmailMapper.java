package com.gop.mapper;

import com.gop.domain.ConfigEmail;
import com.gop.domain.enums.ConfigEmailStatus;

public interface ConfigEmailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConfigEmail record);

    int insertSelective(ConfigEmail record);

    ConfigEmail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConfigEmail record);

    int updateByPrimaryKey(ConfigEmail record);

	ConfigEmail getEmailByMinSendCountAndStatus(ConfigEmailStatus status);
	
	int updateMailCount(Integer id); 
}