package com.gop.mapper;

import com.gop.domain.ChannelAlipayGlobalConfig;

public interface ChannelAlipayGlobalConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChannelAlipayGlobalConfig record);

    int insertSelective(ChannelAlipayGlobalConfig record);

    ChannelAlipayGlobalConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChannelAlipayGlobalConfig record);

    int updateByPrimaryKey(ChannelAlipayGlobalConfig record);
    
    int replaceSelective(ChannelAlipayGlobalConfig record);
}