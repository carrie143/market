package com.gop.match.controller;

import com.github.pagehelper.PageInfo;
import com.gop.api.cloud.common.OrderType;
import com.gop.api.cloud.common.TradeType;
import com.gop.api.cloud.request.CancelOrderReq;
import com.gop.api.cloud.request.MatchOrderPageQueryReq;
import com.gop.api.cloud.request.MatchOrderReq;
import com.gop.api.cloud.request.MatchRecordPageQueryReq;
import com.gop.api.cloud.service.CloudApiService;
import com.gop.domain.enums.TradeCoinStatus;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gop.domain.enums.TradeCoinFlag;
import com.gop.domain.enums.TradeCoinType;
import com.gop.match.dto.MatchOrderDetail;
import com.gop.match.dto.MatchRecordDto;
import com.gop.match.service.SymbolService;
import com.gop.match.service.TradeRecordService;
import com.gop.match.service.TradeServiceManager;
import com.gop.mode.vo.PageModel;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/match")
@Slf4j
public class MatchOrderController {

	@Autowired
	SymbolService symbolService;
	@Autowired
	TradeServiceManager tradeServiceManager;

	@Autowired
	TradeRecordService tradeRecordService;

	@Autowired
	private CloudApiService cloudApiService;

	@RequestMapping(value = "/order", method = RequestMethod.GET)
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkPayPasswordStregy'}})") })
	public void matchOrder(@AuthForHeader AuthContext authContext, @RequestParam("orderNo") String orderNo,
			@RequestParam("tradeCoinType") TradeCoinType tradeCoinType,
			@RequestParam("tradeCoinFlag") TradeCoinFlag tradeCoinFlag, @RequestParam("symbol") String symbol,
			@RequestParam(required = false, value = "price") BigDecimal price,
			@RequestParam(required = false, value = "amount") BigDecimal amount,
			@RequestParam(required = false, value = "market") BigDecimal market) {


		MatchOrderReq request = new MatchOrderReq();
		request.setBrokerUid(authContext.getLoginSession().getUserId().longValue());
		request.setAmount(amount);
		request.setClientOrderNo(orderNo);
		request.setOrderType(OrderType.valueOf(tradeCoinType.name()));
		request.setPrice(price);

		String[] split = symbol.split("_");
		request.setTradeAsset(split[0]);
		request.setPriceAsset(split[1]);

		request.setTradeType(TradeType.valueOf(tradeCoinFlag.name()));
		request.setNanoTime(System.nanoTime());
		cloudApiService.matchOrder(request);

	}

