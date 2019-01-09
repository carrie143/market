package com.gop.offlinetrade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.google.common.base.Splitter;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.OfflineOrderConst;
import com.gop.config.CommonConstants;
import com.gop.config.PushTradeConfig;
import com.gop.domain.TokenOrder;
import com.gop.domain.TokenOrderLog;
import com.gop.domain.enums.TokenOrderState;
import com.gop.exception.AppException;
import com.gop.mode.vo.PageModel;
import com.gop.offlineorder.service.TokenOrderService;
import com.gop.offlinetrade.dto.OfflineOrderResultDto;
import com.gop.offlinetrade.dto.TokenOrderOutLine;
import com.gop.user.facade.UserFacade;
import com.gop.util.SequenceUtil;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

@RestController
@RequestMapping("/offline")
public class OfflineOrderController {

	@Autowired
	private TokenOrderService tokenOrderService;
	@Autowired
	private UserFacade userFacade;

	@RequestMapping(value = "/create-token-order", method = RequestMethod.GET)
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkCertification'}})") })
	public OfflineOrderResultDto createTokenOrder(@AuthForHeader AuthContext authContext,
			@RequestParam("price") BigDecimal price, @RequestParam("num") BigDecimal num,
			@RequestParam("address") String address, @RequestParam("assetName") String assetName) {
		TokenOrder tokenOrder = new TokenOrder();
		if ((price.compareTo(BigDecimal.ZERO) < 0) || (num.compareTo(BigDecimal.ZERO) < 0)) {
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
		if ((price.scale() > 2) || (num.scale() > 0)) {
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
		tokenOrder.setFromAddress(address);
		tokenOrder.setPrice(price);
		tokenOrder.setNum(num);
		tokenOrder.setAssetName(assetName);
		tokenOrder.setSellUid(authContext.getLoginSession().getUserId());
		tokenOrder.setCreateDate(new Date());
		tokenOrder.setPay(price.multiply(num).subtract(PushTradeConfig.FEE));
		tokenOrder.setMoney(price.multiply(num));
		tokenOrder.setTradeAsset(PushTradeConfig.PUSH_ASSET);
		tokenOrder.setSellFee(PushTradeConfig.FEE);
		tokenOrder.setBuyOrderRequestNo(SequenceUtil.getNextId());
		tokenOrder.setSellOrderRequestNo(SequenceUtil.getNextId());
		tokenOrder.setBuyUid(0);
		String key = RandomStringUtils.randomNumeric(4);
		String orderNo = SequenceUtil.getNextId();
		tokenOrder.setPassword(key);
		tokenOrder.setOrderNo(orderNo);
		tokenOrder.setState(TokenOrderState.WAITING);
		tokenOrder.setUpdateDate(new Date());
		int id = tokenOrderService.createTokenOrder(tokenOrder);
		OfflineOrderResultDto offlineOrderResultDto = new OfflineOrderResultDto();
		offlineOrderResultDto.setKey(key);
		offlineOrderResultDto.setOrderNo(orderNo);
		offlineOrderResultDto.setId(id);
		return offlineOrderResultDto;
	}

	// 获取线下买order
	@RequestMapping(value = "/bind-token-order", method = RequestMethod.GET)
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkCertification'}})") })
	public TokenOrder getBuyTokenOrderById(@AuthForHeader AuthContext authContext, @RequestParam("tokenOrderId") int id,
			@RequestParam("password") String password) {

		TokenOrder tokenOrder = tokenOrderService.getTokenOrder(id);
		if (null == tokenOrder) {
			throw new AppException(OfflineOrderConst.TOKEN_ORDER_NOT_FOUND);
		}

		if (!tokenOrder.getPassword().equals(password)) {
			throw new AppException(OfflineOrderConst.PASSWORD_ERROR);
		}
		if (tokenOrder.getBuyUid().equals(authContext.getLoginSession().getUserId())) {
			return tokenOrder;
		}

		boolean flag = tokenOrderService.bloundBuyUid(id, authContext.getLoginSession().getUserId());
		if (!flag) {
			throw new AppException(OfflineOrderConst.USER_BLOUND);
		}
		tokenOrder.setBundTime(new Date());

		return tokenOrderService.getTokenOrder(id);
	}

	// 获取线下卖order
	// 获取卖出订单
	@RequestMapping(value = "/sell/token-order", method = RequestMethod.GET)
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkCertification'}})") })
	public PageModel<TokenOrder> getSellTokenOrderHistory(@AuthForHeader AuthContext authContext,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize,
			@RequestParam("states") String states) {
		pageNo = CommonConstants.getPageNo(pageNo);
		pageSize = CommonConstants.getPageNo(pageSize);
		List<TokenOrderState> tokenOrderStates = null;
		try {
			tokenOrderStates = Splitter.on(',').trimResults().omitEmptyStrings().splitToList(states).stream()
					.map(a -> TokenOrderState.getTokenOrderStateByName(a)).collect(Collectors.toList());
		} catch (Exception e) {
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
		Page<TokenOrder> page = tokenOrderService.getOrderForSellByPage(authContext.getLoginSession().getUserId(),
				tokenOrderStates, pageNo, pageSize);
		PageModel<TokenOrder> PageModel = new PageModel<TokenOrder>();
		PageModel.setList(page.getResult());
		PageModel.setPageNo(pageNo);
		PageModel.setPageSize(page.getPageSize());
		PageModel.setTotal(page.getTotal());
		PageModel.setPageNum(page.getPages());
		return PageModel;

	}

	// 获取买入订单
	@RequestMapping(value = "/buy/token-order", method = RequestMethod.GET)
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkCertification'}})") })
	public PageModel<TokenOrder> getBuyTokenOrder(@AuthForHeader AuthContext authContext,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize,
			@RequestParam("states") String states) {
		List<TokenOrderState> tokenOrderStates = null;
		try {
			tokenOrderStates = Splitter.on(',').trimResults().omitEmptyStrings().splitToList(states).stream()
					.map(a -> TokenOrderState.getTokenOrderStateByName(a)).collect(Collectors.toList());
		} catch (Exception e) {
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}

		Page<TokenOrder> page = tokenOrderService.getOrderForBuyByPage(authContext.getLoginSession().getUserId(),
				tokenOrderStates, pageNo, pageSize);
		PageModel<TokenOrder> PageModel = new PageModel<TokenOrder>();
		PageModel.setList(page.getResult());
		PageModel.setPageNo(pageNo);
		PageModel.setPageSize(page.getPageSize());
		PageModel.setTotal(page.getTotal());
		PageModel.setPageNum(page.getPages());
		return PageModel;
	}

	// 获取订单简单详情
	@RequestMapping(value = "/get-token-order-outline", method = RequestMethod.GET)
	public TokenOrderOutLine getTokenOrderOutline(@RequestParam("tokenOrderId") int id) {
		TokenOrder tokenOrder = tokenOrderService.getTokenOrder(id);
		if (null == tokenOrder) {
			throw new AppException(OfflineOrderConst.TOKEN_ORDER_NOT_FOUND);
		}
		String name = userFacade.getUser(tokenOrder.getSellUid()).getFullname();
		TokenOrderOutLine tokenOrderOutLine = new TokenOrderOutLine();
		tokenOrderOutLine.setAssetName(tokenOrder.getAssetName());
		tokenOrderOutLine.setUserName(name);
		tokenOrderOutLine.setCreateTime(tokenOrder.getCreateDate());
		tokenOrderOutLine.setNum(tokenOrder.getNum());
		return tokenOrderOutLine;
	}

	// 获取订单
	@RequestMapping(value = "/get-token-order", method = RequestMethod.GET)
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkCertification'}})") })
	public TokenOrder getTokenOrder(@AuthForHeader AuthContext authContext, @RequestParam("tokenOrderId") int id) {
		TokenOrder tokenOrder = tokenOrderService.getTokenOrder(id);
		if (null == tokenOrder) {
			throw new AppException(OfflineOrderConst.TOKEN_ORDER_NOT_FOUND);
		}
		if ((tokenOrder.getBuyUid().intValue() != authContext.getLoginSession().getUserId().intValue())
				&& (tokenOrder.getSellUid().intValue() != authContext.getLoginSession().getUserId().intValue())) {
			throw new AppException(OfflineOrderConst.TOKEN_ORDER_NOT_FOUND);
		}
		return tokenOrder;
	}

	// 获取订单状态变更日志
	@RequestMapping(value = "/get-token-order-log", method = RequestMethod.GET)
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkCertification'}})") })
	public List<TokenOrderLog> getBuyTokenOrderHistory(@AuthForHeader AuthContext authContext,
			@RequestParam("tokenOrderId") int id) {
		List<TokenOrderLog> list = tokenOrderService.getTokenOrderLog(id);
		if (list == null) {
			list = new ArrayList<>();
		}
		return list;

	}

	// 支付订单
	// 权限：校验登录，校验支付密码
	@RequestMapping(value = "/pay/token-order", method = RequestMethod.GET)
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkPayPasswordStregy'}})"),
			@Strategy(authStrategy = "exe({{'checkCertification'}})") })
	public void payOfflineOrder(@AuthForHeader AuthContext authContext, @RequestParam("tokenOrderId") Integer orderId,
			@RequestParam("address") String address) {
		if (address.length() > 80) {
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
		TokenOrder tokenOrder = tokenOrderService.getTokenOrder(orderId);

		if (null == tokenOrder) {
			throw new AppException(OfflineOrderConst.TOKEN_ORDER_NOT_FOUND);
		}
		if (tokenOrder.getState().equals(TokenOrderState.WAITING)
				&& authContext.getLoginSession().getUserId().intValue() != tokenOrder.getBuyUid().intValue()) {
			throw new AppException(OfflineOrderConst.TOKEN_ORDER_NOT_FOUND);
		}

		if (new Date().getTime() - tokenOrder.getBundTime().getTime() > 1000 * 60 * 60) {
			tokenOrderService.updateTokenOrderStatus(authContext.getLoginSession().getUserId(), orderId,
					TokenOrderState.CANCEL);
		}

		tokenOrderService.payTokenOrder(address, tokenOrder.getId());
	}

	// 修改订单状态
	@RequestMapping(value = "/change-state", method = RequestMethod.GET)
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkCertification'}})") })
	public void changeOfflineTokenOrderState(@AuthForHeader AuthContext authContext,
			@RequestParam("tokenOrderState") TokenOrderState tokenOrderState,
			@RequestParam("tokenOrderId") Integer orderId) {
		if (tokenOrderState.equals(TokenOrderState.PAID)) {
			throw new AppException(OfflineOrderConst.TOKEN_ORDER_STATE_ERROR);
		}
		tokenOrderService.updateTokenOrderStatus(authContext.getLoginSession().getUserId(), orderId, tokenOrderState);
	}

}
