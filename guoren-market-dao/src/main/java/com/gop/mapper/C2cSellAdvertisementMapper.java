package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.C2cSellAdvertisement;
import com.gop.domain.enums.C2cSellAdvertStatus;

public interface C2cSellAdvertisementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(C2cSellAdvertisement record);

    int insertSelective(C2cSellAdvertisement record);

    C2cSellAdvertisement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(C2cSellAdvertisement record);

    int updateByPrimaryKey(C2cSellAdvertisement record);
    
    int updateC2cSellAdvertStatusByAdvertId(@Param("advertId")String advertId,@Param("status")C2cSellAdvertStatus status,@Param("beginStatus")C2cSellAdvertStatus beginStatus);
    
    List<C2cSellAdvertisement> selectAllByAssetCode(String assetCode);
    
    C2cSellAdvertisement selectByAdvertId(String advertId);
    
    List<C2cSellAdvertisement> selectC2cSellAdvertByUid(Integer uid);
    
    void addC2cSellAdvertisement(C2cSellAdvertisement c2cSellAdvertisement);
    
    List<C2cSellAdvertisement> selectC2cSellAdvertByUidAndStatusAndAssetCode(@Param("uid") Integer uid,@Param("status")C2cSellAdvertStatus status,@Param("assetCode") String assetCode);
}