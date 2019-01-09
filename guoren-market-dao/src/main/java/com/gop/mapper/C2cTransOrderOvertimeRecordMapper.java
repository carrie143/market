package com.gop.mapper;

import com.gop.domain.C2cTransOrderOvertimeRecord;

public interface C2cTransOrderOvertimeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(C2cTransOrderOvertimeRecord record);

    int insertSelective(C2cTransOrderOvertimeRecord record);

    C2cTransOrderOvertimeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(C2cTransOrderOvertimeRecord record);

    int updateByPrimaryKey(C2cTransOrderOvertimeRecord record);
}