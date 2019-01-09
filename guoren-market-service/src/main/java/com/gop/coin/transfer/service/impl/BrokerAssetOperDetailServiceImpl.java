package com.gop.coin.transfer.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gop.domain.StatisticeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.coin.transfer.service.BrokerAssetOperDetailService;
import com.gop.domain.BrokerAssetOperDetail;
import com.gop.mapper.BrokerAssetOperDetailMapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class BrokerAssetOperDetailServiceImpl implements BrokerAssetOperDetailService{
	
	@Autowired
	private BrokerAssetOperDetailMapper brokerAssetOperDetailMapper;

	
	@Override
	public void addBrokerAssetOperDetail(BrokerAssetOperDetail brokerAssetOperDetail) {
		
		brokerAssetOperDetailMapper.addBrokerAssetOperDetail(brokerAssetOperDetail);
	}

	@Override
	public PageInfo<BrokerAssetOperDetail> getBrokerAssetDetail(Integer uid, String assetCode, Date startDate, Date endDate, Integer pageNo, Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		PageHelper.orderBy("create_date desc");
		return new PageInfo<>(brokerAssetOperDetailMapper.selectSelective(uid,assetCode,startDate,endDate));
	}

	@Override
	public List<StatisticeResult> getBroketAssetByAsset(String assetCode, Date startDate, Date endDate) {
		return brokerAssetOperDetailMapper.getBroketAssetByAsset(assetCode,startDate,endDate);
	}

}
