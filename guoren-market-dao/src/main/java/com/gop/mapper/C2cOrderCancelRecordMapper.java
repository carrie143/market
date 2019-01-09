package com.gop.mapper;

import com.gop.domain.C2cOrderCancelRecord;

public interface C2cOrderCancelRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(C2cOrderCancelRecord record);

    int insertSelective(C2cOrderCancelRecord record);

    C2cOrderCancelRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(C2cOrderCancelRecord record);

    int updateByPrimaryKey(C2cOrderCancelRecord record);
    
    int addC2cOrderCancelRecordByOrderId(C2cOrderCancelRecord c2cOrderCancelRecord);
}