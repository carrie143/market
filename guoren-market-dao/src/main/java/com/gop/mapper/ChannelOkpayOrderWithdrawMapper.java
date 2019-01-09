package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.ChannelOkpayOrderWithdraw;

public interface ChannelOkpayOrderWithdrawMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(ChannelOkpayOrderWithdraw record);

	int insertSelective(ChannelOkpayOrderWithdraw record);

	ChannelOkpayOrderWithdraw selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(ChannelOkpayOrderWithdraw record);

	int updateByPrimaryKey(ChannelOkpayOrderWithdraw record);

	ChannelOkpayOrderWithdraw selectForUpdate(Integer id);
	
	List<ChannelOkpayOrderWithdraw> getOkpayListByTxid(String txid);

	String getLastStatusByTxId(@Param("txid") String txid);

	ChannelOkpayOrderWithdraw getLastOrderByTxId(@Param("txid") String txid);

	List<ChannelOkpayOrderWithdraw> getOrdersByStatus(String transferStatus);

	List<ChannelOkpayOrderWithdraw> getOrdersByStatusForUpdate(String transferStatus);
	
	int  updateByVersion(ChannelOkpayOrderWithdraw record);

}