package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.UserTransactionFeeWhiteList;

public interface UserTransactionFeeWhiteListMapper {

    int insert(UserTransactionFeeWhiteList record);

    int insertSelective(UserTransactionFeeWhiteList record);

    UserTransactionFeeWhiteList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserTransactionFeeWhiteList record);

    int updateByPrimaryKey(UserTransactionFeeWhiteList record);
    
    int countUserInWhiteByUid(Integer uid);
    
    List<UserTransactionFeeWhiteList> selectByUid(Integer uid);
    
    int updateByUidInValid(@Param("uid") Integer uid);
    
    int updateByUidSelective(UserTransactionFeeWhiteList record);
    
    List<UserTransactionFeeWhiteList> selectAll();
    
    int checkUidInWhiteListByUid(Integer uid);
}