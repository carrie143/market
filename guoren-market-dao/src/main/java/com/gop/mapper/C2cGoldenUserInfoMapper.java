package com.gop.mapper;

import com.gop.domain.C2cGoldenUserInfo;

public interface C2cGoldenUserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(C2cGoldenUserInfo record);

    int insertSelective(C2cGoldenUserInfo record);

    C2cGoldenUserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(C2cGoldenUserInfo record);

    int updateByPrimaryKey(C2cGoldenUserInfo record);
}