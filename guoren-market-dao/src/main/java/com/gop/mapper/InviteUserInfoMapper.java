package com.gop.mapper;

import com.gop.domain.InviteUserInfo;

public interface InviteUserInfoMapper {

    int insert(InviteUserInfo record);

    int insertSelective(InviteUserInfo record);

    InviteUserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InviteUserInfo record);

    int updateByPrimaryKey(InviteUserInfo record);
    
    InviteUserInfo getInviteUserInfoByUid(Integer uid);
    
    int addInviteUserInfo(InviteUserInfo inviteUserInfo);
    
    InviteUserInfo getInviteUserInfoByInviteCode(String inviteCode);
}