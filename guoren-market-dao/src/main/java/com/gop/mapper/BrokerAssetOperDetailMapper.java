package com.gop.mapper;

import com.gop.domain.BrokerAssetOperDetail;
import com.gop.domain.StatisticeResult;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface BrokerAssetOperDetailMapper {

    int insert(BrokerAssetOperDetail record);

    int insertSelective(BrokerAssetOperDetail record);

    BrokerAssetOperDetail selectByPrimaryKey(Integer id);

    List<BrokerAssetOperDetail> selectSelective(@Param("uid") Integer uid, @Param("assetCode") String assetCode,
                                                @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    int updateByPrimaryKeySelective(BrokerAssetOperDetail record);

    int updateByPrimaryKey(BrokerAssetOperDetail record);
    
    int addBrokerAssetOperDetail(BrokerAssetOperDetail brokerAssetOperDetail);

    List<StatisticeResult> getBroketAssetByAsset(@Param("assetCode") String assetCode, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}