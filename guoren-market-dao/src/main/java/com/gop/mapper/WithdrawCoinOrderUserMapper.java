package com.gop.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.gop.domain.StatisticeResult;
import com.gop.domain.WithDrawStatistic;
import org.apache.ibatis.annotations.Param;

import com.gop.domain.WithdrawCoinOrderUser;
import com.gop.domain.enums.WithdrawCoinOrderStatus;
import com.gop.domain.enums.WithdrawCoinOrderType;

public interface WithdrawCoinOrderUserMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(WithdrawCoinOrderUser record);

	int insertSelective(WithdrawCoinOrderUser record);

	WithdrawCoinOrderUser selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(WithdrawCoinOrderUser record);

	int updateByPrimaryKey(WithdrawCoinOrderUser record);

	List<WithdrawCoinOrderUser> selectListByUidAndAssetCode(@Param("uid") Integer uid,
			@Param("assetCode") String assetCode);

	
	WithdrawCoinOrderUser selectForUpdate(Integer id);
	
	WithdrawCoinOrderUser selectByInnerOrder(@Param("innerOrder") String innerder);
	
	List<WithdrawCoinOrderUser> getWithdrawOrderList(@Param("brokerId") Integer brokerId, @Param("id") Integer id,
			@Param("account") String account, @Param("uid") Integer uId, @Param("address") String address,
			@Param("txid") String txid, @Param("assetCode") String assetCode,@Param("beginTime") Date beginTime,
			@Param("endTime") Date endTime,@Param("email") String email,
			@Param("status") WithdrawCoinOrderStatus status);
	List<WithdrawCoinOrderUser> getWithdrawOrderAlreadyProcessedList(@Param("brokerId") Integer brokerId, @Param("id") Integer id,
			@Param("account") String account, @Param("uid") Integer uId, @Param("address") String address,
			@Param("txid") String txid, @Param("assetCode") String assetCode,@Param("beginTime") Date beginTime,
			@Param("endTime") Date endTime,@Param("email") String email);
	
	List<WithdrawCoinOrderUser> getWithdrawOrderUntreatedList(@Param("brokerId") Integer brokerId, @Param("id") Integer id,
			@Param("account") String account, @Param("uid") Integer uId, @Param("address") String address,
			@Param("txid") String txid, @Param("assetCode") String assetCode,@Param("beginTime") Date beginTime,
			@Param("endTime") Date endTime,@Param("email") String email);
	
	List<WithdrawCoinOrderUser> queryWithdrawOrderList(@Param("brokerId") Integer brokerId, @Param("id") Integer id,
			@Param("account") String account, @Param("uid") Integer uId, @Param("address") String address,
			@Param("txid") String txid, @Param("assetCode") String assetCode,@Param("beginTime") Date beginTime,
			@Param("endTime") Date endTime,@Param("email") String email,
			@Param("status") WithdrawCoinOrderType status);
	
	WithdrawCoinOrderUser selectByUidAndOutOrder(@Param("uid") Integer uid,@Param("outOrder") String outOrder);
	
	BigDecimal getUserDailyWithdrawedCoinValue(@Param("uid") Integer uid,@Param("assetCode") String assetCode, 
			@Param("beginDate") Date beginDate,@Param("endDate") Date endDate);

	Integer getAmountOfAssetWithStatus(@Param("assetCode") String assetCode, @Param("status")WithdrawCoinOrderStatus status);

	List<WithDrawStatistic> getTotalWithdrawedCoinValue(@Param("assetCode") String assetCode,
														@Param("startDate") Date startDate,
														@Param("endDate") Date endDate);

	List<StatisticeResult> getSuccessWithdrawedCoin(@Param("uid") Integer uid);

	List<StatisticeResult> withdrawStaitstic();
}