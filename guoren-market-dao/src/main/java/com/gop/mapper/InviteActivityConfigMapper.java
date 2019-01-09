package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.InviteActivityConfig;
import com.gop.domain.enums.InviteActivityConfigStatus;

public interface InviteActivityConfigMapper {

    int insert(InviteActivityConfig record);

    int insertSelective(InviteActivityConfig record);

    InviteActivityConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InviteActivityConfig record);

    int updateByPrimaryKey(InviteActivityConfig record);
    
    InviteActivityConfig getInviteActivityConfigByActivityName(@Param("activityName")String activityName);

	int createOrUpdate(InviteActivityConfig config);

	List<InviteActivityConfig> queryConfig(@Param("activityName")String activityName);
	
	int countInviteActivityConfigByStatus(InviteActivityConfigStatus status);
    
}