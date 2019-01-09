package com.gop.mapper;

import com.gop.domain.ChannelOkpayGlobalConfig;

public interface ChannelOkpayGlobalConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChannelOkpayGlobalConfig record);

    int insertSelective(ChannelOkpayGlobalConfig record);

    ChannelOkpayGlobalConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChannelOkpayGlobalConfig record);

    int updateByPrimaryKey(ChannelOkpayGlobalConfig record);
    
    int replaceSelective(ChannelOkpayGlobalConfig record);
}