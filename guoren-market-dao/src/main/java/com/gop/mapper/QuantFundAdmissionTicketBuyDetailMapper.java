package com.gop.mapper;

import com.gop.domain.QuantFundAdmissionTicketBuyDetail;

public interface QuantFundAdmissionTicketBuyDetailMapper {
    
    int insert(QuantFundAdmissionTicketBuyDetail record);

    int insertSelective(QuantFundAdmissionTicketBuyDetail record);

    QuantFundAdmissionTicketBuyDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuantFundAdmissionTicketBuyDetail record);

    int updateByPrimaryKey(QuantFundAdmissionTicketBuyDetail record);
    
    int addQuantFundAdmissionTicketBuyDetail(QuantFundAdmissionTicketBuyDetail quantFundAdmissionTicketBuyDetail);
}