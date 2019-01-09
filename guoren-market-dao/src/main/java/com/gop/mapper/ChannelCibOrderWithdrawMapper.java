package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.ChannelCibOrderWithdraw;

public interface ChannelCibOrderWithdrawMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChannelCibOrderWithdraw record);

    int insertSelective(ChannelCibOrderWithdraw record);

    ChannelCibOrderWithdraw selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChannelCibOrderWithdraw record);

    int updateByPrimaryKey(ChannelCibOrderWithdraw record);
    
	List<ChannelCibOrderWithdraw> getCibOrderByStatus(String transferStatus);

	int updateByVersion(ChannelCibOrderWithdraw record);

	List<ChannelCibOrderWithdraw> getCibPayListByTxId(@Param("txid") String txid);

	/**
	 * 获取当前txId所对应的最新状态信息
	 * 
	 * @param txid
	 * @return
	 */
	String getLastStatusByTxId(@Param("txid") String txid);

	ChannelCibOrderWithdraw getLastOrderByTxId(@Param("txid") String txid);
	
	ChannelCibOrderWithdraw selectForUpdate(@Param("id") int id);
	
	
}