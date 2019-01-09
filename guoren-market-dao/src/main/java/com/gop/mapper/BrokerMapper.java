package com.gop.mapper;

import java.util.List;

import com.gop.domain.Broker;

public interface BrokerMapper {
    int deleteByPrimaryKey(Long brokerId);

    int insert(Broker record);

    int insertSelective(Broker record);

    Broker selectByPrimaryKey(Long brokerId);

    int updateByPrimaryKeySelective(Broker record);

    int updateByPrimaryKey(Broker record);
    
    //根据手机号查询Broker表记录
    Broker selectByMobile(String mobile);
    
    //根据邮箱号查询Broker表记录
    Broker selectByEmail(String email);
    
    List<Broker> getBrokerList();
}