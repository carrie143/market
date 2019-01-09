package com.gop.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.C2cUserEncourageInfoRecord;

public interface C2cUserEncourageInfoRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(C2cUserEncourageInfoRecord record);

    int insertSelective(C2cUserEncourageInfoRecord record);

    C2cUserEncourageInfoRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(C2cUserEncourageInfoRecord record);

    int updateByPrimaryKey(C2cUserEncourageInfoRecord record);
    
    int selectByEncourageIdAndDate(@Param("uid") Integer uid,@Param("encourageId") Integer encourageId, 
			@Param("beginDate") Date beginDate,@Param("endDate") Date endDate);
    
}