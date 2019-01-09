package com.gop.mapper;

import java.util.Date;
import java.util.List;

import com.gop.domain.enums.TradeCoinFlag;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gop.domain.TradeMatchResult;
import com.gop.domain.TradeOrder;
import com.gop.domain.enums.SendStatus;
import com.gop.domain.enums.TradeCoinStatus;
import com.gop.domain.enums.TradeCoinType;

public interface TradeOrderMapper {

	int insertSelective(TradeOrder record);

	TradeOrder selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(TradeOrder record);

	List<TradeOrder> getHistoryTradeOrder(@Param("uid") Integer uid);

	List<TradeOrder> getProcessingTradeOrder(@Param("uid") Integer uid);

	TradeOrder getTradeGopWithLock(@Param("id") Integer id);

	TradeOrder getByInternalOrderNo(@Param("internalOrderNo") String internalOrderNo);

	TradeOrder getByOuterOrderNo(@Param("outerOrderNo") String outerOrderNo,@Param("uid") Integer uid);

	List<TradeOrder> getTradeOrder(@Param("uid") Integer uid);

	List<TradeOrder> getHistoryTradeOrderSymbol(@Param("uid") Integer userId, @Param("symbol") String symbol);

	List<TradeOrder> getProcessingTradeOrderSymbol(@Param("uid") Integer uid, @Param("symbol") String symbol);

	public List<TradeOrder> getTradeList(@Param("brokerId") Integer brokerId, @Param("innerOrderId") String innerOrderId,
			@Param("accountId") Integer accountId, @Param("symbol") String symbol, @Param("uid") Integer uId,
			@Param("type") TradeCoinType type, @Param("status") TradeCoinStatus status);

	void updateSendStatusByInnerOrderNo(@Param("sendStatus") SendStatus sendStatus,
			@Param("innerOrderNo") String innerOrderNo);

	List<TradeOrder> getUnSendMatchOrderByStatus(@Param("status") TradeCoinStatus tradeCoinStatus);

	List<TradeOrder> getTradeRecordList(@Param("brokerId") Integer brokerId, @Param("tradeFlag") TradeCoinFlag tradeFlag, @Param("uid") Integer uId,
										@Param("symbol") String symbol, @Param("orderType") TradeCoinType orderType, @Param("status") TradeCoinStatus status,
										@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}