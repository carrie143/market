package com.gop.mapper;

import com.gop.domain.UserPreRegistrationPool;

public interface UserPreRegistrationPoolMapper {

    int insert(UserPreRegistrationPool record);

    int insertSelective(UserPreRegistrationPool record);

    UserPreRegistrationPool selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserPreRegistrationPool record);

    int updateByPrimaryKey(UserPreRegistrationPool record);
}