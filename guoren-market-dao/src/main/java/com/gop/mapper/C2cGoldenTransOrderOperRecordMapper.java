package com.gop.mapper;

import com.gop.domain.C2cGoldenTransOrderOperRecord;

public interface C2cGoldenTransOrderOperRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(C2cGoldenTransOrderOperRecord record);

    int insertSelective(C2cGoldenTransOrderOperRecord record);

    C2cGoldenTransOrderOperRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(C2cGoldenTransOrderOperRecord record);

    int updateByPrimaryKey(C2cGoldenTransOrderOperRecord record);
}