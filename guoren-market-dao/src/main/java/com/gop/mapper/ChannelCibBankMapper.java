package com.gop.mapper;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.ChannelCibBank;

public interface ChannelCibBankMapper {
    int deleteByPrimaryKey(String bankNo);

    int insert(ChannelCibBank record);

    int insertSelective(ChannelCibBank record);

    ChannelCibBank selectByPrimaryKey(String bankNo);

    int updateByPrimaryKeySelective(ChannelCibBank record);

    int updateByPrimaryKey(ChannelCibBank record);
    
    
    ChannelCibBank getCibBankByName(String bankName);

	void clearCibBank();
	
	ChannelCibBank getCibBankByShortName(@Param("shortName") String shortName);
	
	String getBankNoByName(@Param("name")String name);
	
	String getBankNoByShortName(@Param("shortName")String shortName);
}