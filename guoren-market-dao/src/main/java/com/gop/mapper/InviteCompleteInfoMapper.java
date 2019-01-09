package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.InviteCompleteInfo;

public interface InviteCompleteInfoMapper {

    int insert(InviteCompleteInfo record);

    int insertSelective(InviteCompleteInfo record);

    InviteCompleteInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InviteCompleteInfo record);

    int updateByPrimaryKey(InviteCompleteInfo record);
    
    int countInviteCompleteInfoByInviteUidAndActivityId(@Param("inviteUid")Integer inviteUid,
    		@Param("activityId")Integer activityId);
    
    List<InviteCompleteInfo> selectInviteCompleteInfoByInviteUidAndActivityId(@Param("inviteUid")Integer inviteUid,
    		@Param("activityId")Integer activityId);

	InviteCompleteInfo selectInviteCompleteInfoByUidAndActivityId(@Param("uid")Integer uid,@Param("activityId") Integer activityId);
}