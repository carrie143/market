package com.gop.mapper;

import com.gop.domain.ChannelOkpayOrderDeposit;

public interface ChannelOkpayOrderDepositMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChannelOkpayOrderDeposit record);

    int insertSelective(ChannelOkpayOrderDeposit record);

    ChannelOkpayOrderDeposit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChannelOkpayOrderDeposit record);

    int updateByPrimaryKey(ChannelOkpayOrderDeposit record);
}