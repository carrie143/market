package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.UserLoginLog;

public interface UserLoginLogMapper {

    int insert(UserLoginLog record);

    int insertSelective(UserLoginLog record);

    UserLoginLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLoginLog record);

    int updateByPrimaryKey(UserLoginLog record);
    
    List<UserLoginLog> selectByIpAddress(@Param("ipAddress")String ipAddress);
    
    UserLoginLog selectLastLoginIpByUid(@Param("uid") Integer uid);
    
    List<UserLoginLog> selectIpByUid(@Param("uid")Integer uid);

	UserLoginLog getFirstLoginByUid(Integer uid);
    
}