package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.UserInfoProfile;

public interface UserInfoProfileMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(UserInfoProfile record);

	int insertSelective(UserInfoProfile record);

	UserInfoProfile selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(UserInfoProfile record);

	int updateByPrimaryKey(UserInfoProfile record);

	List<UserInfoProfile> getUserInfoProfileList(@Param("uid")int uid);
}