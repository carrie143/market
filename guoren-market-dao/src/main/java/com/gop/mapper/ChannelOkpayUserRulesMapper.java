package com.gop.mapper;

import java.util.List;

import com.gop.domain.ChannelOkpayUserRules;

public interface ChannelOkpayUserRulesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChannelOkpayUserRules record);

    int insertSelective(ChannelOkpayUserRules record);

    ChannelOkpayUserRules selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChannelOkpayUserRules record);

    int updateByPrimaryKey(ChannelOkpayUserRules record);
    
    List<ChannelOkpayUserRules> getRulesList();

	List<ChannelOkpayUserRules> getRulesListUseful();
	
	int replaceSelective(ChannelOkpayUserRules record);
}