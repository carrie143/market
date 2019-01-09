package com.gop.mapper;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.QuantFundTicketPreReturnTemp;

public interface QuantFundTicketPreReturnTempMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QuantFundTicketPreReturnTemp record);

    int insertSelective(QuantFundTicketPreReturnTemp record);

    QuantFundTicketPreReturnTemp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuantFundTicketPreReturnTemp record);

    int updateByPrimaryKey(QuantFundTicketPreReturnTemp record);
    
    QuantFundTicketPreReturnTemp getQuantFundTicketPreReturnTemp(@Param("uid")Integer uid,@Param("fundAssetCode")String fundAssetCode);
}