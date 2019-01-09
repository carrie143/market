package com.gop.match.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;
import com.gop.domain.StatisticeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gop.domain.TradeCountResult;
import com.gop.domain.TradeMatchResult;
import com.gop.domain.enums.TradeCoinType;
import com.gop.mapper.TradeMatchResultMapper;
import com.gop.match.service.TradeRecordService;
import com.gop.mode.vo.PageModel;
import com.gop.util.BigDecimalUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TradeRecordServiceImpl implements TradeRecordService {
	@Autowired
	TradeMatchResultMapper tradeMatchResultMapper;

	@Override
	public void insert(TradeMatchResult tradeMatchResult) {
		tradeMatchResultMapper.insertSelective(tradeMatchResult);
	}

	@Override
	public PageModel<TradeMatchResult> getTradeMatchResultByTradeType(int uid, TradeCoinType tradeCoinType, int pageNo,
			int pageSize) {
		PageHelper.orderBy("createTime desc");
		PageHelper.startPage(pageNo, pageSize);
		List<TradeMatchResult> tradeMatchResults = null;
		if (TradeCoinType.BUY.equals(tradeCoinType)) {
			tradeMatchResults = tradeMatchResultMapper.getRecordByBuyUid(uid);
		} else {
			tradeMatchResults = tradeMatchResultMapper.getRecordBySellUid(uid);
		}
		PageModel<TradeMatchResult> pageModel = new PageModel<>();
		if (null == tradeMatchResults || tradeMatchResults.isEmpty()) {
			return null;
		}
		Page<TradeMatchResult> page = (Page<TradeMatchResult>) tradeMatchResults;
		pageModel.setPageNo(pageNo);
		pageModel.setPageNum(page.getPageNum());
		pageModel.setPageSize(pageSize);
		pageModel.setList(tradeMatchResults);
		return pageModel;
	}

	@Override
	public PageModel<TradeMatchResult> getTradeMatchResult(int pageNo, int pageSize) {
		PageHelper.orderBy("createTime desc");
		PageHelper.startPage(pageNo, pageSize);
		List<TradeMatchResult> tradeMatchResults = null;
		tradeMatchResults = tradeMatchResultMapper.getMatchResult();
		PageModel<TradeMatchResult> pageModel = new PageModel<>();
		if (null == tradeMatchResults || tradeMatchResults.isEmpty()) {
			return null;
		}
		Page<TradeMatchResult> page = (Page<TradeMatchResult>) tradeMatchResults;
		pageModel.setPageNo(pageNo);
		pageModel.setPageNum(page.getPages());
		pageModel.setPageSize(pageSize);
		pageModel.setList(tradeMatchResults);
		return pageModel;
	}

	@Override
	public PageModel<TradeMatchResult> getTradeMatchResult(String transaction, TradeCoinType tradeCoinType, int pageNo,
			int pageSize) {
		PageHelper.orderBy("create_time desc");
		PageHelper.startPage(pageNo, pageSize);
		List<TradeMatchResult> tradeMatchResults = null;
		if (TradeCoinType.BUY.equals(tradeCoinType)) {
			tradeMatchResults = tradeMatchResultMapper.getTradeByBuyInnerOrderNo(transaction);
		} else {
			tradeMatchResults = tradeMatchResultMapper.getTradeBySellInnerOrderNo(transaction);
		}
		PageModel<TradeMatchResult> pageModel = new PageModel<>();
		if (null == tradeMatchResults || tradeMatchResults.isEmpty()) {
			return null;
		}
		Page<TradeMatchResult> page = (Page<TradeMatchResult>) tradeMatchResults;
		pageModel.setPageNo(pageNo);
		pageModel.setPageNum(page.getPages());
		pageModel.setPageSize(pageSize);
		pageModel.setList(tradeMatchResults);
		return pageModel;
	}

	@Override
	public PageInfo<TradeMatchResult> queryOrders(Integer brokerId, String symbol, String buyTid, Integer buyUid,
			String sellTid, Integer sellUid, int pageNo, int pageSize) {
		PageHelper.orderBy("create_time desc");
		PageHelper.startPage(pageNo, pageSize);
		return new PageInfo<>(tradeMatchResultMapper.queryOrders(brokerId, symbol, buyTid, buyUid, sellTid, sellUid));
	}

	@Override
	public PageInfo<TradeMatchResult> queryOrdersResult(Integer brokerId, String symbol, Date beginTime, Date endTime, Integer uid,
																											String tid, int pageNo, int pageSize) {
		int offset = (pageNo -1) * pageSize;
		return new PageInfo<>(tradeMatchResultMapper.queryTradeOrders(brokerId, symbol, beginTime, endTime, uid, tid, offset,pageSize));
	}

	@Override
	public TradeCountResult countTradeMatchResult(Integer brokerId, String symbol, Date beginTime, Date endTime,
																								Integer uid, String tid) {

		return tradeMatchResultMapper.countTradeMatchResult(brokerId, symbol, beginTime, endTime, uid, tid);
	}

	@Override
	public Map<String,BigDecimal> culBuyFeeByAsset(String assetCode,Date startDate,Date endDate) {
		List<StatisticeResult> statisticeResults = tradeMatchResultMapper.getBuyFee(assetCode,startDate,endDate);
		Map<String,BigDecimal> buyFee = Maps.newHashMap();
		for(StatisticeResult result : statisticeResults){
			if(result != null && result.getSymbol() != null) {
				buyFee.put(result.getSymbol().split("_")[1], result.getTradeNumber());
			}
		}
		return buyFee;
	}

	@Override
	public Map<String,BigDecimal> culSellFeeByAsset(String assetCode,Date startDate,Date endDate) {
		List<StatisticeResult> statisticeResults = tradeMatchResultMapper.getSellFee(assetCode,startDate,endDate);
		Map<String,BigDecimal> sellFee = Maps.newHashMap();
		for(StatisticeResult result : statisticeResults){
			if(result != null && result.getSymbol() != null) {
				sellFee.put(result.getSymbol().split("_")[0], result.getTradeNumber());
			}
		}
		return sellFee;
	}

	@Override
	public List<StatisticeResult> culTradeAmountByUid(Integer uid) {
		return tradeMatchResultMapper.getTradeAmount(uid);
	}

	@Override
	public List<StatisticeResult> getNumberBySymbol() {
		return tradeMatchResultMapper.getNumberBySymbol();
	}
}
