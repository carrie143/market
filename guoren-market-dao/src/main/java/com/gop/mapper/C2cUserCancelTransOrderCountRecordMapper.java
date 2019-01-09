package com.gop.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.C2cUserCancelTransOrderCountRecord;

public interface C2cUserCancelTransOrderCountRecordMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(C2cUserCancelTransOrderCountRecord record);

	int insertSelective(C2cUserCancelTransOrderCountRecord record);

	C2cUserCancelTransOrderCountRecord selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(C2cUserCancelTransOrderCountRecord record);

	int updateByPrimaryKey(C2cUserCancelTransOrderCountRecord record);

	Integer getCountOfCancelToday(@Param("uid") Integer uid, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);
}