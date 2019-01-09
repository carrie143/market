package com.gop.mapper;

import com.gop.domain.C2cUserEncourageInfo;

public interface C2cUserEncourageInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(C2cUserEncourageInfo record);

    int insertSelective(C2cUserEncourageInfo record);

    C2cUserEncourageInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(C2cUserEncourageInfo record);

    int updateByPrimaryKey(C2cUserEncourageInfo record);
    
    C2cUserEncourageInfo selectByUid(Integer uid);
  
    int updateEncouragedCountByUid(Integer uid);
}