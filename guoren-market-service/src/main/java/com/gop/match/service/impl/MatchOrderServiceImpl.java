//package com.gop.match.service.impl;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DuplicateKeyException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.google.common.base.Strings;
//import com.google.common.collect.Lists;
//import com.gop.asset.dto.AssetOperationDto;
//import com.gop.code.consts.CommonCodeConst;
//import com.gop.code.consts.MatchCodeConst;
//import com.gop.code.consts.OrderCodeConst;
//import com.gop.domain.ConfigSymbol;
//import com.gop.domain.TradeOrder;
//import com.gop.domain.enums.SendStatus;
//import com.gop.domain.enums.TradeCoinFlag;
//import com.gop.domain.enums.TradeCoinStatus;
//import com.gop.domain.enums.TradeCoinType;
//import com.gop.exception.AppException;
//import com.gop.exchange.model.Trade;
//import com.gop.exchange.model.modelenum.Flag;
//import com.gop.exchange.model.modelenum.Type;
//import com.gop.financecheck.enums.AccountClass;
//import com.gop.financecheck.enums.AccountSubject;
//import com.gop.financecheck.enums.BusinessSubject;
//import com.gop.mapper.TradeOrderMapper;
//import com.gop.match.dto.MatchOrderDto;
//import com.gop.match.service.MatchOrderService;
//import com.gop.match.service.PriceLimitService;
//import com.gop.match.service.SymbolService;
//import com.gop.mode.vo.PageModel;
//import com.gop.util.BigDecimalUtils;
//import com.gop.util.ResourceUtils;
//import com.gop.util.SequenceUtil;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Service
//@Slf4j
//public class MatchOrderServiceImpl implements MatchOrderService {
//
//	@Autowired
//	private TradeOrderMapper tradeOrderMapper;
//	private static Set<String> limitSymbol;
//
//	@Autowired
//	private SymbolService symbolService;
//
//	@Autowired
//	private PriceLimitService priceLimitService;
//	{
//		limitSymbol = new HashSet<>();
//		String symbols = ResourceUtils.get("common", "price_limit");
//		if (!Strings.isNullOrEmpty(symbols)) {
//			String[] sybolsArrays = symbols.split(",");
//			for (int i = 0; i < sybolsArrays.length; i++) {
//				limitSymbol.add(sybolsArrays[i]);
//			}
//		}
//	}
//
//	public String createMacthOrder(int brokerId, MatchOrderDto matchOrderDto) {
//		if (limitSymbol.contains(matchOrderDto.getSymbol())) {
//			if (matchOrderDto.getTradeCoinType().equals(TradeCoinType.BUY)) {
//				priceLimitService.limitBuy(matchOrderDto.getSymbol(), matchOrderDto.getPrice(),
//						matchOrderDto.getTradeCoinFlag());
//			} else {
//				priceLimitService.limitSell(matchOrderDto.getSymbol(), matchOrderDto.getPrice(),
//						matchOrderDto.getTradeCoinFlag());
//			}
//		}
//
//		TradeOrder tradeOrder = new TradeOrder();
//		tradeOrder.setUid(matchOrderDto.getUserId());
//		tradeOrder.setCreateDate(new Date());
//		tradeOrder.setOrderType(matchOrderDto.getTradeCoinType());
//		tradeOrder.setOuterOrderNo(matchOrderDto.getOutOrderNo());
//		String innerOrderNo = SequenceUtil.getNextId();
//		tradeOrder.setInnerOrderNo(innerOrderNo);
//		tradeOrder.setOrderType(matchOrderDto.getTradeCoinType());
//		tradeOrder.setBrokerId(brokerId);
//		if (matchOrderDto.getTradeCoinFlag().equals(TradeCoinFlag.FIXED)) {
//			tradeOrder.setTradeFlag(TradeCoinFlag.FIXED);
//			tradeOrder.setPrice(matchOrderDto.getPrice());
//		} else {
//			tradeOrder.setPrice(BigDecimalUtils.getZero());
//			tradeOrder.setTradeFlag(TradeCoinFlag.MARKET);
//			tradeOrder.setMoney(matchOrderDto.getMarket());
//			tradeOrder.setMoneyOver(matchOrderDto.getMarket());
//		}
//
//		tradeOrder.setNumber(null == matchOrderDto.getAmount() ? BigDecimalUtils.getZero() : matchOrderDto.getAmount());
//		tradeOrder.setNumberOver(
//				null == matchOrderDto.getAmount() ? BigDecimalUtils.getZero() : matchOrderDto.getAmount());
//
//		tradeOrder.setStatus(TradeCoinStatus.WAITING);
//		tradeOrder.setSymbol(matchOrderDto.getSymbol());
//		tradeOrder.setTradedMoney(BigDecimalUtils.getZero());
//		tradeOrder.setTradedNumber(BigDecimalUtils.getZero());
//		tradeOrder.setAccountId(0);
//		tradeOrder.setUpdateDate(new Date());
//		tradeOrder.setSendStatus(SendStatus.WAIT);
//		try {
//			tradeOrderMapper.insertSelective(tradeOrder);
//		} catch (DuplicateKeyException e) {
//			throw new AppException(MatchCodeConst.DUPLICATE_OUT_ORDER);
//		} catch (Exception e) {
//			log.error("订单插入数据库异常：{}", e);
//			throw new AppException(CommonCodeConst.SERVICE_ERROR);
//		}
//
//		return innerOrderNo;
//	}
//
//	@Transactional
//	public Trade payMatchOrderByInnerOrderNo(String innerOrderNo) {
//		TradeOrder tradeOrder = queryTradeOrderInnerOrderNo(innerOrderNo);
//		if (tradeOrder == null) {
//			log.error("没有发现内部订单号{}", innerOrderNo);
//			throw new AppException(CommonCodeConst.SERVICE_ERROR);
//		}
//
//		Integer userId = tradeOrder.getUid();
//
//		ConfigSymbol configSymbol = symbolService.getConfigSymbolBYSymbol(tradeOrder.getSymbol());
//
//		tradeOrder = tradeOrderMapper.getTradeGopWithLock(tradeOrder.getId());
//
//		if (!tradeOrder.getStatus().equals(TradeCoinStatus.WAITING)) {
//			return null;
//		}
//
//		TradeCoinType tradeCoinType = tradeOrder.getOrderType();
//		AssetOperationDto assetOperationDto = new AssetOperationDto();
//		assetOperationDto.setAccountClass(AccountClass.EQUITY);
//		assetOperationDto.setUid(tradeOrder.getUid());
//		assetOperationDto.setBusinessSubject(BusinessSubject.LOCK);
//		assetOperationDto.setAccountSubject(AccountSubject.UNKNOWN);
//		BigDecimal lockAmount = null;
//		Trade trade = new Trade();
//		trade.setUid(userId);
//		trade.setTransactionNo(tradeOrder.getInnerOrderNo());
//
//		if (tradeCoinType.equals(TradeCoinType.BUY)) {
//			trade.setType(Type.BUY);
//			assetOperationDto.setAssetCode(configSymbol.getAssetCode1());
//			if (tradeOrder.getTradeFlag().equals(TradeCoinFlag.FIXED)) {
//				trade.setFlag(Flag.FIXED);
//				trade.setPrice(tradeOrder.getPrice());
//				trade.setNumTotal(tradeOrder.getNumber());
//
//				lockAmount = BigDecimalUtils.multiply(tradeOrder.getPrice(), tradeOrder.getNumber());
//
//			} else {
//				trade.setFlag(Flag.MARKET);
//				trade.setMarket(tradeOrder.getMoney());
//				lockAmount = tradeOrder.getMoney();
//			}
//		} else {
//			trade.setType(Type.SELL);
//			if (tradeOrder.getTradeFlag().equals(TradeCoinFlag.FIXED)) {
//				trade.setFlag(Flag.FIXED);
//			} else {
//				trade.setFlag(Flag.MARKET);
//			}
//
//			trade.setPrice(tradeOrder.getPrice());
//			trade.setNumTotal(tradeOrder.getNumber());
//
//			lockAmount = tradeOrder.getNumber();
//			assetOperationDto.setAssetCode(configSymbol.getAssetCode2());
//		}
//
//		assetOperationDto.setAmount(lockAmount.negate());
//		assetOperationDto.setLockAmount(lockAmount);
//		assetOperationDto.setLoanAmount(BigDecimalUtils.getZero());
//		String requestNo = SequenceUtil.getNextId();
//		assetOperationDto.setRequestNo(requestNo);
//
//		TradeOrder tradeOrder2 = new TradeOrder();
//		tradeOrder2.setId(tradeOrder.getId());
//		tradeOrder2.setRequestNo(requestNo);
//
//		tradeOrder2.setStatus(TradeCoinStatus.PROCESSING);
//		tradeOrder2.setUpdateDate(new Date());
//		int result = tradeOrderMapper.updateByPrimaryKeySelective(tradeOrder2);
//		if (result == 0) {
//			log.error("更新订单表时出错，id为:{}", tradeOrder2.getId());
//			throw new AppException(CommonCodeConst.SERVICE_ERROR);
//		}
//
//
//		return trade;
//
//	}
//
//	@Override
//	public TradeOrder queryTradeOrderbyOuterOrderNo(int uid, String outerOrderNo) {
//
//		TradeOrder trade = null;
//		try {
//			trade = tradeOrderMapper.getByOuterOrderNo(outerOrderNo, uid);
//		} catch (Exception e) {
//			log.error("查询数据库订单表异常,error:{}", e);
//			throw new AppException(CommonCodeConst.SERVICE_ERROR);
//		}
//		return trade;
//	}
//
//	@Override
//	public TradeOrder queryTradeOrderInnerOrderNo(String innerOrderNo) {
//		TradeOrder trade = null;
//		try {
//			trade = tradeOrderMapper.getByInternalOrderNo(innerOrderNo);
//		} catch (Exception e) {
//			log.error("查询数据库订单表异常,error:{}", e);
//			throw new AppException(CommonCodeConst.SERVICE_ERROR);
//		}
//		return trade;
//	}
//
//	/**
//	 * 查询订单
//	 */
//	@Override
//	public PageModel<TradeOrder> queryTradeOrderByPage(Integer uid, Integer pageNo, Integer pageSize) {
//		PageHelper.orderBy("id asc");
//		PageHelper.startPage(pageNo, pageSize, true);
//		List<TradeOrder> tradeOrders = new ArrayList<>();
//		try {
//			tradeOrders = tradeOrderMapper.getTradeOrder(uid);
//		} catch (Exception e) {
//			log.error("查询数据库订单表异常,error:{}", e);
//			throw new AppException(CommonCodeConst.SERVICE_ERROR);
//		}
//		if (null == tradeOrders || tradeOrders.isEmpty()) {
//			return null;
//		}
//		Page<TradeOrder> page = (Page<TradeOrder>) tradeOrders;
//		PageModel<TradeOrder> pageModel = new PageModel<>();
//		pageModel.setList(tradeOrders);
//		pageModel.setPageNo(pageNo);
//		pageModel.setPageNum(page.getPages());
//		pageModel.setPageSize(page.getPageSize());
//		return pageModel;
//	}
//
//	@Override
//	public PageModel<TradeOrder> queryHistoryTradeOrderByPage(Integer uid, Integer pageNo, Integer pageSize) {
//		PageHelper.orderBy("id desc");
//		PageHelper.startPage(pageNo, pageSize, true);
//		List<TradeOrder> tradeOrders = new ArrayList<>();
//		try {
//			tradeOrders = tradeOrderMapper.getHistoryTradeOrder(uid);
//		} catch (Exception e) {
//			log.error("查询数据库订单表异常,error:{}", e);
//			throw new AppException(CommonCodeConst.SERVICE_ERROR);
//		}
//		if (null == tradeOrders || tradeOrders.isEmpty()) {
//			return null;
//		}
//		Page<TradeOrder> page = (Page<TradeOrder>) tradeOrders;
//		PageModel<TradeOrder> pageModel = new PageModel<>();
//		pageModel.setList(tradeOrders);
//		pageModel.setPageNo(pageNo);
//		pageModel.setPageNum(page.getPages());
//		pageModel.setPageSize(page.getPageSize());
//		return pageModel;
//	}
//
//	@Override
//	public PageModel<TradeOrder> queryCurrentTradeOrderByPage(Integer uid, Integer pageNo, Integer pageSize) {
//		PageHelper.orderBy("id desc");
//		PageHelper.startPage(pageNo, pageSize, true);
//		List<TradeOrder> tradeOrders = new ArrayList<>();
//		try {
//			tradeOrders = tradeOrderMapper.getProcessingTradeOrder(uid);
//
//		} catch (Exception e) {
//			log.error("查询数据库订单表异常,error:{}", e);
//			throw new AppException(CommonCodeConst.SERVICE_ERROR);
//		}
//		if (null == tradeOrders || tradeOrders.isEmpty()) {
//			return null;
//		}
//		Page<TradeOrder> page = (Page<TradeOrder>) tradeOrders;
//		PageModel<TradeOrder> pageModel = new PageModel<>();
//		pageModel.setList(tradeOrders);
//		pageModel.setPageNo(pageNo);
//		pageModel.setPageNum(page.getPages());
//		pageModel.setPageSize(page.getPageSize());
//		return pageModel;
//	}
//
//	/**
//	 * 撤单
//	 */
//	@Transactional
//	public void cancelOrder(Integer userId, String macthOrderNo, String symbol, BigDecimal price, BigDecimal marketOver,
//			BigDecimal numberOver) {
//
//		TradeOrder tradeOrder = tradeOrderMapper.getByInternalOrderNo(macthOrderNo);
//
//		ConfigSymbol configSymbol = symbolService.getConfigSymbolBYSymbol(symbol);
//
//		tradeOrder = tradeOrderMapper.getTradeGopWithLock(tradeOrder.getId());
//
//		if (tradeOrder.getStatus().equals(TradeCoinStatus.CANCEL)) {
//			throw new AppException(OrderCodeConst.ORDER_HAD_CANCELLED);
//		}
//		if (tradeOrder.getStatus().equals(TradeCoinStatus.WAITING)) {
//			throw new AppException(OrderCodeConst.ORDER_HAD_PROCESSING);
//		}
//		if (tradeOrder.getStatus().equals(TradeCoinStatus.SUCCESS)) {
//			throw new AppException(OrderCodeConst.ORDER_HAD_TRADED);
//		}
//		AssetOperationDto assetOperationDto = new AssetOperationDto();
//		assetOperationDto.setRequestNo(tradeOrder.getInnerOrderNo());
//		assetOperationDto.setUid(tradeOrder.getUid());
//		assetOperationDto.setAccountClass(AccountClass.EQUITY);
//		assetOperationDto.setAccountSubject(AccountSubject.SYSTEM_TRANSFER_EQUITY_LESS);
//		assetOperationDto.setBusinessSubject(BusinessSubject.UNLOCK);
//		assetOperationDto.setMemo("撤单");
//		if (tradeOrder.getOrderType().equals(TradeCoinType.BUY)) {
//			assetOperationDto.setAssetCode(configSymbol.getAssetCode1());
//			if (tradeOrder.getTradeFlag().equals(TradeCoinFlag.FIXED)) {
//				assetOperationDto.setAmount(numberOver.multiply(price));
//				assetOperationDto.setLockAmount(numberOver.multiply(price).negate());
//				assetOperationDto.setLoanAmount(BigDecimal.ZERO);
//			} else {
//				assetOperationDto.setAmount(marketOver);
//				assetOperationDto.setLockAmount(marketOver.negate());
//				assetOperationDto.setLoanAmount(BigDecimal.ZERO);
//			}
//
//		} else {
//			assetOperationDto.setAssetCode(configSymbol.getAssetCode2());
//			assetOperationDto.setAmount(numberOver);
//			assetOperationDto.setLockAmount(numberOver.negate());
//			assetOperationDto.setLoanAmount(BigDecimal.ZERO);
//		}
//		TradeOrder tradeOrder2 = new TradeOrder();
//		tradeOrder2.setStatus(TradeCoinStatus.CANCEL);
//		tradeOrder2.setUpdateDate(new Date());
//		tradeOrder2.setId(tradeOrder.getId());
//
//		tradeOrderMapper.updateByPrimaryKeySelective(tradeOrder2);
////		userAccountFacade.assetOperation(Lists.newArrayList(assetOperationDto));
//	}
//
//	@Override
//	public PageModel<TradeOrder> queryHistoryTradeOrderByPage(String symbol, Integer userId, Integer pageNo,
//			Integer pageSize) {
//		PageHelper.orderBy("id desc");
//		PageHelper.startPage(pageNo, pageSize, true);
//		List<TradeOrder> tradeOrders = new ArrayList<>();
//		try {
//			tradeOrders = tradeOrderMapper.getHistoryTradeOrderSymbol(userId, symbol);
//		} catch (Exception e) {
//			log.error("查询数据库订单表异常,error:{}", e);
//			throw new AppException(CommonCodeConst.SERVICE_ERROR);
//		}
//		if (null == tradeOrders || tradeOrders.isEmpty()) {
//			return null;
//		}
//		Page<TradeOrder> page = (Page<TradeOrder>) tradeOrders;
//		PageModel<TradeOrder> pageModel = new PageModel<>();
//		pageModel.setList(tradeOrders);
//		pageModel.setPageNo(pageNo);
//		pageModel.setPageNum(page.getPages());
//		pageModel.setPageSize(page.getPageSize());
//		return pageModel;
//	}
//
//	@Override
//	public PageModel<TradeOrder> queryCurrentTradeOrderByPage(Integer userId, String symbol, Integer pageNo,
//			Integer pageSize) {
//		PageHelper.orderBy("id desc");
//		PageHelper.startPage(pageNo, pageSize, true);
//		List<TradeOrder> tradeOrders = new ArrayList<>();
//		try {
//			tradeOrders = tradeOrderMapper.getProcessingTradeOrderSymbol(userId, symbol);
//		} catch (Exception e) {
//			log.error("查询数据库订单表异常,error:{}", e);
//			throw new AppException(CommonCodeConst.SERVICE_ERROR);
//		}
//		if (null == tradeOrders || tradeOrders.isEmpty()) {
//			return null;
//		}
//		Page<TradeOrder> page = (Page<TradeOrder>) tradeOrders;
//		PageModel<TradeOrder> pageModel = new PageModel<>();
//		pageModel.setList(tradeOrders);
//		pageModel.setPageNo(pageNo);
//		pageModel.setPageNum(page.getPages());
//		pageModel.setPageSize(page.getPageSize());
//		return pageModel;
//	}
//
//	@Override
//	public BigDecimal updateMatchOrder(String innerOrderNo, BigDecimal price, BigDecimal num, BigDecimal fee) {
//		TradeOrder tradeOrder = queryTradeOrderInnerOrderNo(innerOrderNo);
//		if (null == tradeOrder) {
//			throw new AppException(OrderCodeConst.ORDER_NOT_EXISTED);
//		}
//		BigDecimal unlock = null;
//		tradeOrder = tradeOrderMapper.getTradeGopWithLock(tradeOrder.getId());
//
//		if (tradeOrder.getOrderType().equals(TradeCoinType.BUY)) {
//			if (tradeOrder.getTradeFlag().equals(TradeCoinFlag.FIXED)) {
//				tradeOrder.setNumberOver(tradeOrder.getNumberOver().subtract(num));
//				unlock = tradeOrder.getPrice().multiply(num).subtract(price.multiply(num));
//				if (unlock.compareTo(BigDecimal.ZERO) < 0) {
//					unlock = BigDecimal.ZERO;
//				}
//				if (tradeOrder.getNumberOver().compareTo(BigDecimal.ZERO) == 0) {
//					tradeOrder.setStatus(TradeCoinStatus.SUCCESS);
//				}
//			} else {
//				unlock = BigDecimal.ZERO;
//				tradeOrder.setMoneyOver(tradeOrder.getMoneyOver().subtract(price.multiply(num)));
//				if (tradeOrder.getMoneyOver().compareTo(new BigDecimal("0.1")) < 0) {
//					tradeOrder.setStatus(TradeCoinStatus.SUCCESS);
//					unlock = tradeOrder.getMoneyOver();
//				}
//			}
//
//		} else {
//			unlock = BigDecimal.ZERO;
//			tradeOrder.setNumberOver(tradeOrder.getNumberOver().subtract(num));
//			if (tradeOrder.getNumberOver().compareTo(BigDecimal.ZERO) == 0) {
//				tradeOrder.setStatus(TradeCoinStatus.SUCCESS);
//			}
//		}
//		tradeOrder.setUpdateDate(new Date());
//		tradeOrder.setFee(BigDecimalUtils.add(fee, tradeOrder.getFee()));
//		tradeOrder.setTradedNumber(tradeOrder.getTradedNumber().add(num));
//		tradeOrder.setTradedMoney(tradeOrder.getTradedMoney().add(price.multiply(num)));
//		tradeOrderMapper.updateByPrimaryKeySelective(tradeOrder);
//		return unlock;
//	}
//
//	@Override
//	@Transactional
//	public Trade createAndPayMatchOrder(Integer brokerId, MatchOrderDto matchOrderDto) {
//
//		String innerorderno = createMacthOrder(brokerId, matchOrderDto);
//		return payMatchOrderByInnerOrderNo(innerorderno);
//
//	}
//
//	@Override
//	public PageInfo<TradeOrder> userTradeList(Integer brokerId, String innerOrderId, Integer accountId, String symbol,
//			Integer uId, TradeCoinType type, TradeCoinStatus status, int pageNo, int pageSize) {
//		PageHelper.orderBy("id desc");
//		PageHelper.startPage(pageNo, pageSize);
//		return new PageInfo<>(
//				tradeOrderMapper.getTradeList(brokerId, innerOrderId, accountId, symbol, uId, type, status));
//	}
//
//	@Transactional(noRollbackFor = AppException.class)
//	public Trade payMatchOrderByOuterOrderNo(int uid, String outerOrderNo) {
//		TradeOrder tradeOrder = queryTradeOrderbyOuterOrderNo(uid, outerOrderNo);
//		if (tradeOrder == null) {
//			log.error("没有发现内部订单号{}", outerOrderNo);
//			throw new AppException(CommonCodeConst.SERVICE_ERROR);
//		}
//
//		Integer userId = tradeOrder.getUid();
//
//		ConfigSymbol configSymbol = symbolService.getConfigSymbolBYSymbol(tradeOrder.getSymbol());
//
//		tradeOrder = tradeOrderMapper.getTradeGopWithLock(tradeOrder.getId());
//
//		if (!tradeOrder.getStatus().equals(TradeCoinStatus.WAITING)) {
//			return null;
//		}
//
//		TradeCoinType tradeCoinType = tradeOrder.getOrderType();
//		AssetOperationDto assetOperationDto = new AssetOperationDto();
//		assetOperationDto.setAccountClass(AccountClass.EQUITY);
//		assetOperationDto.setUid(tradeOrder.getUid());
//		assetOperationDto.setBusinessSubject(BusinessSubject.LOCK);
//		assetOperationDto.setAccountSubject(AccountSubject.UNKNOWN);
//		BigDecimal lockAmount = null;
//		Trade trade = new Trade();
//		trade.setUid(userId);
//		trade.setTransactionNo(tradeOrder.getInnerOrderNo());
//
//		if (tradeCoinType.equals(TradeCoinType.BUY)) {
//			trade.setType(Type.BUY);
//			assetOperationDto.setAssetCode(configSymbol.getAssetCode1());
//			if (tradeOrder.getTradeFlag().equals(TradeCoinFlag.FIXED)) {
//				trade.setFlag(Flag.FIXED);
//				trade.setPrice(tradeOrder.getPrice());
//				trade.setNumTotal(tradeOrder.getNumber());
//
//				lockAmount = BigDecimalUtils.multiply(tradeOrder.getPrice(), tradeOrder.getNumber());
//
//			} else {
//				trade.setFlag(Flag.MARKET);
//				trade.setMarket(tradeOrder.getMoney());
//				lockAmount = tradeOrder.getMoney();
//			}
//		} else {
//			trade.setType(Type.SELL);
//			if (tradeOrder.getTradeFlag().equals(TradeCoinFlag.FIXED)) {
//				trade.setFlag(Flag.FIXED);
//			} else {
//				trade.setFlag(Flag.MARKET);
//			}
//
//			trade.setPrice(tradeOrder.getPrice());
//			trade.setNumTotal(tradeOrder.getNumber());
//
//			lockAmount = tradeOrder.getNumber();
//			assetOperationDto.setAssetCode(configSymbol.getAssetCode2());
//		}
//
//		assetOperationDto.setAmount(lockAmount.negate());
//		assetOperationDto.setLockAmount(lockAmount);
//		assetOperationDto.setLoanAmount(BigDecimalUtils.getZero());
//		String requestNo = SequenceUtil.getNextId();
//		assetOperationDto.setRequestNo(requestNo);
//
//		TradeOrder tradeOrder2 = new TradeOrder();
//		tradeOrder2.setId(tradeOrder.getId());
//		tradeOrder2.setRequestNo(requestNo);
//
//		tradeOrder2.setStatus(TradeCoinStatus.PROCESSING);
//		tradeOrder2.setUpdateDate(new Date());
//		int result = tradeOrderMapper.updateByPrimaryKeySelective(tradeOrder2);
//		if (result == 0) {
//			log.error("更新订单表时出错，id为:{}", tradeOrder2.getId());
//			throw new AppException(CommonCodeConst.SERVICE_ERROR);
//		}
////
////		try {
////			userAccountFacade.assetOperation(Lists.newArrayList(assetOperationDto));
////		} catch (AppException e) {
////			tradeOrder2.setStatus(TradeCoinStatus.WAITING);
////			tradeOrder2.setStatus(TradeCoinStatus.FAIL);
////			tradeOrder2.setFailMessageCode(e.getErrCode());
////			tradeOrder2.setFailMessageDes(e.getMessage());
////			tradeOrderMapper.updateByPrimaryKeySelective(tradeOrder2);
////			throw e;
////		}
//
//		return trade;
//	}
//
//	@Override
//	public void updateSendStatusByInnerOrderNo(SendStatus sendStatus, String innerOrderNo) {
//		tradeOrderMapper.updateSendStatusByInnerOrderNo(sendStatus, innerOrderNo);
//	}
//
//	@Override
//	public List<TradeOrder> getUnSendMatchOrderByStatus(TradeCoinStatus tradeCoinStatus) {
//		return tradeOrderMapper.getUnSendMatchOrderByStatus(tradeCoinStatus);
//	}
//
//	@Override
//	public PageInfo<TradeOrder> getTradeOrderRecord(Integer brokerId, TradeCoinFlag tradeFlag, Integer uId, String symbol, TradeCoinType orderType, TradeCoinStatus status, Date startTime, Date endTime, Integer pageNo, Integer pageSize) {
//		PageHelper.orderBy("id desc");
//		PageHelper.startPage(pageNo, pageSize);
//		return new PageInfo<TradeOrder>(
//				tradeOrderMapper.getTradeRecordList(brokerId, tradeFlag,uId,symbol,orderType,status,startTime,endTime));
//	}
//
//}
