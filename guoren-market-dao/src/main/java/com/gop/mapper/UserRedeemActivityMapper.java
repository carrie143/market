package com.gop.mapper;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.UserRedeemActivity;
import com.gop.domain.enums.UserRedeemActivityStatus;

public interface UserRedeemActivityMapper {

    int insert(UserRedeemActivity record);

    int insertSelective(UserRedeemActivity record);

    UserRedeemActivity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRedeemActivity record);

    int updateByPrimaryKey(UserRedeemActivity record);
    
    UserRedeemActivity getRedeemActivityByAssetCodeAndRedeemCode(@Param("assetCode") String assetCode,@Param("redeemCode") String redeemCode);
    
    int updateRedeemActivityStatusAndUid(@Param("id") Integer id,@Param("uid") Integer uid,@Param("beginStatus") UserRedeemActivityStatus beginStatus,@Param("endStatus") UserRedeemActivityStatus endStatus);
}