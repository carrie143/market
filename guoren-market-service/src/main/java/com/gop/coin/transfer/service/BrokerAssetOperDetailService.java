package com.gop.coin.transfer.service;

import com.github.pagehelper.PageInfo;
import com.gop.domain.BrokerAssetOperDetail;
import com.gop.domain.StatisticeResult;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface BrokerAssetOperDetailService {
	
	public void addBrokerAssetOperDetail(BrokerAssetOperDetail brokerAssetOperDetail);

	PageInfo<BrokerAssetOperDetail> getBrokerAssetDetail(Integer uid, String assetCode, Date startDate,Date endDate, Integer pageNo,Integer pageSize);

	List<StatisticeResult> getBroketAssetByAsset(String assetCode, Date startDate, Date endDate);
}
