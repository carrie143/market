package com.gop.mapper;

import com.gop.domain.QuantFundReceiveIncomeDetail;

public interface QuantFundReceiveIncomeDetailMapper {
    
    int insert(QuantFundReceiveIncomeDetail record);

    int insertSelective(QuantFundReceiveIncomeDetail record);

    QuantFundReceiveIncomeDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuantFundReceiveIncomeDetail record);

    int updateByPrimaryKey(QuantFundReceiveIncomeDetail record);
    
    int addQuantFundReceiveIncomeDetail(QuantFundReceiveIncomeDetail quantFundReceiveIncomeDetail);
}