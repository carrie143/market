package com.gop.mapper;

import com.gop.domain.ForkUserReceiveCandyDetail;

public interface ForkUserReceiveCandyDetailMapper {

    int insert(ForkUserReceiveCandyDetail record);

    int insertSelective(ForkUserReceiveCandyDetail record);

    ForkUserReceiveCandyDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ForkUserReceiveCandyDetail record);

    int updateByPrimaryKey(ForkUserReceiveCandyDetail record);
    
    int addForkUserReceiveCandyDetail(ForkUserReceiveCandyDetail record); 
}