package com.gop.mapper;

import com.gop.domain.C2cAlipayInfo;
import com.gop.domain.C2cBankInfo;

public interface C2cBankInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(C2cBankInfo record);

    int insertSelective(C2cBankInfo record);

    C2cBankInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(C2cBankInfo record);

    int updateByPrimaryKey(C2cBankInfo record);
    
    int deleteById(Integer id);
    
    C2cBankInfo selectStatusUsingByUid(Integer uid);
}