package com.gop.mapper;

import java.util.List;

import com.gop.domain.ChannelAlipayUserRules;

public interface ChannelAlipayUserRulesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChannelAlipayUserRules record);

    int insertSelective(ChannelAlipayUserRules record);

    ChannelAlipayUserRules selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChannelAlipayUserRules record);

    int updateByPrimaryKey(ChannelAlipayUserRules record);
    
    List<ChannelAlipayUserRules> getRulesList();
    
    List<ChannelAlipayUserRules> getRulesListUseful();

    int replaceSelective(ChannelAlipayUserRules record);
}