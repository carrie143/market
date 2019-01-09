package com.gop.mapper;

import com.gop.domain.C2cGoldenTransOrder;

public interface C2cGoldenTransOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(C2cGoldenTransOrder record);

    int insertSelective(C2cGoldenTransOrder record);

    C2cGoldenTransOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(C2cGoldenTransOrder record);

    int updateByPrimaryKey(C2cGoldenTransOrder record);
}