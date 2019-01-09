package com.gop.mapper;

import com.gop.domain.ChannelCibOrderDeposit;

public interface ChannelCibOrderDepositMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChannelCibOrderDeposit record);

    int insertSelective(ChannelCibOrderDeposit record);

    ChannelCibOrderDeposit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChannelCibOrderDeposit record);

    int updateByPrimaryKey(ChannelCibOrderDeposit record);
}