package com.gop.mapper;

import com.gop.domain.MqDeadsMessage;

public interface MqDeadsMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MqDeadsMessage record);

    int insertSelective(MqDeadsMessage record);

    MqDeadsMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MqDeadsMessage record);

    int updateByPrimaryKeyWithBLOBs(MqDeadsMessage record);

    int updateByPrimaryKey(MqDeadsMessage record);
}