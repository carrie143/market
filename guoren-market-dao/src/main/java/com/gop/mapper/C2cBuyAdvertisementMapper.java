package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.C2cBuyAdvertisement;
import com.gop.domain.enums.C2cBuyAdvertStatus;

public interface C2cBuyAdvertisementMapper {

    int insert(C2cBuyAdvertisement record);

    int insertSelective(C2cBuyAdvertisement record);

    C2cBuyAdvertisement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(C2cBuyAdvertisement record);

    int updateByPrimaryKey(C2cBuyAdvertisement record);
    
    List<C2cBuyAdvertisement> selectAllByShowStatus();
    
    C2cBuyAdvertisement selectByAdvertId(String advertId);
    
    int delBuyAdvertisementByAdvertId(@Param("advertId")String advertId,@Param("status")C2cBuyAdvertStatus status,@Param("beginStatus")C2cBuyAdvertStatus beginStatus);
    
    List<C2cBuyAdvertisement> selectC2cBuyAdvertByUid(Integer uid);
    
    List<C2cBuyAdvertisement> selectC2cBuyAdvertByUidAndStatus(@Param("uid")Integer uid,@Param("status")C2cBuyAdvertStatus status);
    
    C2cBuyAdvertisement selectAllStatusByAdvertId(String advertId);
}