package com.gop.match.service;

import com.github.pagehelper.PageInfo;
import com.gop.domain.StatisticeResult;
import com.gop.domain.TradeCountResult;
import com.gop.domain.TradeMatchResult;
import com.gop.domain.enums.TradeCoinType;
import com.gop.mode.vo.PageModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TradeRecordService {

	public void insert(TradeMatchResult tradeMatchResult);

	public PageModel<TradeMatchResult> getTradeMatchResultByTradeType(int uid, TradeCoinType tradeCoinType, int pageNo,
			int pageSize);

	public PageModel<TradeMatchResult> getTradeMatchResult(int pageNo, int pageSize);

	public PageModel<TradeMatchResult> getTradeMatchResult(String transaction, TradeCoinType tradeCoinType, int pageNo,
			int pageSize);

	public PageInfo<TradeMatchResult> queryOrders(Integer brokerId, String symbol, String buyTid, Integer buyUid,
			String sellTid, Integer sellUid, int pageNo, int pageSize);

	public PageInfo<TradeMatchResult> queryOrdersResult(Integer brokerId, String symbol, Date beginTime, Date endTime, Integer uid,
																									String tid, int pageNo, int pageSize);

	TradeCountResult countTradeMatchResult(Integer brokerId, String symbol, Date beginTime, Date endTime, Integer uid, String tid);

	Map<String,BigDecimal> culBuyFeeByAsset(String assetCode, Date startDate, Date endDate);
	Map<String,BigDecimal> culSellFeeByAsset(String assetCode, Date startDate, Date endDate);
	List<StatisticeResult> culTradeAmountByUid(Integer uid);

	public List<StatisticeResult> getNumberBySymbol();
}