	// 查询历史下单记录
	@Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))
	@RequestMapping(value = "/match-order/history", method = RequestMethod.GET)
	public PageModel<MatchOrderDetail> getHistoryMatchOrder(@AuthForHeader AuthContext authContext,
			@RequestParam("pageNo") Integer pageNo, @RequestParam("symbol") String symbol,
			@RequestParam("pageSize") Integer pageSize) {
		PageModel<MatchOrderDetail> pageModelDto = new PageModel<>();

		MatchOrderPageQueryReq queryReq = new MatchOrderPageQueryReq();
		queryReq.setNanoTime(System.nanoTime());
		queryReq.setBrokerUid(authContext.getLoginSession().getUserId().longValue());
		queryReq.setPageNo(pageNo);
		queryReq.setPageSize(pageSize);
		String[] split = symbol.split("_");
		queryReq.setTradeAsset(split[0]);
		queryReq.setPriceAsset(split[1]);
		PageInfo<com.gop.api.cloud.response.MatchOrderDetail> matchOrderDetailPageInfo = cloudApiService
				.matchOrderHistory(queryReq);
		pageModelDto = buildPageModel(pageModelDto, matchOrderDetailPageInfo);

		return pageModelDto;

	}

	private PageModel<MatchOrderDetail> buildPageModel(PageModel<MatchOrderDetail> pageModelDto,
			PageInfo<com.gop.api.cloud.response.MatchOrderDetail> matchOrderDetailPageInfo) {

		pageModelDto.setPageSize(matchOrderDetailPageInfo.getPageSize());
		pageModelDto.setPageNo(matchOrderDetailPageInfo.getPageNum());
		pageModelDto.setTotal(matchOrderDetailPageInfo.getTotal());
		pageModelDto.setPageNum(matchOrderDetailPageInfo.getPages());

		List<MatchOrderDetail> matchOrderDetailList = new LinkedList<>();
		matchOrderDetailPageInfo.getList().stream().forEach(mo -> {
			MatchOrderDetail  detail = new MatchOrderDetail();
			BeanUtils.copyProperties(mo, detail);

			detail.setTradeCoinFlag(TradeCoinFlag.valueOf(mo.getTradeType().name()));
			detail.setTradeCoinType(TradeCoinType.valueOf(mo.getOrderType().name()));
			detail.setOrderNo(mo.getClientOrderNo());
			detail.setTradeCoinStatus(TradeCoinStatus.valueOf(mo.getOrderState().name()));
			detail.setSymbol(mo.getTradeAssert()+"_"+mo.getPriceAssert());
			detail.setPayTransactionNo(mo.getPlatformOrderNo());

			detail.setTradedNumber(mo.getTradedNumber()!=null?mo.getTradedNumber().stripTrailingZeros().toPlainString():"");
			detail.setNumberOver(mo.getNumberOver()!=null?mo.getNumberOver().stripTrailingZeros().toPlainString():"");
			detail.setPrice(mo.getPrice()!=null?mo.getPrice().stripTrailingZeros().toPlainString():"");
			detail.setNumber(mo.getNumber()!=null?mo.getNumber().stripTrailingZeros().toPlainString():"");
			detail.setMatchedMoney(mo.getMatchedMoney()!=null?mo.getMatchedMoney().stripTrailingZeros().toPlainString():"");
			detail.setFee(mo.getFee()!=null?mo.getFee().stripTrailingZeros().toPlainString():"");

			matchOrderDetailList.add(detail);
		});
		pageModelDto.setList(matchOrderDetailList);
		return pageModelDto;

	}

	@Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))
	@RequestMapping(value = "/match-order/match-record", method = RequestMethod.GET)
	public PageModel<MatchRecordDto> getMatchRecord(@AuthForHeader AuthContext authContext,
			@RequestParam("pageNo") Integer pageNo, @RequestParam("symbol") String symbol,
			@RequestParam("pageSize") Integer pageSize, @RequestParam("orderNo") String orderNo) {

		PageModel<MatchRecordDto> pageModelDto = new PageModel<>();
		MatchRecordPageQueryReq request = new MatchRecordPageQueryReq();
		request.setOrderNo(orderNo);
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);
		String[] split = symbol.split("_");

		request.setTradeAsset(split[0]);
		request.setPriceAsset(split[1]);
		request.setBrokerUid(authContext.getLoginSession().getUserId().longValue());
		PageInfo<com.gop.api.cloud.response.MatchRecordDto> matchRecordDtoPageInfo = cloudApiService
				.matchRecord(request);
		pageModelDto = buildRecordPageModel(pageModelDto, matchRecordDtoPageInfo);
		return pageModelDto;

	}

	private PageModel<MatchRecordDto> buildRecordPageModel(PageModel<MatchRecordDto> pageModelDto,
			PageInfo<com.gop.api.cloud.response.MatchRecordDto> matchRecordDtoPageInfo) {

		pageModelDto.setPageSize(matchRecordDtoPageInfo.getPageSize());
		pageModelDto.setPageNo(matchRecordDtoPageInfo.getPageNum());
		pageModelDto.setTotal(matchRecordDtoPageInfo.getTotal());
		pageModelDto.setPageNum(matchRecordDtoPageInfo.getPages());

		List<MatchRecordDto> matchOrderDetailList = new LinkedList<>();
		matchRecordDtoPageInfo.getList().stream().forEach(recordDto -> {
			MatchRecordDto recordDto1 = new MatchRecordDto();

			recordDto1.setCreateTime(recordDto.getCreateTime());
			recordDto1.setNum(recordDto.getNum()!=null?recordDto.getNum().stripTrailingZeros().toPlainString():"");
			recordDto1.setPrice(recordDto.getPrice()!=null?recordDto.getPrice().stripTrailingZeros().toPlainString():"");
			matchOrderDetailList.add(recordDto1);
		});
		pageModelDto.setList(matchOrderDetailList);
		return pageModelDto;

	}

	@Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))
	@RequestMapping(value = "/match-order/current", method = RequestMethod.GET)
	public PageModel<MatchOrderDetail> getMatchOrderDetail(@AuthForHeader AuthContext authContext,
			@RequestParam("pageNo") Integer pageNo, @RequestParam("symbol") String symbol,
			@RequestParam("pageSize") Integer pageSize) {

		PageModel<MatchOrderDetail> pageModelDto = new PageModel<>();;
		MatchOrderPageQueryReq request = new MatchOrderPageQueryReq();
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);
		request.setBrokerUid(authContext.getLoginSession().getUserId().longValue());
		String[] split = symbol.split("_");

		request.setTradeAsset(split[0]);
		request.setPriceAsset(split[1]);
		PageInfo<com.gop.api.cloud.response.MatchOrderDetail> matchOrderDetailPageInfo = cloudApiService
				.matchOrderCurrentDetail(request);
		pageModelDto = buildPageModel(pageModelDto, matchOrderDetailPageInfo);
		return pageModelDto;

	}

	// 撤单
	@Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))
	@RequestMapping(value = "/match-order/cancel", method = RequestMethod.GET)
	public void cancelMatchOrder(@AuthForHeader AuthContext authContext, @RequestParam("orderNo") String macthOrderNo,
			@RequestParam("symbol") String symbol) {

		CancelOrderReq request = new CancelOrderReq();
		request.setNanoTime(System.nanoTime());
		request.setBrokerUid(authContext.getLoginSession().getUserId().longValue());
		request.setClientOrderNo(macthOrderNo);
		cloudApiService.matchOrderCancel(request);
	}

}
