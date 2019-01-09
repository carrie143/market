package com.gop.mapper;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.UserPayPassword;

public interface UserPayPasswordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPayPassword record);

    int insertSelective(UserPayPassword record);

    UserPayPassword selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserPayPassword record);

    int updateByPrimaryKey(UserPayPassword record);
    
    UserPayPassword getInfoByUid(@Param("userId") Integer userId);

	void updateByUid(UserPayPassword lockPaypwd);
}