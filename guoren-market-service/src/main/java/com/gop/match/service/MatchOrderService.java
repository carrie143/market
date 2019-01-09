//package com.gop.match.service;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.List;
//
//import com.github.pagehelper.PageInfo;
//import com.gop.domain.TradeOrder;
//import com.gop.domain.enums.SendStatus;
//import com.gop.domain.enums.TradeCoinFlag;
//import com.gop.domain.enums.TradeCoinStatus;
//import com.gop.domain.enums.TradeCoinType;
//import com.gop.exchange.model.Trade;
//import com.gop.match.dto.MatchOrderDto;
//import com.gop.mode.vo.PageModel;
//
//public interface MatchOrderService {
//
//	public String createMacthOrder(int brokerId, MatchOrderDto matchOrderDto);
//
//	public Trade payMatchOrderByInnerOrderNo(String innerOrderNo);
//
//	public Trade payMatchOrderByOuterOrderNo(int uid,String outerOrderNo);
//
//	public Trade createAndPayMatchOrder(Integer brokerId, MatchOrderDto matchOrderDto);
//
//	public TradeOrder queryTradeOrderbyOuterOrderNo(int uid,String outerOrderNo);
//
//	public TradeOrder queryTradeOrderInnerOrderNo(String innerOrderNo);
//
//	public PageModel<TradeOrder> queryTradeOrderByPage(Integer uid, Integer pageNo, Integer pageSize);
//
//	public PageModel<TradeOrder> queryHistoryTradeOrderByPage(Integer uid, Integer pageNo, Integer pageSize);
//
//	public PageModel<TradeOrder> queryCurrentTradeOrderByPage(Integer uid, Integer pageNo, Integer pageSize);
//
//	public void cancelOrder(Integer userId, String macthOrderNo, String symbol, BigDecimal price, BigDecimal marketOver,
//			BigDecimal numberOver);
//
//	public PageModel<TradeOrder> queryHistoryTradeOrderByPage(String symbol, Integer userId, Integer pageNo,
//			Integer pageSize);
//
//	public PageModel<TradeOrder> queryCurrentTradeOrderByPage(Integer userId, String symbol, Integer pageNo,
//			Integer pageSize);
//
//	public BigDecimal updateMatchOrder(String innerOrderNo, BigDecimal price, BigDecimal num,BigDecimal fee);
//
//	public void updateSendStatusByInnerOrderNo(SendStatus sendStatus, String innerOrderNo);
//
//	/**
//	 * 控制中心查询撮合历史接口
//	 *
//	 * @param brokerId
//	 * @param accountId
//	 * @param symbol
//	 * @param uId
//	 * @param type
//	 * @param status
//	 * @param pageNo
//	 * @param pageSize
//	 * @return
//	 */
//	PageInfo<TradeOrder> userTradeList(Integer brokerId,String innerOrderId, Integer accountId, String symbol,
//			Integer uId, TradeCoinType type, TradeCoinStatus status, int pageNo, int pageSize);
//
//	List<TradeOrder> getUnSendMatchOrderByStatus(TradeCoinStatus processing);
//
//
//	PageInfo<TradeOrder> getTradeOrderRecord(Integer brokerId, TradeCoinFlag tradeFlag, Integer uId, String symbol, TradeCoinType orderType, TradeCoinStatus status, Date startTime, Date endTime, Integer pageNo, Integer pageSize);
//
//}
