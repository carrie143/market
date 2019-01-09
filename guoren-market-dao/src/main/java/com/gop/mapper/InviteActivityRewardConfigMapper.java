package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.InviteActivityRewardConfig;
import com.gop.domain.enums.InviteActivityRewardConfigInviteType;
import com.gop.domain.enums.InviteActivityRewardConfigStatus;

public interface InviteActivityRewardConfigMapper {

    int insert(InviteActivityRewardConfig record);

    int insertSelective(InviteActivityRewardConfig record);

    InviteActivityRewardConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InviteActivityRewardConfig record);

    int updateByPrimaryKey(InviteActivityRewardConfig record);

	int createOrUpdate(InviteActivityRewardConfig config);
    
    List<InviteActivityRewardConfig> getInviteActivityRewardConfigListByActivityId(@Param("activityId") Integer activityId,
    		@Param("inviteType")InviteActivityRewardConfigInviteType inviteType,@Param("status") InviteActivityRewardConfigStatus status);

	List<InviteActivityRewardConfig> queryRewardConfig(Integer activityId);
}