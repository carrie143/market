package com.gop.mapper;

import com.gop.mapper.dto.WithdrawAddress;

import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by YAO on 2018/6/30.
 */
public interface WithdrawAddressBrokerMapper {
  void addWithdrawAddressBroker(@Param("assetCode") String assetCode,
                                @Param("address") String address,
                                @Param("createDate") Timestamp createDate,
                                @Param("updateDate") Timestamp updateDate
                                );

  void updateWithdrawAddressBroker(@Param("assetCode") String assetCode,
                                   @Param("address") String address,
                                   @Param("updateDate") Timestamp updateDate
                                  );

  List<WithdrawAddress> selectWithdrawAddressBroker(@Param("assetCode") String assetCode);
}
