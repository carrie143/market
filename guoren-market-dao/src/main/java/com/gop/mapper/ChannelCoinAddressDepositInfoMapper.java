package com.gop.mapper;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.ChannelCoinAddressDepositInfo;

public interface ChannelCoinAddressDepositInfoMapper {

    int insert(ChannelCoinAddressDepositInfo record);

    int insertSelective(ChannelCoinAddressDepositInfo record);

    ChannelCoinAddressDepositInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChannelCoinAddressDepositInfo record);

    int updateByPrimaryKey(ChannelCoinAddressDepositInfo record);
    
    ChannelCoinAddressDepositInfo getChannelCoinAddressDepositInfoByTargetAssetCode(@Param("targetAssetCode")String targetAssetCode);
}