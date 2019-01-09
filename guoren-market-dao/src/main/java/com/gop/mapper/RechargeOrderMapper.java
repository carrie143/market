package com.gop.mapper;

import com.gop.domain.RechargeOrder;

public interface RechargeOrderMapper {

    int insert(RechargeOrder record);

    int updateByPrimaryKey(RechargeOrder record);

    RechargeOrder selectByPrimaryKey(Integer id);

}
