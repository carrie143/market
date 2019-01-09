package com.gop.mapper;

import com.gop.domain.BeginingBalance;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by wuyanjie on 2018/4/28.
 */
public interface BeginingBalanceMapper {

    List<BeginingBalance> getBeginingBalance(@Param("assetCode") String assetCode, @Param("startDate") Date startDate);

    void insertBatch(List<BeginingBalance> beginingBalances);
 }
