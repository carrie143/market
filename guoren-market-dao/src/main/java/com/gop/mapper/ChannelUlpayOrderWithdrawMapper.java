package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.enums.UlPayStatus;
import com.gop.domain.ChannelCibOrderWithdraw;
import com.gop.domain.ChannelUlpayOrderWithdraw;

public interface ChannelUlpayOrderWithdrawMapper {
    int deleteByPrimaryKey(Integer no);

    int insert(ChannelUlpayOrderWithdraw record);

    int insertSelective(ChannelUlpayOrderWithdraw record);

    ChannelUlpayOrderWithdraw selectByPrimaryKey(Integer no);

    int updateByPrimaryKeySelective(ChannelUlpayOrderWithdraw record);

    int updateByPrimaryKey(ChannelUlpayOrderWithdraw record);
    
    int updateByWithVersion(ChannelUlpayOrderWithdraw record);

    List<ChannelUlpayOrderWithdraw> getUlPayListByTxNo(@Param("txNo") String txNo);
    
    List<ChannelUlpayOrderWithdraw> getUlPayListByStatus(@Param("status") String status);
    
    ChannelUlpayOrderWithdraw getUlPayByTxNo(String txNo);
    
    ChannelUlpayOrderWithdraw selectForUpdate(@Param("id") int id);
    
    ChannelUlpayOrderWithdraw getLastOrderByTxId(@Param("txid") String txid);
}