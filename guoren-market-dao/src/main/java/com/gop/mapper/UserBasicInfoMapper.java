package com.gop.mapper;

import com.gop.domain.UserBasicInfo;

public interface UserBasicInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserBasicInfo record);

    int insertSelective(UserBasicInfo record);

    UserBasicInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserBasicInfo record);

    int updateByPrimaryKey(UserBasicInfo record);
    
    UserBasicInfo selectByUid(Integer uid);
    
    int updateByUid(UserBasicInfo record);
}