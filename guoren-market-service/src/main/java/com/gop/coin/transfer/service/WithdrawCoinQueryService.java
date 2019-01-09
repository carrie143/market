package com.gop.coin.transfer.service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.gop.domain.StatisticeResult;
import com.gop.domain.WithdrawCoinOrderUser;
import com.gop.domain.enums.WithdrawCoinOrderStatus;
import com.gop.domain.enums.WithdrawCoinOrderType;

public interface WithdrawCoinQueryService {

	public PageInfo<WithdrawCoinOrderUser> getTransferList(int uid, String assetCode, Integer pageSize, Integer pageNo);
	
	public PageInfo<WithdrawCoinOrderUser> queryOrder(Integer brokerId, Integer id, String account, Integer uId,
			String address, String txid, String assetCode, Date beginTime,Date endTime,String email,WithdrawCoinOrderStatus status, Integer pageNo, Integer pageSize);
	public PageInfo<WithdrawCoinOrderUser> queryAlreadyProcessedOrder(Integer brokerId, Integer id, String account, Integer uId,
			String address, String txid, String assetCode, Date beginTime,Date endTime,String email,WithdrawCoinOrderType status,String orderBy, Integer pageNo, Integer pageSize);

	public WithdrawCoinOrderUser getTransferOutOrderByExternalOrderId(int uid,String outOrder);

	public WithdrawCoinOrderUser getOrderById(int id);

	Map<String ,Map<String,BigDecimal>> getTotalWithdrawedCoinValue(String assetCode, Date startDate, Date endDate);

	List<StatisticeResult> getSuccessWithdrawedCoin(Integer uid);

	public List<StatisticeResult> withdrawStaitstic();
}
