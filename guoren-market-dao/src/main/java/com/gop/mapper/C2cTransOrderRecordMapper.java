package com.gop.mapper;

import com.gop.domain.C2cTransOrderRecord;

public interface C2cTransOrderRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(C2cTransOrderRecord record);

    int insertSelective(C2cTransOrderRecord record);

    C2cTransOrderRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(C2cTransOrderRecord record);

    int updateByPrimaryKey(C2cTransOrderRecord record);
}