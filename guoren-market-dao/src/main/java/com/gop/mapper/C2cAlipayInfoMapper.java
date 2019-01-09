package com.gop.mapper;

import com.gop.domain.C2cAlipayInfo;

public interface C2cAlipayInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(C2cAlipayInfo record);

    int insertSelective(C2cAlipayInfo record);

    C2cAlipayInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(C2cAlipayInfo record);

    int updateByPrimaryKey(C2cAlipayInfo record);
    
    int deleteById(Integer id);
    
    C2cAlipayInfo selectStatusUsingByUid(Integer uid);
    
}