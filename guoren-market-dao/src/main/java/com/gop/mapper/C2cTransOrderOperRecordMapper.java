package com.gop.mapper;

import com.gop.domain.C2cTransOrderOperRecord;

public interface C2cTransOrderOperRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(C2cTransOrderOperRecord record);

    int insertSelective(C2cTransOrderOperRecord record);

    C2cTransOrderOperRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(C2cTransOrderOperRecord record);

    int updateByPrimaryKey(C2cTransOrderOperRecord record);
}