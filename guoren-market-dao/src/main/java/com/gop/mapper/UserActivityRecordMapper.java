package com.gop.mapper;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.UserActivityRecord;

public interface UserActivityRecordMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(UserActivityRecord record);

	int insertRecord(UserActivityRecord record);

	int insertSelective(UserActivityRecord record);

	UserActivityRecord selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(UserActivityRecord record);

	int updateByPrimaryKey(UserActivityRecord record);

	int getCountByUidAndActivityType(@Param("uid") Integer uid, @Param("type") String type);
	
	int getCountByActivityTypeAndCardTypeAndCardNo(@Param("type")String type,@Param("cardType")String cardType,@Param("cardNo")String cardNo);
}