package com.gop.coin.transfer.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gop.domain.ConfigAsset;
import com.gop.domain.StatisticeResult;
import com.gop.domain.WithDrawStatistic;
import com.gop.mapper.ConfigAssetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gop.code.consts.CommonCodeConst;
import com.gop.coin.transfer.service.WithdrawCoinQueryService;
import com.gop.domain.WithdrawCoinOrderUser;
import com.gop.domain.enums.WithdrawCoinOrderStatus;
import com.gop.domain.enums.WithdrawCoinOrderType;
import com.gop.exception.AppException;
import com.gop.mapper.WithdrawCoinOrderUserMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WithdrawCoinQueryServiceImpl implements WithdrawCoinQueryService {

	@Autowired
	private WithdrawCoinOrderUserMapper withdrawCoinOrderUserMapper;
	@Autowired
	private ConfigAssetMapper configAssetMapper;

	@Override
	public PageInfo<WithdrawCoinOrderUser> queryOrder(Integer brokerId, Integer id, String account, Integer uId,
			String address, String txid, String assetCode,Date beginTime,Date endTime,String email, WithdrawCoinOrderStatus status, Integer pageNo,
			Integer pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		PageHelper.orderBy("id desc");
        
		return new PageInfo<>(withdrawCoinOrderUserMapper.getWithdrawOrderList(brokerId, id, account, uId, address, txid,
				assetCode,beginTime,endTime,email, status));
	}

	@Override
	public WithdrawCoinOrderUser getTransferOutOrderByExternalOrderId(int uid, String outOrder) {
		return withdrawCoinOrderUserMapper.selectByUidAndOutOrder(uid, outOrder);
	}

	@Override
	public WithdrawCoinOrderUser getOrderById(int id) {
		return withdrawCoinOrderUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageInfo<WithdrawCoinOrderUser> getTransferList(int uid, String assetCode, Integer pageSize,
			Integer pageNo) {
		PageHelper.startPage(pageNo, pageSize);
		PageHelper.orderBy("create_date desc");
		return new PageInfo<>(withdrawCoinOrderUserMapper.selectListByUidAndAssetCode(uid, assetCode));
	}

	@Override
	public PageInfo<WithdrawCoinOrderUser> queryAlreadyProcessedOrder(Integer brokerId, Integer id, String account,
			Integer uId, String address, String txid, String assetCode, Date beginTime, Date endTime, String email,
			WithdrawCoinOrderType status,String orderBy, Integer pageNo, Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		switch (status) {
		case PROCESSED:
			PageHelper.orderBy(orderBy);
			return new PageInfo<WithdrawCoinOrderUser>(withdrawCoinOrderUserMapper.getWithdrawOrderAlreadyProcessedList(brokerId, id, account, uId, address, txid,
					assetCode,beginTime,endTime,email));
		case UNTREATED:
			PageHelper.orderBy(orderBy);
			return new PageInfo<WithdrawCoinOrderUser>(withdrawCoinOrderUserMapper.getWithdrawOrderUntreatedList(brokerId, id, account, uId, address, txid,
					assetCode,beginTime,endTime,email));
		default:
		    PageHelper.orderBy(orderBy);
	        return new PageInfo<WithdrawCoinOrderUser>(withdrawCoinOrderUserMapper.queryWithdrawOrderList(brokerId, id, account, uId, address, txid,
					assetCode,beginTime,endTime,email, status));
		}
	}

	@Override
	public Map<String,Map<String,BigDecimal>> getTotalWithdrawedCoinValue(String assetCode, Date startDate, Date endDate) {
		List<WithDrawStatistic> withdrawCoinOrderUsers = withdrawCoinOrderUserMapper.getTotalWithdrawedCoinValue(assetCode, startDate, endDate);

		Map<String,Map<String,BigDecimal>> listMap = Maps.newHashMap();
		Map<String,BigDecimal> map1 = Maps.newHashMap();
		Map<String,BigDecimal> map2 = Maps.newHashMap();
		String asset = null;
		for (WithDrawStatistic withDrawStatistic : withdrawCoinOrderUsers) {
			asset = withDrawStatistic.getAssetCode();
			BigDecimal txfee = withDrawStatistic.getTxfee();
			map1.put(asset +"_"+withDrawStatistic.getStatus(),withDrawStatistic.getNumber());
			if(map2.containsKey(asset)) {
				txfee = map2.get(asset).add(txfee);
			}
			map2.put(asset,txfee);
		}
		listMap.put("number",map1);
		listMap.put("txfee",map2);
		return listMap;
	}

	@Override
	public List<StatisticeResult> getSuccessWithdrawedCoin(Integer uid) {
		return withdrawCoinOrderUserMapper.getSuccessWithdrawedCoin(uid);
	}

	@Override
	public List<StatisticeResult> withdrawStaitstic(){
		return withdrawCoinOrderUserMapper.withdrawStaitstic();
	}
}
