package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.ChannelCoinAddressWithdraw;

public interface ChannelCoinAddressWithdrawMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(ChannelCoinAddressWithdraw record);

	int insertSelective(ChannelCoinAddressWithdraw record);

	ChannelCoinAddressWithdraw selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(ChannelCoinAddressWithdraw record);

	int updateByPrimaryKey(ChannelCoinAddressWithdraw record);

	ChannelCoinAddressWithdraw selectForUpdate(Integer id);

	ChannelCoinAddressWithdraw selectByUidCoinAddress(@Param("uid") Integer uid, @Param("assetCode") String assetCode,
			@Param("coinAddress") String coinAddress);

	public List<ChannelCoinAddressWithdraw> getAddress(@Param("uid") Integer uid, @Param("assetCode") String assetCode);

	public ChannelCoinAddressWithdraw selectByAddressAndAssetCodeNotDel(@Param("address") String address,
			@Param("assetCode") String assetCode,@Param("uid") Integer uid);
	
	int countMemoByUidAndAssetCodeAndStatus(@Param("memo") String memo,@Param("assetCode") String assetCode,@Param("uid") Integer uid);
}