package com.gop.mapper;

import com.gop.domain.QuantFundAdmissionTicketReturnDetail;

public interface QuantFundAdmissionTicketReturnDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QuantFundAdmissionTicketReturnDetail record);

    int insertSelective(QuantFundAdmissionTicketReturnDetail record);

    QuantFundAdmissionTicketReturnDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuantFundAdmissionTicketReturnDetail record);

    int updateByPrimaryKey(QuantFundAdmissionTicketReturnDetail record);
    
    int addQuantFundAdmissionTicketReturnDetail(QuantFundAdmissionTicketReturnDetail quantFundAdmissionTicketReturnDetail);
}