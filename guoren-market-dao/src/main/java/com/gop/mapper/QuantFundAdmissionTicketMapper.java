package com.gop.mapper;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.QuantFundAdmissionTicket;
import com.gop.domain.enums.QuantFundAdmissionTicketStatus;

public interface QuantFundAdmissionTicketMapper {

    int insert(QuantFundAdmissionTicket record);

    int insertSelective(QuantFundAdmissionTicket record);

    QuantFundAdmissionTicket selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuantFundAdmissionTicket record);

    int updateByPrimaryKey(QuantFundAdmissionTicket record);
    
    QuantFundAdmissionTicket getQuantFundAdmissionTicketByUidAndFundAssetCode(@Param("uid") Integer uid,@Param("fundAssetCode")String fundAssetCode);
    
    int addQuantFundAdmissionTicket(QuantFundAdmissionTicket quantFundAdmissionTicket);
    
    int updateQuantFundAdmissionTicketStatusById(@Param("id") Integer id,
    		@Param("beginStatus") QuantFundAdmissionTicketStatus beginStatus,@Param("endStatus") QuantFundAdmissionTicketStatus endStatus);
}