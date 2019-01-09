package com.gop.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.UserLockPosition;


public interface UserLockPositionMapper {

    int insert(UserLockPosition record);

    int insertSelective(UserLockPosition record);

    UserLockPosition selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLockPosition record);

    int updateByPrimaryKey(UserLockPosition record);
    
    UserLockPosition selectByUidAndAssetCode(@Param("uid")Integer uid,@Param("assetCode")String assetCode);
    
    int addUserLockPosition(UserLockPosition userLockPosition);
    
    int updateUserLockPositionAmountByUidAndAssetCode(UserLockPosition userLockPosition);
    
    int updateUserLockPositionAmountByid(@Param("id")Integer id,@Param("amount")BigDecimal amount);
}