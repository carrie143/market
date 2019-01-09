package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.WithdrawCurrencyOrderUser;
import com.gop.domain.enums.WithdrawCoinOrderStatus;
import com.gop.domain.enums.WithdrawCurrencyOrderStatus;
import com.gop.domain.enums.WithdrawCurrencyPayMode;

public interface WithdrawCurrencyOrderUserMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(WithdrawCurrencyOrderUser record);

	int insertSelective(WithdrawCurrencyOrderUser record);

	WithdrawCurrencyOrderUser selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(WithdrawCurrencyOrderUser record);

	int updateByPrimaryKey(WithdrawCurrencyOrderUser record);

	List<WithdrawCurrencyOrderUser> selectByStatusAndPayMode(@Param("status") WithdrawCurrencyOrderStatus status,@Param("payMode") WithdrawCurrencyPayMode payMode);
	
	WithdrawCurrencyOrderUser selectByTxId(@Param("txid") String txid);
	
	WithdrawCurrencyOrderUser selectByUidAndOuterOrderNo(@Param("uid") Integer uid, @Param("outerOrderNo") String outerOrderNo);

	WithdrawCurrencyOrderUser selectByTxIdForUpdate(@Param("txid") String txid);

	WithdrawCurrencyOrderUser selectForUpdate(Integer id);

	List<WithdrawCurrencyOrderUser> getUserOrdersByAsset(@Param("uid") Integer uid,
			@Param("assetCode") String assetCode);

	List<WithdrawCurrencyOrderUser> getWithdrawOrderList(@Param("brokerId") Integer brokerId, @Param("id") Integer id, @Param("uid") Integer uid, @Param("internalOrderId") String internalOrderId, 
			@Param("name") String name, @Param("account") String account, @Param("status") WithdrawCurrencyOrderStatus status,
			@Param("payMode") String payMode);

}