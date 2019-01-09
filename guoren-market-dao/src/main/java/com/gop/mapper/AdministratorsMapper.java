package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.Administrators;

public interface AdministratorsMapper {
	
	int deleteByPrimaryKey(Integer adminId);

	int insertSelective(Administrators record);

	Administrators selectByPrimaryKey(Integer adminId);
	
	int updateByPrimaryKeySelective(Administrators record);

	int updateByPrimaryKey(Administrators record);

	/*
	 * List<Administrators> getAdministratorList(@Param("uid") Integer uid,
	 * 
	 * @Param("mobile") String phone,
	 * 
	 * @Param("locked") String lockStatus,
	 * 
	 * @Param("startId") Integer startId,
	 * 
	 * @Param("pageSize") Integer pageSize);
	 */
	List<Administrators> getAdministratorList(@Param("adminId") Integer adminId, @Param("mobile") String phone,
			@Param("locked") String lockStatus);

	int getAdministratorCount(@Param("uid") Integer uid, @Param("mobile") String phone,
			@Param("locked") String lockStatus);
	
	Administrators selectByAccount(@Param("mobile")String phone);
}