package com.gop.offlinetrade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gop.mapper.dto.OrderRankOfCoinsNumDto;
import com.gop.mapper.dto.OrderRankOfLastBoundDto;
import com.gop.mapper.dto.OrderRankOfSellDto;
import com.gop.mode.vo.PageModel;
import com.gop.offlineorder.service.OrderInfoRankService;
import com.gop.offlineorder.service.TokenOrderService;

@RestController
@RequestMapping("/rank")
public class OrderInfoRankController {

	@Autowired
	@Qualifier("OrderInfoRankServiceImpl")
	private OrderInfoRankService orderInfoRankService;
	@Autowired
	private TokenOrderService tokenOrderService;

	// 最多成交(卖家uid,成功卖出比数)
	@RequestMapping(value = "/order-best-sell", method = RequestMethod.GET)
	public PageModel<OrderRankOfSellDto> orderRankOfSell(@RequestParam(value="pageNo", required = false) Integer pageNo, @RequestParam(value = "pageSize", required = false) Integer pageSize) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize < 0 || pageSize >10) {
			pageSize = 10 ;
		}
		PageModel<OrderRankOfSellDto> pageModel = orderInfoRankService.getRankOfSellFromTableOfCompletionCount(pageNo, pageSize);
		return pageModel;
	}

	// 最多成交单数量(币种,单数)
	@RequestMapping(value = "/order-coins-num", method = RequestMethod.GET)
	public PageModel<OrderRankOfCoinsNumDto> orderRankOfCoinNum(@RequestParam(value = "pageNo", required = false) Integer pageNo, @RequestParam(value = "pageSize" , required = false) Integer pageSize) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize < 0 || pageSize >10) {
			pageSize = 10 ;
		}
		PageModel<OrderRankOfCoinsNumDto> pageModel = orderInfoRankService.getRankOfCoinsNumFromTableOfCompletionCoins(pageNo, pageSize);
		return pageModel;
	}

	// 最新成交单倒排名(uid,售出数量,币种,成交状态,绑定时间)
	@RequestMapping(value = "/order-info-last", method = RequestMethod.GET)
	public PageModel<OrderRankOfLastBoundDto> orderRankOfBoundTime(@RequestParam(value = "pageNo",required = false) Integer pageNo, @RequestParam(value = "pageSize",required = false) Integer pageSize) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize < 0 || pageSize >10) {
			pageSize = 10 ;
		}
		PageModel<OrderRankOfLastBoundDto> pageModel = tokenOrderService.getRankOfLastBound(pageNo, pageSize);
		return pageModel;
	}
}
