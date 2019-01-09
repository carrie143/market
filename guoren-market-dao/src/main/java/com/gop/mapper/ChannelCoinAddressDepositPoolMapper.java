package com.gop.mapper;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.ChannelCoinAddressDepositPool;
import com.gop.domain.enums.CoinAddressStatus;

public interface ChannelCoinAddressDepositPoolMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChannelCoinAddressDepositPool record);

    int insertSelective(ChannelCoinAddressDepositPool record);

    ChannelCoinAddressDepositPool selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChannelCoinAddressDepositPool record);

    int updateByPrimaryKey(ChannelCoinAddressDepositPool record);
    
    ChannelCoinAddressDepositPool selectUsefulAddress(@Param("assetCode") String assetCode);
    
    ChannelCoinAddressDepositPool selectForUpdate(Integer id);

	Integer selectByAssetCodeAndAddressStatus(@Param("assetCode")String assetCode, @Param("status")CoinAddressStatus status);
    
	ChannelCoinAddressDepositPool selectByUidAndAssetCode(@Param("uid") Integer uid,@Param("assetCode") String assetCode);
}