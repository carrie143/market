package com.gop.mapper;

import com.gop.domain.ChannelAlipayOrderDeposit;

public interface ChannelAlipayOrderDepositMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChannelAlipayOrderDeposit record);

    int insertSelective(ChannelAlipayOrderDeposit record);

    ChannelAlipayOrderDeposit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChannelAlipayOrderDeposit record);

    int updateByPrimaryKey(ChannelAlipayOrderDeposit record);
}