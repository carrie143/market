package com.gop.match.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Throwables;

import com.gop.api.cloud.common.OrderState;
import com.gop.api.cloud.common.OrderType;
import com.gop.api.cloud.request.TradeOrderQueryReq;
import com.gop.api.cloud.response.TradeOrderDto;
import com.gop.api.cloud.service.CloudApiService;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.MatchCodeConst;
import com.gop.code.consts.OrderCodeConst;
import com.gop.domain.ConfigSymbol;
import com.gop.domain.TradeCountResult;
import com.gop.domain.TradeMatchResult;
import com.gop.domain.TradeOrder;
import com.gop.domain.User;
import com.gop.domain.enums.SymbolStatus;
import com.gop.domain.enums.TradeCoinStatus;
import com.gop.domain.enums.TradeCoinType;
import com.gop.exception.AppException;
//import com.gop.exchange.hessian.TradeServcie;
//import com.gop.exchange.model.TradeResult;
//import com.gop.exchange.model.modelenum.Type;
import com.gop.match.dto.OrderDto;
import com.gop.match.dto.OrderNumDto;
import com.gop.match.dto.TradeDto;
import com.gop.match.service.ConfigSymbolService;
import com.gop.match.service.SymbolService;
import com.gop.match.service.TradeRecordService;
import com.gop.match.service.TradeServiceManager;
import com.gop.match.vo.TradePageModel;
import com.gop.mode.vo.PageModel;
import com.gop.user.service.UserService;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController("managerMatchOrderController")
@RequestMapping("/match")
// @Api("撮合查询controller")
public class MatchOrderController {

	@Autowired
	private TradeRecordService tradeRecordService;

//	@Autowired
//	private MatchOrderService matchOrderService;
	
	@Autowired
	private SymbolService symbolService;
	
	@Autowired
	private TradeServiceManager tradeServiceManager;

	@Autowired
	private UserService userService;

	@Autowired
	private CloudApiService cloudApiService;

	@Autowired
	private ConfigSymbolService configSymbolService;

//	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
//			@Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})") })
//	@RequestMapping(value = "/trades", method = RequestMethod.GET)
	// @ApiOperation("挂单交易查询")
//	public PageModel<TradeDto> tradeList(@AuthForHeader AuthContext context,
//			@RequestParam(value = "brokerId", required = false) Integer brokerId,
//			@RequestParam(value = "id", required = false) String innerOrderId,
//			@RequestParam(value = "accountId", required = false) Integer accountId,
//			@RequestParam(value = "symbol", required = false) String symbol,
//			@RequestParam(value = "uId", required = false) Integer uId,
//			@RequestParam(value = "type", required = false) TradeCoinType type,
//			@RequestParam(value = "status", required = false) TradeCoinStatus status,
//			@RequestParam("pageNo") Integer pageNo,
//			@RequestParam("pageSize") Integer pageSize) {
//		PageInfo<TradeOrder> pageInfo = matchOrderService.userTradeList(brokerId, innerOrderId, accountId, symbol, uId, type,
//				status, pageNo, pageSize);
//		PageModel<TradeDto> pages = new PageModel<>();
//		pages.setPageNo(pageNo);
//		pages.setPageSize(pageSize);
//		pages.setPageNum(pageInfo.getPages());
//		pages.setTotal(pageInfo.getTotal());
//		pages.setList(pageInfo.getList().stream().map(result -> new TradeDto(result)).collect(Collectors.toList()));
//
//		return pages;
//	}

//	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
//		@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
//	@RequestMapping(value = "/orders", method = RequestMethod.GET)
//	public PageModel<OrderDto> getOrders(@AuthForHeader AuthContext context,
//			@RequestParam(value = "brokerId", required = false) Integer brokerId,
//			@RequestParam(value = "symbol", required = false) String symbol,
//			@RequestParam(value = "buyTid", required = false) String buyTid,
//			@RequestParam(value = "buyUid", required = false) Integer buyUid,
//			@RequestParam(value = "sellTid", required = false) String sellTid,
//			@RequestParam(value = "sellUid", required = false) Integer sellUid, @RequestParam("pageNo") Integer pageNo,
//			@RequestParam("pageSize") Integer pageSize) {
//
//		PageInfo<TradeMatchResult> pageInfo = tradeRecordService.queryOrders(brokerId, symbol, buyTid, buyUid, sellTid,
//				sellUid, pageNo, pageSize);
//		PageModel<OrderDto> pages = new PageModel<>();
//		pages.setPageNo(pageNo);
//		pages.setPageSize(pageSize);
//		pages.setPageNum(pageInfo.getPages());
//		pages.setTotal(pageInfo.getTotal());
//		pages.setList(pageInfo.getList().stream().map(result -> new OrderDto(result)).collect(Collectors.toList()));
//
//		return pages;
//	}

