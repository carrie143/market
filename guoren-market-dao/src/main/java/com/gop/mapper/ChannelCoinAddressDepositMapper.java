package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.ChannelCoinAddressDeposit;
import com.gop.domain.ChannelCoinAddressWithdraw;

public interface ChannelCoinAddressDepositMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChannelCoinAddressDeposit record);

    int insertSelective(ChannelCoinAddressDeposit record);

    ChannelCoinAddressDeposit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChannelCoinAddressDeposit record);

    int updateByPrimaryKey(ChannelCoinAddressDeposit record);
    
    public ChannelCoinAddressDeposit getAddress(@Param("uid") Integer uid ,@Param("assetCode") String assetCode);
    
    public ChannelCoinAddressDeposit selectByAssetAndAddress(@Param("coinAddress") String coinAddress ,@Param("assetCode") String assetCode);

	public ChannelCoinAddressDeposit selectByAddressAndAssetCodeNotDel(@Param("address") String address,
			@Param("assetCode") String assetCode);
	
	public ChannelCoinAddressDeposit selectByAddressNotDel(@Param("address") String address);
    
    public List<ChannelCoinAddressDeposit> getAddressList(@Param("uid") Integer uid ,@Param("assetCode") String assetCode);
    
}