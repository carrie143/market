package com.gop.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.gop.domain.StatisticeResult;
import com.gop.domain.enums.AssetStatus;
import org.apache.ibatis.annotations.Param;

import com.gop.domain.DepositCoinOrderUser;
import com.gop.domain.enums.DepositCoinAssetStatus;

public interface DepositCoinOrderUserMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(DepositCoinOrderUser record);

	int insertSelective(DepositCoinOrderUser record);

	DepositCoinOrderUser selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(DepositCoinOrderUser record);

	int updateByPrimaryKey(DepositCoinOrderUser record);

	List<DepositCoinOrderUser> selectListByUidAndAssetCode(@Param("uid") Integer uid,
			@Param("assetCode") String assetCode);

	DepositCoinOrderUser selectForUpdate(Integer id);

	List<DepositCoinOrderUser> selectDepositOrderList(@Param("brokerId") Integer brokerId, @Param("id") Integer id,
			@Param("account") String account, @Param("uid") Integer uId, @Param("address") String address,
			@Param("txid") String txid, @Param("assetCode") String assetCode,@Param("beginTime") Date beginTime,
			@Param("endTime") Date endTime,@Param("email") String email,
			@Param("assetStatus") DepositCoinAssetStatus status);

	List<DepositCoinOrderUser> selectByUidAndDateScope(@Param("uId") Integer uId, @Param("fromDate") Date fromDate,
			@Param("toDate") Date toDate);

	DepositCoinOrderUser selectByInnerOrderNo(@Param("txid") String txid);

	DepositCoinOrderUser selectByOutOrder(@Param("assetCode") String assetCode, @Param("outOrder") String outOrder);

	BigDecimal getTotalDeposit(@Param("assetCode") String assetCode, @Param("status") DepositCoinAssetStatus status,
							   @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	List<StatisticeResult> getTotalDeposits(@Param("assetCode") String assetCode, @Param("status") DepositCoinAssetStatus status,
										   @Param("startDate") Date startDate, @Param("endDate") Date endDate);


	List<StatisticeResult> depositStaitstic();

}