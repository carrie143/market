package com.gop.mapper;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.ForkUserReceiveCandy;
import com.gop.domain.enums.ForkUserReceiveCandyStatus;

public interface ForkUserReceiveCandyMapper {

    int insert(ForkUserReceiveCandy record);

    int insertSelective(ForkUserReceiveCandy record);

    ForkUserReceiveCandy selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ForkUserReceiveCandy record);

    int updateByPrimaryKey(ForkUserReceiveCandy record);
    
    ForkUserReceiveCandy getForkUserReceiveCandyByUidAndAssetCode(@Param("uid")Integer uid,@Param("forkAssetCode")String forkAssetCode,@Param("targetAssetCode")String targetAssetCode);
    
    int updateForkUserReceiveCandyStatusById(@Param("id")Integer id,@Param("beginStatus")ForkUserReceiveCandyStatus beginStatus,@Param("endStatus")ForkUserReceiveCandyStatus endStatus);
    
}