package com.gop.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.DepositCurrencyOrderUser;
import com.gop.domain.enums.WithdrawCurrencyOrderStatus;

public interface DepositCurrencyOrderUserMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(DepositCurrencyOrderUser record);

	int insertSelective(DepositCurrencyOrderUser record);

	DepositCurrencyOrderUser selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(DepositCurrencyOrderUser record);

	int updateByPrimaryKey(DepositCurrencyOrderUser record);

	List<DepositCurrencyOrderUser> selectTransferCnyInList(@Param("uid") Integer uid);

	
	
	DepositCurrencyOrderUser selectForUpdate(Integer id);
	/**
	 * 查询人民币转出历史
	 * 
	 * Description: <br>
	 * 修改人：zhangxianglong
	 * 
	 * @param uid
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @see
	 */
	List<DepositCurrencyOrderUser> selectTransferCnyOutList(@Param("uid") Integer uid);
	
	
	List<DepositCurrencyOrderUser> getDepositListByAssetcode(@Param("uid") Integer uid,@Param("assetCode") String assetCode);


	public List<DepositCurrencyOrderUser> getDepositOrderList(@Param("brokerId") Integer brokerId, @Param("id") Integer id, @Param("uid") Integer uid,@Param("orderNo") String orderNo,
			@Param("account") String account, @Param("name") String name, @Param("acnumber") String acnumber,@Param("status") WithdrawCurrencyOrderStatus status);


	BigDecimal selectTransferCnyOutSum(@Param("uid") Integer uid);

	int selectTransOutTimeoutSum();

	DepositCurrencyOrderUser getTransferCnyByTxid(@Param("txid") String txid);

	DepositCurrencyOrderUser selectByPrimaryKeyLock(Integer id);

//	List<DepositCurrencyOrderUser> getTransferCnyByNameAndAccountNo(@Param("name") String name,
//			@Param("accountNo") String accountNo);

	int updateCnyRechargeByTimeOut();
	
	
	List<DepositCurrencyOrderUser> selectLastThrityOrderByWaitAndSup3d();
	
}