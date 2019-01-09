//package com.gop.match.facade;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DuplicateKeyException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.gop.asset.dto.AssetOperationDto;
//import com.gop.domain.ConfigSymbol;
//import com.gop.domain.TradeMatchResult;
//import com.gop.domain.TradeOrder;
//import com.gop.domain.enums.ConfigSymbolType;
//import com.gop.domain.enums.TradeCoinType;
//import com.gop.financecheck.enums.AccountClass;
//import com.gop.financecheck.enums.AccountSubject;
//import com.gop.financecheck.enums.BusinessSubject;
//import com.gop.match.service.ConfigSymbolProfileService;
//import com.gop.match.service.MatchOrderService;
//import com.gop.match.service.SymbolService;
//import com.gop.match.service.TradeRecordService;
//import com.gop.match.service.UserTransactionFeeWhiteListService;
//import com.gop.util.BigDecimalUtils;
//import com.gop.util.SequenceUtil;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Service
//@Slf4j
//public class MatchTradeFacadeImpl implements MatchTradeFacade {
//	@Autowired
//	private SymbolService symbolService;
//
//	@Autowired
//	private ConfigSymbolProfileService configSymbolProfileService;
//
//	@Autowired
//	private TradeRecordService tradeRecordService;
//
//	@Autowired
//	private MatchOrderService matchOrderService;
//
//	@Autowired
//	private UserTransactionFeeWhiteListService userTransactionFeeWhiteListService;
//
//	@Override
//	@Transactional
//	public void matchRecord(String buyRequestNo, String sellRequestNo, Integer buyId, Integer sellId, BigDecimal num,
//			BigDecimal price, String symbol) {
//
//		TradeOrder buyTradeOrder = matchOrderService.queryTradeOrderInnerOrderNo(buyRequestNo);
//		TradeOrder sellTradeOrder = matchOrderService.queryTradeOrderInnerOrderNo(sellRequestNo);
//		// 生成撮合成功买入订单号
//		String buySuccessOrder = SequenceUtil.getNextId();
//		// 生成撮合成功卖出订单号
//		String sellSuccessOrder = SequenceUtil.getNextId();
//		TradeMatchResult tradeResult = new TradeMatchResult();
//		tradeResult.setBuyInnerOrderNo(buyRequestNo);
//		tradeResult.setSellInnerOrderNo(sellRequestNo);
//		tradeResult.setBuyRequestNo(buySuccessOrder);
//		tradeResult.setSellRequestNo(sellSuccessOrder);
//		tradeResult.setBuyUid(buyId);
//		tradeResult.setSellUid(sellId);
//		tradeResult.setSymbol(symbol);
//		tradeResult.setBuyBrokerId(buyTradeOrder.getBrokerId());
//		tradeResult.setSellBrokerId(sellTradeOrder.getBrokerId());
//		tradeResult.setNumber(num);
//		tradeResult.setPrice(price);
//
//		tradeResult.setCreateTime(new Date());
//
//		BigDecimal buyFee = BigDecimal.ZERO;
//		if (!userTransactionFeeWhiteListService.checkUserinWhiteList(buyId)) {
//			BigDecimal buyFeeRate = configSymbolProfileService.getBigDecimalValue(tradeResult.getSymbol(),
//					ConfigSymbolType.ASSETFEERATE);
//			BigDecimal buyMinfee = configSymbolProfileService.getBigDecimalValue(tradeResult.getSymbol(),
//					ConfigSymbolType.ASSETMINFEE);
//			BigDecimal fee = num.multiply(buyFeeRate);
//			buyFee = BigDecimalUtils.isBiggerOrEqual(fee, buyMinfee) ? fee : BigDecimal.ZERO;
//		}
//
//		BigDecimal sellFee = BigDecimal.ZERO;
//		if (!userTransactionFeeWhiteListService.checkUserinWhiteList(sellId)) {
//			BigDecimal sellFeeRate = configSymbolProfileService.getBigDecimalValue(tradeResult.getSymbol(),
//					ConfigSymbolType.ASSETFEERATE);
//			BigDecimal sellMinfee = configSymbolProfileService.getBigDecimalValue(tradeResult.getSymbol(),
//					ConfigSymbolType.ASSETMINFEE);
//			BigDecimal fee = num.multiply(tradeResult.getPrice()).multiply(sellFeeRate);
//			sellFee = BigDecimalUtils.isBiggerOrEqual(fee, sellMinfee) ? fee : BigDecimal.ZERO;
//		}
//
//		tradeResult.setBuyFee(buyFee);
//		tradeResult.setSellFee(sellFee);
//
//		try {
//			CreateTradeMatchResult(tradeResult);
//		} catch (DuplicateKeyException e) {
//			log.info("收到重复的消息:{}", tradeResult);
//			return;
//		}
//		List<AssetOperationDto> operationDtos = null;
//		if (buyId > sellId) {
//			operationDtos = updateMatchOrder(buyId, buyRequestNo, buySuccessOrder, symbol, TradeCoinType.BUY, num,
//					price, buyFee);
//			operationDtos.addAll(updateMatchOrder(sellId, sellRequestNo, sellSuccessOrder, symbol, TradeCoinType.SELL,
//					num, price, sellFee));
//		} else {
//			operationDtos = updateMatchOrder(sellId, sellRequestNo, sellSuccessOrder, symbol, TradeCoinType.SELL, num,
//					price, sellFee);
//			operationDtos.addAll(updateMatchOrder(buyId, buyRequestNo, buySuccessOrder, symbol, TradeCoinType.BUY, num,
//					price, buyFee));
//		}
//	}
//
//	private void CreateTradeMatchResult(TradeMatchResult tradeResult) {
//		log.info("" + tradeResult);
//		tradeRecordService.insert(tradeResult);
//
//	}
//
//	/**
//	 *
//	 * @param uid
//	 * @param symbol
//	 * @param num
//	 * @param price
//	 * @return 退款金额
//	 */
//	private List<AssetOperationDto> updateMatchOrder(int uid, String innerNo, String requestNo, String symbol,
//			TradeCoinType cointype, BigDecimal num, BigDecimal price, BigDecimal fee) {
//
//		ConfigSymbol configSymbol = symbolService.getConfigSymbolBYSymbol(symbol);
//
//		if (cointype.equals(TradeCoinType.BUY)) {
//
//			BigDecimal unlock = matchOrderService.updateMatchOrder(innerNo, price, num, fee);
//
//			return buildMatchOrderBuyAssetOperationDto(uid, requestNo, configSymbol.getAssetCode1(),
//					configSymbol.getAssetCode2(), num, price, unlock, fee);
//		} else {
//
//			BigDecimal unlock = matchOrderService.updateMatchOrder(innerNo, price, num, fee);
//			return buildMatchOrderSellAssetOperationDto(uid, requestNo, configSymbol.getAssetCode1(),
//					configSymbol.getAssetCode2(), num, price, unlock, fee);
//		}
//
//	}
//
//	private List<AssetOperationDto> buildMatchOrderBuyAssetOperationDto(int uid, String requestNo, String assetCode1,
//			String assetCode2, BigDecimal num, BigDecimal price, BigDecimal unlock, BigDecimal fee) {
//		List<AssetOperationDto> lists = new ArrayList<>();
//
//		AssetOperationDto assetOperationDtoUnlock = new AssetOperationDto();
//		assetOperationDtoUnlock.setAccountClass(AccountClass.EXPENSE);
//		assetOperationDtoUnlock.setAccountSubject(AccountSubject.MATCH_OUT);
//		assetOperationDtoUnlock.setBusinessSubject(BusinessSubject.MATCH_SELL);
//		assetOperationDtoUnlock.setAssetCode(assetCode1);
//		assetOperationDtoUnlock.setLockAmount(unlock.add(price.multiply(num)).negate());
//		assetOperationDtoUnlock.setAmount(unlock);
//		assetOperationDtoUnlock.setUid(uid);
//		assetOperationDtoUnlock.setLoanAmount(BigDecimal.ZERO);
//		assetOperationDtoUnlock.setMemo("解冻");
//		assetOperationDtoUnlock.setRequestNo(requestNo);
//		assetOperationDtoUnlock.setIndex(0);
//		lists.add(assetOperationDtoUnlock);
//
//		// 获得
//		AssetOperationDto assetOperationDtoAc = new AssetOperationDto();
//		assetOperationDtoAc.setAccountClass(AccountClass.INCOME);
//		assetOperationDtoAc.setAccountSubject(AccountSubject.MATCH_IN);
//		assetOperationDtoAc.setBusinessSubject(BusinessSubject.MATCH_BUY);
//		assetOperationDtoAc.setAssetCode(assetCode2);
//		assetOperationDtoAc.setAmount(num);
//		assetOperationDtoAc.setLoanAmount(BigDecimal.ZERO);
//		assetOperationDtoAc.setLockAmount(BigDecimal.ZERO);
//		assetOperationDtoAc.setUid(uid);
//		assetOperationDtoAc.setMemo("买入获得");
//		assetOperationDtoAc.setRequestNo(requestNo);
//		assetOperationDtoAc.setIndex(1);
//		lists.add(assetOperationDtoAc);
//
//		// 手续费
//		if (!BigDecimalUtils.isEqualZero(fee)) {
//			AssetOperationDto assetOperationDtoFee = new AssetOperationDto();
//			assetOperationDtoFee.setAccountClass(AccountClass.INCOME);
//			assetOperationDtoFee.setAccountSubject(AccountSubject.FEE_MATCH_SPEND);
//			assetOperationDtoFee.setBusinessSubject(BusinessSubject.FEE);
//			assetOperationDtoFee.setAssetCode(assetCode2);
//			assetOperationDtoFee.setLockAmount(BigDecimal.ZERO);
//			assetOperationDtoFee.setLoanAmount(BigDecimal.ZERO);
//			assetOperationDtoFee.setAmount(fee.negate());
//			assetOperationDtoFee.setUid(uid);
//			assetOperationDtoFee.setMemo("买入手续费");
//			assetOperationDtoFee.setRequestNo(requestNo);
//			assetOperationDtoFee.setIndex(2);
//			lists.add(assetOperationDtoFee);
//		}
//
//		return lists;
//	}
//
//	private List<AssetOperationDto> buildMatchOrderSellAssetOperationDto(int uid, String requestNo, String assetCode1,
//			String assetCode2, BigDecimal num, BigDecimal price, BigDecimal unlock, BigDecimal fee) {
//		List<AssetOperationDto> lists = new ArrayList<>();
//
//		AssetOperationDto assetOperationDtoUnlock = new AssetOperationDto();
//		assetOperationDtoUnlock.setAccountClass(AccountClass.EXPENSE);
//		assetOperationDtoUnlock.setAccountSubject(AccountSubject.MATCH_OUT);
//		assetOperationDtoUnlock.setBusinessSubject(BusinessSubject.MATCH_SELL);
//		assetOperationDtoUnlock.setAssetCode(assetCode2);
//		assetOperationDtoUnlock.setLockAmount(num.negate());
//		assetOperationDtoUnlock.setAmount(BigDecimal.ZERO);
//		assetOperationDtoUnlock.setLoanAmount(BigDecimal.ZERO);
//		assetOperationDtoUnlock.setUid(uid);
//		assetOperationDtoUnlock.setMemo("卖出解冻");
//		assetOperationDtoUnlock.setRequestNo(requestNo);
//		assetOperationDtoUnlock.setIndex(0);
//		lists.add(assetOperationDtoUnlock);
//		// 获得
//		AssetOperationDto assetOperationDtoAc = new AssetOperationDto();
//		assetOperationDtoAc.setAccountClass(AccountClass.INCOME);
//		assetOperationDtoAc.setAccountSubject(AccountSubject.MATCH_IN);
//		assetOperationDtoAc.setBusinessSubject(BusinessSubject.MATCH_BUY);
//		assetOperationDtoAc.setAssetCode(assetCode1);
//		assetOperationDtoAc.setAmount(num.multiply(price));
//		assetOperationDtoAc.setLoanAmount(BigDecimal.ZERO);
//		assetOperationDtoAc.setLockAmount(BigDecimal.ZERO);
//		assetOperationDtoAc.setUid(uid);
//		assetOperationDtoAc.setMemo("卖出获得");
//		assetOperationDtoAc.setRequestNo(requestNo);
//		assetOperationDtoAc.setIndex(1);
//		lists.add(assetOperationDtoAc);
//
//		// 手续费
//		if (!BigDecimalUtils.isEqualZero(fee)) {
//			AssetOperationDto assetOperationDtoFee = new AssetOperationDto();
//			assetOperationDtoFee.setAccountClass(AccountClass.LIABILITY);
//			assetOperationDtoFee.setAccountSubject(AccountSubject.FEE_MATCH_SPEND);
//			assetOperationDtoFee.setBusinessSubject(BusinessSubject.FEE);
//			assetOperationDtoFee.setAssetCode(assetCode1);
//			assetOperationDtoFee.setLockAmount(BigDecimal.ZERO);
//			assetOperationDtoFee.setLoanAmount(BigDecimal.ZERO);
//			assetOperationDtoFee.setAmount(fee.negate());
//			assetOperationDtoFee.setUid(uid);
//			assetOperationDtoFee.setMemo("卖出手续费");
//			assetOperationDtoFee.setRequestNo(requestNo);
//			assetOperationDtoFee.setIndex(2);
//			lists.add(assetOperationDtoFee);
//		}
//		return lists;
//	}
//}