	// /**
	// *
	// * 商户挂单查询
	// *
	// * @param buyPhone
	// * @param buyTid
	// * @param buyUid
	// * @param sellPhone
	// * @param sellTid
	// * @param sellUid
	// * @param pageNo
	// * @param pageSize
	// * @param request
	// * @return Object
	// */
	// @RequestMapping("/orderList")
	// public PageModel<OrderDto> getOrderList(@AuthForHeader AuthContext
	// context,
	// @RequestParam("brokerId") String brokerId, @RequestParam("symbol") String
	// symbol,
	// @RequestParam("orderType") String orderType, @RequestParam("status")
	// String status,
	// @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer
	// pageSize) {
	// return null;
	// }

	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
		@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	@RequestMapping(value = "/order-total", method = RequestMethod.GET)
	public OrderNumDto getBusinessInfo(@AuthForHeader AuthContext context, @RequestParam("brokerId") String brokerId) {
		return null;
	}

	//  管理员撤单
//	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
//			@Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
//			@Strategy(authStrategy = "exe({{'checkLoginPasswordStrategy'}})") })
//	@RequestMapping(value = "/match-order/cancel", method = RequestMethod.GET)
//	public void cancelMatchOrder(@AuthForHeader AuthContext authContext, @RequestParam("orderNo") String macthOrderNo,
//			@RequestParam("uid") Integer uid,
//			@RequestParam("symbol") String symbol) {
//
//		ConfigSymbol configSymbol = symbolService.getConfigSymbolBYSymbol(symbol);
////		if (null == configSymbol || !configSymbol.getStatus().equals(SymbolStatus.LISTED)) {
////			throw new AppException(MatchCodeConst.INVALID_SYMBOL);
////		}
//		if (null == configSymbol || configSymbol.getStatus().equals(SymbolStatus.INIT)) {
//			throw new AppException(MatchCodeConst.INVALID_SYMBOL);
//		}
////		Integer uid = authContext.getLoginSession().getUserId();
//		TradeServcie tradeServcie = tradeServiceManager.getTradeService(symbol);
//		if (tradeServcie == null) {
//			throw new AppException(MatchCodeConst.INVALID_SYMBOL);
//		}
//		TradeResult tradeResult;
//		TradeOrder tradeOrder = matchOrderService.queryTradeOrderInnerOrderNo(macthOrderNo);
//		if (tradeOrder == null || !tradeOrder.getUid().equals(uid) || !tradeOrder.getSymbol().equals(symbol)) {
//			throw new AppException(OrderCodeConst.ORDER_NOT_EXISTED);
//		}
//		if (tradeOrder.getStatus().equals(TradeCoinStatus.CANCEL)) {
//			throw new AppException(OrderCodeConst.ORDER_HAD_CANCELLED);
//		}
//
//		try {
//			tradeResult = tradeServcie.cancel(macthOrderNo,
//					tradeOrder.getOrderType().equals(TradeCoinType.BUY) ? Type.BUY : Type.SELL);
//		} catch (Exception e) {
//			log.info("发现异常", e);
//			Throwables.propagateIfInstanceOf(e, AppException.class);
//			log.error("statck", e);
//			throw new AppException(CommonCodeConst.SERVICE_ERROR);
//		}
//		matchOrderService.cancelOrder(uid, macthOrderNo, symbol, tradeResult.getPrice(), tradeResult.getMarketOver(),
//				tradeResult.getOverNum());
//	}

	/**
	 * 交易结果查询
	 */
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
													 @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})")})
	@RequestMapping(value = "/orders-query", method = RequestMethod.GET)
	public TradePageModel<TradeOrderDto> queryOrders(@AuthForHeader AuthContext context,
																							@RequestParam(value = "beginTime", required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
																							@RequestParam(value = "endTime", required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
																							@RequestParam(value = "tradeAsset", required = false) String tradeAsset ,
																							@RequestParam(value = "priceAsset", required = false) String priceAsset  ,
																							@RequestParam(value = "uid", required = false) Integer uid,
																							@RequestParam(value = "orderType", required = false) OrderType orderType,
																							@RequestParam(value = "status", required = false) OrderState orderState,
																							@RequestParam("pageNo") Integer pageNo,
																							@RequestParam("pageSize") Integer pageSize) {
    // assetCode1计价 assetCode2交易
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TradeOrderQueryReq request = new TradeOrderQueryReq();
		request.setBeginTime(beginTime==null?null:format.format(beginTime));
		request.setEndTime(endTime==null?null:format.format(endTime));
		request.setPriceAsset(priceAsset);
		request.setTradeAsset(tradeAsset);
		request.setUid(uid == null?null:(long)uid);
		request.setStatus(orderState);
		request.setOrderType(orderType);
		request.setTradeFlag(null);
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);
		PageInfo<TradeOrderDto> pageInfo = cloudApiService.queryUserOrderRecord(request);
		TradePageModel<TradeOrderDto> pages = new TradePageModel<>();
		pages.setPageNo(pageNo);
		pages.setPageSize(pageSize);
		pages.setPageNum(pageInfo.getPages());
		pages.setTotal(pageInfo.getTotal());
		pages.setList(pageInfo.getList());
		return pages;
	}
}


