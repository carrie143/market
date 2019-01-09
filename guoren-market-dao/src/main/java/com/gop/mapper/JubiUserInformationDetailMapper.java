package com.gop.mapper;

import com.gop.domain.JubiUserInformationDetail;

public interface JubiUserInformationDetailMapper {

    int insert(JubiUserInformationDetail record);

    int insertSelective(JubiUserInformationDetail record);

    JubiUserInformationDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JubiUserInformationDetail record);

    int updateByPrimaryKey(JubiUserInformationDetail record);
    
    void addJubiUserInformationDetail(JubiUserInformationDetail record);
}