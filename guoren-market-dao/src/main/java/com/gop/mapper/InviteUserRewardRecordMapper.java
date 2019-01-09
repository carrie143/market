package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.InviteUserRewardRecord;
import com.gop.domain.enums.InviteActivityRewardConfigInviteType;

public interface InviteUserRewardRecordMapper {

    int insert(InviteUserRewardRecord record);

    int insertSelective(InviteUserRewardRecord record);

    InviteUserRewardRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InviteUserRewardRecord record);

    int updateByPrimaryKey(InviteUserRewardRecord record);
    
    List<InviteUserRewardRecord> getInviteUserRewardRecordListByUidAndActivityId(@Param("uid")Integer uid,
			@Param("activityId")Integer activityId);
    
    List<InviteUserRewardRecord> getInviteUserRewardRecordListByUidAndActivityIdAndType(@Param("uid")Integer uid,
			@Param("activityId")Integer activityId,@Param("inviteType")InviteActivityRewardConfigInviteType inviteType);
    
    int countInviteUserRewardRecordByUidAndActivityIdAndType(@Param("uid")Integer uid,
			@Param("activityId")Integer activityId,@Param("inviteType")InviteActivityRewardConfigInviteType inviteType);
}