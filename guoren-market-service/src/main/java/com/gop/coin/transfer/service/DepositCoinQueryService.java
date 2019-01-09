package com.gop.coin.transfer.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gop.domain.DepositCoinOrderUser;
import com.gop.domain.StatisticeResult;
import com.gop.domain.enums.DepositCoinAssetStatus;

public interface DepositCoinQueryService {

	public PageInfo<DepositCoinOrderUser> queryOrder(Integer brokerId, Integer id, String account, Integer uId,
			String address, String txid, String assetCode, Date beginTime ,Date endTime,String emial ,DepositCoinAssetStatus status, Integer pageNo, Integer pageSize);

	public List<DepositCoinOrderUser> queryOrder(Integer uId, Date fromDate, Date toDate);
	
	public PageInfo<DepositCoinOrderUser> getTransferList(int uid, String assetCode, Integer pageSize, Integer pageNo);

	BigDecimal getTotalDeposit(String assetcode,DepositCoinAssetStatus status,Date startDate,Date endDate);
	List<StatisticeResult> getTotalDeposits(String assetcode,DepositCoinAssetStatus status,Date startDate,Date endDate);
	List<StatisticeResult> depositStaitstic();
}
