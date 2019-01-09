package com.gop.mapper;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.BrokerNotify;

public interface BrokerNotifyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BrokerNotify record);

    int insertSelective(BrokerNotify record);

    BrokerNotify selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BrokerNotify record);

    int updateByPrimaryKey(BrokerNotify record);

	BrokerNotify selectByBrokerId(@Param("borkerId")int borkerId);
}