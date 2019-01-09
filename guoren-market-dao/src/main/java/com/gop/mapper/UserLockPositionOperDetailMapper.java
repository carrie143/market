package com.gop.mapper;

import com.gop.domain.UserLockPositionOperDetail;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserLockPositionOperDetailMapper {

    int insert(UserLockPositionOperDetail record);

    int insertSelective(UserLockPositionOperDetail record);

    UserLockPositionOperDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLockPositionOperDetail record);

    int updateByPrimaryKey(UserLockPositionOperDetail record);

    List<UserLockPositionOperDetail> getUserOperationList(@Param("uid") Integer userId, @Param("assetCode") String assetCode, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    Integer countUserOperation(@Param("uid") Integer userId);
    
    int addUserLockPositionOperDetail(UserLockPositionOperDetail userLockPositionOperDetail);
}