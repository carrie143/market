package com.gop.mapper;

import com.gop.domain.ChannelUlpayBank;

public interface ChannelUlpayBankMapper {
    int deleteByPrimaryKey(String id);

    int insert(ChannelUlpayBank record);

    int insertSelective(ChannelUlpayBank record);

    ChannelUlpayBank selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ChannelUlpayBank record);

    int updateByPrimaryKey(ChannelUlpayBank record);
    
    ChannelUlpayBank getBankByName(String name);
}