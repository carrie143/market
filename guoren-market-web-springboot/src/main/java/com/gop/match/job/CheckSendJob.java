//package com.gop.match.job;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.dangdang.ddframe.job.api.ShardingContext;
//import com.dangdang.ddframe.job.api.simple.SimpleJob;
//import com.gop.domain.TradeOrder;
//import com.gop.domain.enums.SendStatus;
//import com.gop.domain.enums.TradeCoinFlag;
//import com.gop.domain.enums.TradeCoinStatus;
//import com.gop.domain.enums.TradeCoinType;
//import com.gop.exchange.hessian.TradeServcie;
//import com.gop.exchange.model.Trade;
//import com.gop.exchange.model.modelenum.Flag;
//import com.gop.exchange.model.modelenum.Type;
//import com.gop.match.service.MatchOrderService;
//import com.gop.match.service.TradeServiceManager;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Component("checkSendJob")
//public class CheckSendJob implements SimpleJob {
//	@Autowired
//	MatchOrderService matchOrderService;
//	@Autowired
//	TradeServiceManager tradeServiceManager;
//
//	@Override
//	public void execute(ShardingContext arg0) {
//
//		List<TradeOrder> lists = matchOrderService.getUnSendMatchOrderByStatus(TradeCoinStatus.PROCESSING);
//		while (null != lists && lists.size() > 0) {
//			for (TradeOrder tradeOrder : lists) {
//				log.info("校验订单:{}", tradeOrder);
//				String symbol = tradeOrder.getSymbol();
//				TradeServcie tradeService = tradeServiceManager.getTradeService(symbol);
//				if (null == tradeService) {
//					log.error("未发现symbol为{}的撮合引擎", symbol);
//					continue;
//				}
//				String internalOrder = tradeOrder.getInnerOrderNo();
//				TradeCoinType tradeCoinType = tradeOrder.getOrderType();
//				boolean flag = false;
//				try {
//					flag = tradeService.hasTrade(internalOrder,
//							tradeCoinType.equals(TradeCoinType.BUY) ? Type.BUY : Type.SELL);
//				} catch (Exception e) {
//					log.error("调动撮合引擎错误, {}", e);
//					return;
//				}
//				if (!flag) {
//					log.info("发现没有进入撮合队列的订单：{}", tradeOrder);
//					Trade trade = new Trade();
//					trade.setFlag(tradeOrder.getTradeFlag().equals(TradeCoinFlag.FIXED) ? Flag.FIXED : Flag.MARKET);
//					trade.setMarket(tradeOrder.getMoney());
//					trade.setNumTotal(tradeOrder.getNumber());
//					trade.setPrice(tradeOrder.getPrice());
//					trade.setTransactionNo(internalOrder);
//					trade.setUid(tradeOrder.getUid());
//					trade.setType(tradeCoinType.equals(TradeCoinType.BUY) ? Type.BUY : Type.SELL);
//					try {
//						tradeService.trade(trade);
//					} catch (Exception e) {
//						log.error("下单出现异常:{}", e);
//						return;
//					}
//				}
//				matchOrderService.updateSendStatusByInnerOrderNo(SendStatus.SEND, internalOrder);
//				lists = matchOrderService.getUnSendMatchOrderByStatus(TradeCoinStatus.PROCESSING);
//			}
//		}
//
//	}
//
//}
