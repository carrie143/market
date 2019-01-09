package com.gop.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.gop.domain.*;
import org.apache.ibatis.annotations.Param;

public interface TradeMatchResultMapper {
	int deleteByPrimaryKey(Long id);

	int insertSelective(TradeMatchResult record);

	int updateByPrimaryKeySelective(TradeMatchResult record);

	int updateByPrimaryKey(TradeMatchResult record);

	TradeMatchResult selectByPrimaryKey(TradeRecordKey key);

	List<TradeMatchResult> getTradeByBuyInnerOrderNo(String transcationNo);

	List<TradeMatchResult> getTradeBySellInnerOrderNo(String transcationNo);

	List<TradeMatchResult> getRecordByBuyUid(Integer uid);

	List<TradeMatchResult> getRecordBySellUid(Integer uid);

	List<TradeMatchResult> getMatchResult();

	List<TradeMatchResult> queryOrders(@Param("brokerId") Integer brokerId, @Param("symbol") String symbol,
			@Param("buyTid") String buyTid, @Param("buyUid") Integer buyUid, @Param("sellTid") String sellTid,
			@Param("sellUid") Integer sellUid);

	List<TradeMatchResult> queryTradeOrders(@Param("brokerId") Integer brokerId, @Param("symbol") String symbol,
																					@Param("beginTime")  Date beginTime, @Param("endTime") Date endTime,
																					@Param("uid") Integer uid, @Param("tid") String tid,
																					@Param("offset") int offset,@Param("pageSize") int pageSize);

	TradeCountResult countTradeMatchResult(@Param("brokerId") Integer brokerId, @Param("symbol") String symbol,
																				 @Param("beginTime")  Date beginTime, @Param("endTime") Date endTime,
																				 @Param("uid") Integer uid, @Param("tid") String tid);
	List<StatisticeResult> getSellFee(@Param("assetCode") String assetCode,
						  @Param("startDate")  Date startDate, @Param("endDate") Date endDate);

	List<StatisticeResult> getBuyFee(@Param("assetCode") String assetCode,
						  @Param("startDate")  Date startDate, @Param("endDate") Date endDate);

	List<StatisticeResult> getTradeAmount(@Param("uid")  Integer uid);

	List<StatisticeResult> getNumberBySymbol();
}